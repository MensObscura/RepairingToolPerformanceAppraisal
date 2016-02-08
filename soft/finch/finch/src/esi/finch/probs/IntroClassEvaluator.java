package esi.finch.probs;


import java.lang.reflect.Method;


import org.apache.commons.logging.Log;
import org.junit.runner.JUnitCore;

import ec.util.MersenneTwisterFast;
import esi.finch.ecj.bc.BytecodeEvaluator;
import esi.finch.ecj.bc.BytecodeIndividual;
import esi.finch.probs.introclass.test.*;
import esi.util.Config;

public class IntroClassEvaluator implements BytecodeEvaluator {

	private static final Log log = Config.getLogger();
	public  static Class klass;
	@Override
	public Result evaluate(BytecodeIndividual ind, long timeout, long steps, MersenneTwisterFast random,
			int threadnum) {


		IntroClassWhiteBoxEvaluator white = new IntroClassWhiteBoxEvaluator();
		
		IntroClassBlackBoxEvaluator black = new IntroClassBlackBoxEvaluator();
		
		
		Result blanc = white.evaluate(ind, timeout, steps, random, threadnum);
		Result noir = black.evaluate(ind, timeout, steps, random, threadnum);
		//System.out.println(resultTest.getFailures().get(0).getTrace());
				//if all test green, then result true else false
		float fitness = blanc.fitness + noir.fitness;
		boolean ideal = blanc.ideal && noir.ideal;
		return new Result(fitness, ideal);
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
