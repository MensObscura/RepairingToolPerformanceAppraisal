package esi.finch.probs.type.test;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import esi.finch.probs.IntroClassWhiteBoxEvaluator;
import esi.util.Config;


public class CasConstanteWhiteboxTest {

	Method mainMethod;
	private   static final Log log      = Config.getLogger();
	@Before
	public void getClassFromEvalutaor(){
		try {
			Method[] m = IntroClassWhiteBoxEvaluator.getGeneratedClass().newInstance().getClass().getMethods();
			for(int i = 0 ; i < m.length; i++){
				if(m[i].getName().equals("getToto")){
					mainMethod = m[i];
					continue;
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("Cant get method from: "+IntroClassWhiteBoxEvaluator.getGeneratedClass());
		}
	}
	
	@Test
	public void GetTotoTest() {
		
	
		try {
			assertTrue(new String(mainMethod.invoke(null, null)+"").equals(new String(1+"")));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			log.error("Cant call Method  : "+e);
		}
		
	}


}
