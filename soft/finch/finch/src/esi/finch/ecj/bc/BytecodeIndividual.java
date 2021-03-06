package esi.finch.ecj.bc;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.Method;

import ec.EvolutionState;
import ec.Individual;
import ec.util.MersenneTwisterFast;
import ec.util.Parameter;
import esi.bc.AnalyzedClassNode;
import esi.bc.AnalyzedMethodNode;
import esi.bc.manip.CodeInterrupter;
import esi.bc.manip.CodeMerger;
import esi.bc.manip.CodeModifier;
import esi.bc.manip.CodeProducer;
import esi.bc.manip.ConstantsMutator;
import esi.bc.xo.CompatibleCrossover;
import esi.bc.xo.TypeVerifier;
import esi.finch.ecj.immutable.ImmutableIndividual;
import esi.finch.ecj.immutable.ImmutableSpecies;
import esi.finch.xo.CrossoverFinder;
import esi.finch.xo.CrossoverFinder.Sections;
import esi.util.Config;
import esi.util.Loader;
import esi.util.SpecializedConstants;

/**
 * Bytecode individual.
 *
 * <p>Default base: <tt>bytecode.ind</tt>.
 * Parameters:
 * <ul>
 * <li><tt>class</tt>        (name of initial class),
 * <li><tt>method-name</tt>  (method name),
 * <li><tt>method-desc</tt>  (optional method descriptor),
 * <li><tt>xo-class</tt>     (crossover finder class)
 * <li><tt>mut-class</tt>    (constants mutation class, assumes mut-prob > 0)
 * <li><tt>max-growth</tt>   (optional maximal method growth factor).
 * </ul>
 *
 * <p>Constants:
 * <ul>
 * <li><code>class.BytecodeIndividual.dump</code>: whether to save all individuals in
 *     {@link Config#DIR_OUT_ECJ}
 * <li><code>class.BytecodeIndividual.steps-indexes</code>: number of supported indexes
 *     for given evaluation thread step counters
 * </ul>
 *
 * @author Michael Orlov
 */
public class BytecodeIndividual extends Individual implements ImmutableIndividual<BytecodeIndividual> {

	private static final boolean DUMP       = SpecializedConstants.getBoolean(BytecodeIndividual.class, "dump");
	private static final int     STEPS_MULT = SpecializedConstants.getInt(BytecodeIndividual.class, "steps-indexes");
	private static final Log     log        = Config.getLogger();

	private static final long   serialVersionUID      = 1L;
	private static final String P_BYTECODE_INDIVIDUAL = "ind";

	// Parameter names
	private static final String P_INIT_CLASS          = "class";
	private static final String P_METHOD_NAME         = "method-name";
	private static final String P_METHOD_DESC         = "method-desc";
	private static final String P_XO_CLASS            = "xo-class";
	private static final String P_MUT_CLASS           = "mut-class";
	private static final String P_MAX_GROWTH          = "max-growth";

	/**
	 * If steps limit for the given thread is set, decrement it
	 * and throw {@link InterruptedException} if zero is reached.
	 *
	 * If steps limit is not set, call {@link Thread#sleep(long)}
	 * to allow interruption of the thread.
	 *
	 * NOTE: the interrupt method is static in order to keep
	 * {@link CodeInterrupter} framework simple, which implies
	 * appropriate limitations.
	 */
	public static class StepsInterrupter {
		// Per-evaluation steps (initialized in setup)
		private static long[]	steps;

		public static void interrupt(int id) throws InterruptedException {
			if (steps[id] != 0) {
				if (--steps[id] == 0)
					throw new InterruptedException("Steps limit exceeded");
			}
			else
				Thread.sleep(0);
		}
	}

	// Per-breed counters (initialized in setup)
	private int[]								counters;
	// Initial values (filled in setup)
	private Class<?>							initClass;
	private AnalyzedClassNode					initClassNode;
	private Method								methodDef;
	private Class<?>[]							methodParams;
	private Class<? extends CrossoverFinder>	xoFinderClass;
	private Class<? extends ConstantsMutator>	mutConstantsClass;
	private int									initSize;
	private float								maxGrowth;

