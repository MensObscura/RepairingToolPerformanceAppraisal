package esi.finch.probs.type.test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import esi.finch.probs.IntroClassBlackBoxEvaluator;
import esi.util.Config;


public class CasConstanteBlackboxTest {
	Method mainMethod;
	private   static final Log log      = Config.getLogger();
	
	@Before
	public void getClassFromEvalutaor(){
		try {
			Method[] m = IntroClassBlackBoxEvaluator.getGeneratedClass().newInstance().getClass().getMethods();
			for(int i = 0 ; i < m.length; i++){
				if(m[i].getName().equals("getToto")){
					mainMethod = m[i];
					continue;
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("Cant get method from: "+IntroClassBlackBoxEvaluator.getGeneratedClass());
		}
	}
	
	@Test
	public void GetTotoTest() {
		
	
		try {
			assertTrue(new String(mainMethod.invoke(null, null)+"").equals(new String(mainMethod.invoke(null,null)+"")));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			log.error("Cant call Method  : "+e);
		}
		
	}



}
