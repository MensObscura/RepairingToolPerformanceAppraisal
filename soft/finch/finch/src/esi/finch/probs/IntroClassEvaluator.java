package esi.finch.probs;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.junit.runner.JUnitCore;

import ec.util.MersenneTwisterFast;
import esi.finch.ecj.bc.BytecodeEvaluator;
import esi.finch.ecj.bc.BytecodeIndividual;
import esi.finch.probs.ArtificialAnt.OperationsLimit;
import esi.util.Config;
import esi.util.SandBox;
import java.lang.ClassLoader;

public class IntroClassEvaluator implements BytecodeEvaluator {

	private static final Log log = Config.getLogger();
	private static final String EXEC		 = "exec";
	@Override
	public Result evaluate(BytecodeIndividual ind, long timeout, long steps, MersenneTwisterFast random,
			int threadnum) {


		Method exec;

		Class klass = ind.getMethod().getDeclaringClass();

		try{
			exec = klass.getDeclaredMethod(EXEC);
		} catch (Exception e) {
			throw new Error("Unexpected exception", e);
		}
		// Create an artificial ant instance
		Object instance;
		try {
			Constructor<?> konst = klass.getConstructor();
			instance = konst.newInstance();

			// Save instance for representation purposes
			ind.setInfo(instance);
		} catch (Exception e) {
			throw new Error("Unexpected exception", e);
		}

		SandBox sandbox = new SandBox(instance, exec, timeout);

		// Execute the "go" method (exception is thrown when maximum steps reached)
		boolean ideal = false;
		boolean valid = true;
		float finess = 0;

		SandBox.Result result = sandbox.call();

		// Timeout invalidates the individual
		if (result == null) {
			log.debug("Timeout in " + ind);
			valid = false;
		}
		// Unexpected exception invalidates the individual
		else if (result.exception != null  &&  !(result.exception instanceof OperationsLimit)) {
			log.debug("Exception: " + result.exception + " in " + ind);
			valid = false;
		}
		else {
			org.junit.runner.Result resultTest = null;
			try {
				Class<?> test = Class.forName("syllables_fcf701e8_002WhiteboxTest");
				resultTest =JUnitCore.runClasses(test);
			} catch (ClassNotFoundException e) {
				System.out.println("Class not found : " +e );
			}

			if(resultTest != null){
				ideal = resultTest.wasSuccessful();
				finess = resultTest.getRunCount() - resultTest.getFailureCount();
				System.out.println("IntroClassEvaluator : finess = "+ resultTest.getRunCount() +"-"+ resultTest.getFailureCount());
			}
		}

		//if all test green, then result true else false
		return new Result(finess, ideal);
	}

	@Override
	public MatchResult evaluate(BytecodeIndividual ind1, BytecodeIndividual ind2, long timeout, long steps,
			MersenneTwisterFast random, int threadnum) {
		System.out.println("Not implemented");
		return null;
	}

}