	// Genome
	private int             	size;		// bytecode length (or initSize)
	private CodeProducer		producer;	// for accessing individuals' classes
	private Object				info;		// used in genome representation

	@Override
	public BytecodeIndividual crossover(BytecodeIndividual other,
			EvolutionState state, int thread) {
		// Individuals entering crossover pipeline are already evaluated
		assert evaluated  &&  other.evaluated;

		ImmutableSpecies    species = (ImmutableSpecies) this.species;
		MersenneTwisterFast random  = state.random[thread];

		BytecodeIndividual res = this;

		if (random.nextFloat() < species.getXoProb()) {
			// Original name (from initClass) and new name (although same names are ok)
			String origName = initClass.getName();
			String name     = createClassName(state.generation, thread);

			// Alpha and beta class nodes
			AnalyzedClassNode alphaClassNode = getClassNode();
			AnalyzedClassNode betaClassNode  = other.getClassNode();
			TypeVerifier      verifier       = new TypeVerifier(alphaClassNode, betaClassNode);

			// Alpha and beta method nodes
			AnalyzedMethodNode alphaMethod = alphaClassNode.findMethod(methodDef);
			AnalyzedMethodNode betaMethod  = betaClassNode.findMethod(methodDef);

			// Pick crossover sections
			CompatibleCrossover xo         = new CompatibleCrossover(alphaMethod, betaMethod, verifier);
			CrossoverFinder     xoFinder   = Loader.loadClassInstance(xoFinderClass,
					alphaMethod, betaMethod, xo, random, Math.round(initSize * maxGrowth));
			Sections            xoSections = xoFinder.getSuggestion();

			// If crossover was found
			if (xoSections != null) {
				// Bytecode merger
				CodeMerger merger = new CodeMerger(name, origName, alphaClassNode, betaClassNode, xoSections.alpha, xoSections.beta);

				// Create and fill new individual
				res = clone();
				res.fillGenome(merger);
			}
		}

		log.trace("Crossover " + ((res == this) ? "same" : "changed") + ": " + res);
		return res;
	}

	@Override
	public BytecodeIndividual mutate(EvolutionState state, int thread) {
		// It is possible for individuals entering mutation pipeline
		// to be unevaluated (if they come after crossover)

		ImmutableSpecies    species = (ImmutableSpecies) this.species;
		BytecodeIndividual  res     = this;

		if (species.getMutProb() > 0) {
			// New name (although same names are ok)
			String name = createClassName(state.generation, thread);

			// Mutator and code modifier
			MersenneTwisterFast random   = state.random[thread];
			ConstantsMutator    mutator  = Loader.loadClassInstance(mutConstantsClass,
					species.getMutProb(), random);
			CodeModifier        modifier = new CodeModifier(name, getClassNode(), methodDef, mutator);

			// Create and fill new individual
			res = clone();
			res.fillGenome(modifier);
		}

		return res;
	}

	// Creates a name for a new class (counter increment side-effect)
	private String createClassName(int generation, int thread) {
		// The new individual is created for the next generation
		return initClass.getName() + "_G" + (generation+1)
								+ "_T" + thread
								+ "_" + (++counters[thread]);
	}

	@Override
	public void reset(EvolutionState state, int thread) {
		assert !evaluated  &&  isInitial();
		fillGenome(null);

		log.trace("Reset: " + this);
	}

	@Override
	public boolean isInitial() {
		return producer == null;
	}

	private AnalyzedClassNode getClassNode() {
		// NOTE: Expensive operation - production of AnalyzedClassNode
		return isInitial()  ?  initClassNode
				:  producer.getClassNode();
	}

	// Fills the genome's fields
	private void fillGenome(CodeProducer producer) {
		// fill fields
		this.producer = producer;
		evaluated     = false;
		info          = null;

		// size (for parsimony pressure, if applicable)
		// class size, not the size used for max growth
		size = (producer == null)  ?  initSize  :  producer.size();

		// Save individual if dumping is enabled
		if (DUMP)
			try {
				saveClass(Config.DIR_OUT_ECJ);
			} catch (IOException e) {
				throw new RuntimeException("Unexpected I/O error", e);
			}
	}

