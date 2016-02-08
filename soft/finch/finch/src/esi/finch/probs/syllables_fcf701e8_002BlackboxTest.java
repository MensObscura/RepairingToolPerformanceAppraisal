package esi.finch.probs;

import org.junit.Test;

import esi.util.Config;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.junit.Before;
public class syllables_fcf701e8_002BlackboxTest {
	

	private   static final Log log      = Config.getLogger();
	Object mainClass;
	Method mainMethod;
	Field scanner;
	Field output;
	@Before
	public void getClassFromEvalutaor(){
		try {
			
				mainClass =  IntroClassBlackBoxEvaluator.getGeneratedClass().newInstance();
			
				Method[] m = mainClass.getClass().getMethods();
				for(int i = 0 ; i < m.length; i++){
					if(m[i].getName().equals("exec")){
						mainMethod = m[i];
						continue;
					}
				}
				
				for (Field f : mainClass.getClass().getDeclaredFields()) {
					if(f.getName().equals("scanner")){
						scanner =f;
						
					}
					
					if(f.getName().equals("output")){
						output = f;
						
					}
					
				}
				
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("fail to get GeneratedClass  or Cant get method from: "+IntroClassBlackBoxEvaluator.getGeneratedClass());
			}
	
		
	}

    @Test (timeout = 1000) public void test1 () throws Exception {
        
    	String expected =  "Please enter a string > The number of syllables is 0.";
scanner.set(mainClass, new java.util.Scanner ("khd"));
       mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test2 () throws Exception {
        
        String expected = "Please enter a string > The number of syllables is 6.";
scanner.set(mainClass, new java.util.Scanner ("aeiouy"));
       mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test3 () throws Exception {
        
        String expected = "Please enter a string > The number of syllables is 5.";
scanner.set(mainClass, new java.util.Scanner ("here and there"));
       mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test4 () throws Exception {
        
        String expected = "Please enter a string > The number of syllables is 1.";
scanner.set(mainClass, new java.util.Scanner ("bbbbbbb a"));
       mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test5 () throws Exception {
        
        String expected = "Please enter a string > The number of syllables is 0.";
scanner.set(mainClass, new java.util.Scanner ("9876543210"));
       mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test6 () throws Exception {
        
        String expected = "Please enter a string > The number of syllables is 3.";
scanner.set(mainClass, new java.util.Scanner ("1 a 2 e 3 $#@ u"));
       mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
}
