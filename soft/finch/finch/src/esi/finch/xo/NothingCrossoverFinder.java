package esi.finch.xo;

import org.apache.commons.logging.Log;

import ec.util.MersenneTwisterFast;
import esi.bc.AnalyzedMethodNode;
import esi.bc.xo.CompatibleCrossover;
import esi.finch.xo.CrossoverFinder.Sections;
import esi.util.Config;

public class NothingCrossoverFinder  extends CrossoverFinder{

	private   static final Log log      = Config.getLogger();

	public NothingCrossoverFinder(AnalyzedMethodNode alphaMethod, AnalyzedMethodNode betaMethod,
			CompatibleCrossover xo, MersenneTwisterFast random, int maxSize) {
		super(alphaMethod, betaMethod);
	}

	public Sections getSuggestion(){
		// null indicates failure
	
		return null;

	}

}