	@Override
	public void setup(EvolutionState state, Parameter base) {
		super.setup(state, base);

		// This should only happen once in a single experiment (but happens more in tests)
		synchronized (getClass()) {
			if (StepsInterrupter.steps == null) {
				// Initialize per-eval-thread*multiplier step counters
				StepsInterrupter.steps = new long[state.evalthreads * STEPS_MULT];
			}
			else
				log.warn("ECJ attempted to initialize prototype individual more than once");
		}

		// Initialize per-breeding-thread counters to 0
		counters = new int[state.breedthreads];

		Parameter def = defaultBase();

		// Load the class, and also check that it can be loaded
		initClass = state.parameters.getClassForParameter(base.push(P_INIT_CLASS), def.push(P_INIT_CLASS), Object.class);

		// Create initial class node (recompute frames)
		try {
			initClassNode = AnalyzedClassNode.readClass(initClass, true, true);
		} catch (IOException e) {
			throw new Error("Unexpected error, cannot re-read class", e);
		}

		// Load methodName
		String methodName = state.parameters.getString(base.push(P_METHOD_NAME), def.push(P_METHOD_NAME));
		if (methodName == null)
			state.output.error("No method name", base.push(P_METHOD_NAME), def.push(P_METHOD_NAME));

		// Load method descriptor (can be null)
		String  methodDesc  = state.parameters.getString(base.push(P_METHOD_DESC), def.push(P_METHOD_DESC));
		boolean methodDescProvided = (methodDesc != null);
		boolean methodFound = false;

		// Check that method is present, deduce descriptor if not supplied
		for (java.lang.reflect.Method method: initClass.getDeclaredMethods()) {
			if (method.getName().equals(methodName)) {
				String desc = Type.getMethodDescriptor(method);
				if (methodDesc == null  ||  methodDesc.equals(desc)) {
					methodDesc  = desc;
					methodFound = true;

					methodParams = method.getParameterTypes();
				}
				else if (!methodDescProvided  &&  !methodDesc.equals(desc))
					state.output.error("Ambiguous method name, provide descriptor", base.push(P_METHOD_DESC), def.push(P_METHOD_DESC));
			}
		}

		if (!methodFound)
			state.output.error("Method not found", base.push(P_METHOD_NAME), def.push(P_METHOD_NAME));

		methodDef = new Method(methodName, methodDesc);
		initSize  = initClassNode.findMethod(methodDef).instructions.size();

		// Load XO finder class (always) and constants mutator class (if given)
		xoFinderClass     = ((Class<?>) state.parameters.getClassForParameter(base.push(P_XO_CLASS), def.push(P_XO_CLASS), CrossoverFinder.class)).asSubclass(CrossoverFinder.class);
		if (state.parameters.getString(base.push(P_MUT_CLASS), def.push(P_MUT_CLASS)) != null)
			mutConstantsClass = ((Class<?>) state.parameters.getClassForParameter(base.push(P_MUT_CLASS), def.push(P_MUT_CLASS), ConstantsMutator.class)).asSubclass(ConstantsMutator.class);

		// Load max growth factor (default is 0 - unused)
		maxGrowth = state.parameters.getFloat(base.push(P_MAX_GROWTH), def.push(P_MAX_GROWTH), 1);
		if (maxGrowth == 1-1)
			maxGrowth = 0;

		log.info("Bytecode individual set up:"
				+ "\n    class="      + initClass.getName()
				+ "\n    method="     + methodDef
				+ "\n    size="       + initSize
				+ "\n    xo-class="   + xoFinderClass.getName()
				+ "\n    mut-class="  + (mutConstantsClass == null  ?  "none"  :  mutConstantsClass.getName())
				+ "\n    max-growth=" + maxGrowth);
	}

	@Override
	public Parameter defaultBase() {
		return BytecodeDefaults.base().push(P_BYTECODE_INDIVIDUAL);
	}

