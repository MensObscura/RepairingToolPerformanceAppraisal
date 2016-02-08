package esi.finch.probs;


import java.lang.reflect.Method;


import org.apache.commons.logging.Log;
import org.junit.runner.JUnitCore;

import ec.util.MersenneTwisterFast;
import esi.finch.ecj.bc.BytecodeEvaluator;
import esi.finch.ecj.bc.BytecodeIndividual;
import esi.finch.probs.introclass.test.*;
import esi.util.Config;

public class IntroClassBlackBoxEvaluator implements BytecodeEvaluator {

	private static final Log log = Config.getLogger();
	public  static Class klass;
	@Override
	public Result evaluate(BytecodeIndividual ind, long timeout, long steps, MersenneTwisterFast random,
			int threadnum) {



		this.klass = ind.getMethod().getDeclaringClass();


		boolean ideal = false;
		float fitness = 0;
		org.junit.runner.Result resultTest = null;
		Class testB =null;

		String pack = this.klass.getPackage().getName().replace("src","test");
		try {
			testB = Class.forName(pack+"."+this.klass.getSimpleName()+"BlackboxTest");


		} catch (ClassNotFoundException e) {

			String[] tabName = this.klass.getSimpleName().split("_");
			String name ="";
			if(tabName.length > 0){
				name = tabName[0] ;
				for(int i = 1 ; i < tabName.length-3 ; i++){
					name +="_"+tabName[i];
				}
			}
			try {
				

				testB = Class.forName(pack+"."+name+"BlackboxTest");
			} catch (ClassNotFoundException e1) {
				System.err.println("could not found class : "+e);
			}

		}


		JUnitCore ju = new JUnitCore();
		resultTest =ju.runClasses( testB);
		
		if(resultTest != null){
			ideal = resultTest.wasSuccessful();
			fitness = resultTest.getRunCount() - resultTest.getFailureCount();
			System.out.println("IntroClassEvaluator :successful : "+ideal+", fitness = "+ resultTest.getRunCount() +"-"+ resultTest.getFailureCount() +"= "+fitness+"");
		}
		//System.out.println(resultTest.getFailures().get(0).getTrace());
				//if all test green, then result true else false
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
