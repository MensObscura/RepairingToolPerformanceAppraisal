package esi.finch.probs;


import java.lang.reflect.Method;


import org.apache.commons.logging.Log;
import org.junit.runner.JUnitCore;

import ec.util.MersenneTwisterFast;
import esi.finch.ecj.bc.BytecodeEvaluator;
import esi.finch.ecj.bc.BytecodeIndividual;
import esi.util.Config;

public class IntroClassEvaluator implements BytecodeEvaluator {

	private static final Log log = Config.getLogger();
	public  static Class klass;
	@Override
	public Result evaluate(BytecodeIndividual ind, long timeout, long steps, MersenneTwisterFast random,
			int threadnum) {



		this.klass = ind.getMethod().getDeclaringClass();

		
			boolean ideal = false;
			boolean valid = true;
			float finess = 0;
			org.junit.runner.Result resultTest = null;
			Class test = new syllables_fcf701e8_002WhiteboxTest().getClass();
			JUnitCore ju = new JUnitCore();
			resultTest =ju.runClasses(test);

			if(resultTest != null){
				ideal = resultTest.wasSuccessful();
				finess = resultTest.getRunCount() - resultTest.getFailureCount();
				System.out.println("IntroClassEvaluator : finess = "+ resultTest.getRunCount() +"-"+ resultTest.getFailureCount());
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

	public  static Class getGeneratedClass(){
		return klass;
	}
}