	/**
	 * Evolving method accessor, intended to be used by implementers
	 * of {@link BytecodeEvaluator}.
	 *
	 * @return Java method of the genome's class
	 */
	public java.lang.reflect.Method getMethod() {
		try {
			// NOTE: Expensive operation - production of Class
			Class<?> klass = isInitial()  ?  initClass
					:  producer.getClassLoader().loadClass(producer.getName());
			assert isInitial()  ||  producer.getName().equals(klass.getName());

			// Use getDeclaredMethod (and not getMethod), since the method can
			// only be located in the concrete class (never higher in the hierarchy)
			return klass.getDeclaredMethod(methodDef.getName(), methodParams);
		} catch (ClassNotFoundException e) {
			throw new Error("Unexpected: class not found", e);
		} catch (NoSuchMethodException e) {
			throw new Error("Unexpected: method not found", e);
		}
	}

	/**
	 * Evolving method accessor, intended to be used by implementers
	 * of {@link BytecodeEvaluator} that need to get an interruptible
	 * class version of the genome class.
	 *
	 * Uses {@link CodeInterrupter}.
	 *
	 * @param maxSteps maximum number of steps before throwing {@link InterruptedException}
	 * 		  (0 means no limit, in which case {@link Thread#sleep(long)} is invoked every time)
	 * @param threadnum number of evaluation thread
	 * @param index index to disambiguate several method in same thread
	 *        (must be less than the "steps-indexes" specialized parameter)
	 *
	 * @return Java method of the genome's interruptible class
	 */
	public java.lang.reflect.Method getInterruptibleMethod(long maxSteps, int threadnum, int index) {
		assert threadnum < (StepsInterrupter.steps.length / STEPS_MULT)  &&  index < STEPS_MULT;
		int id = index * (StepsInterrupter.steps.length / STEPS_MULT) + threadnum;

		StepsInterrupter.steps[id] = maxSteps;

		CodeProducer cp = isInitial()
			?  new CodeInterrupter(initClass.getName() + "_Int",
					initClassNode,
					StepsInterrupter.class.getName(), "interrupt", id)
			:  new CodeInterrupter(producer.getName() + "_Int",
					producer.getClassReader(),
					StepsInterrupter.class.getName(), "interrupt", id);

		try {
			Class<?> klass = cp.getClassLoader().loadClass(cp.getName());
			return klass.getDeclaredMethod(methodDef.getName(), methodParams);
		} catch (ClassNotFoundException e) {
			throw new Error("Unexpected: class not found", e);
		} catch (NoSuchMethodException e) {
			throw new Error("Unexpected: method not found", e);
		}
	}

	/**
	 * Saves the class file in the supplied directory.
	 *
	 * This is only possible when the class has been produced as
	 * a sequence of bytes, i.e., it has an associated bytes class loader.
	 *
	 * @param outputDir where to save the class file
	 * @return whether file was saved (a merger was available)
	 * @throws IOException in case of I/O error
	 * @throws RuntimeException if the genome is an initial class
	 */
	public boolean saveClass(File outputDir) throws IOException {
		// No loader if it's an initial individual
		if (isInitial())
			return false;

		try {
			Class<?> savedClass = producer.getClassLoader(outputDir).loadClass(producer.getName());
			assert producer.getName().equals(savedClass.getName());
		} catch (ClassNotFoundException e) {
			throw new Error("Unexpected: class not found", e);
		}

		log.info("Saved " + this + " in directory " + outputDir.getAbsolutePath());
		return true;
	}

	@Override
	public long size() {
		return size;
	}

	@Override
	public boolean equals(Object ind) {
		assert ind instanceof BytecodeIndividual;
		return producer == ((BytecodeIndividual) ind).producer;
	}

	@Override
	public int hashCode() {
		return isInitial()  ?  0  :  producer.hashCode();
	}

	@Override
	public BytecodeIndividual clone() {
		// Default clone is OK
		return (BytecodeIndividual) super.clone();
	}

	@Override
	public String toString() {
		return "Bytecode = "
			+ (isInitial()  ?  initClass.getName()  :  producer.getName())
			+ "." + methodDef
			+ " [" + size() + "]";
	}

	@Override
	public String genotypeToStringForHumans() {
		String rep = super.genotypeToStringForHumans();

		if (info != null)
			rep += "\n" + info;

		return rep;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

}
