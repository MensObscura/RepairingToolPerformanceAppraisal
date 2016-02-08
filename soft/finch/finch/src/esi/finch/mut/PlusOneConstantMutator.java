package esi.finch.mut;

import org.apache.commons.logging.Log;

import ec.util.MersenneTwisterFast;
import esi.bc.manip.IdentityConstantsMutator;
import esi.util.Config;

public class PlusOneConstantMutator extends IdentityConstantsMutator{

	
	private   static final Log log      = Config.getLogger();
	
	public PlusOneConstantMutator(float mutProb, MersenneTwisterFast random) {
	}
	@Override
	public int mutate(int x) {
		int y = x+1;
		log.warn(x +" ---> "+y);
		return x+1;
	}
}
