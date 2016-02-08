package esi.finch.probs.introclass.test;

import org.junit.Test;

import esi.finch.probs.IntroClassWhiteBoxEvaluator;
import esi.finch.probs.introclass.src.smallest_15cb07a7_007;
import esi.util.Config;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.junit.Before;
public class smallest_15cb07a7_007WhiteboxTest {
	
	private   static final Log log      = Config.getLogger();
	
	Object mainClass;
	Method mainMethod;
	Field scanner;
	Field output;
	@Before
	public void getClassFromEvalutaor(){
		try {
			
				mainClass =  IntroClassWhiteBoxEvaluator.getGeneratedClass().newInstance();
			
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
				log.error("fail to get GeneratedClass  or Cant get method from: "+IntroClassWhiteBoxEvaluator.getGeneratedClass());
			}
	
		
	}
	
    @Test (timeout = 1000) public void test1 () throws Exception {
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
        scanner.set(mainClass, new java.util.Scanner ("0 0 0 0"));
        mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    
    @Test (timeout = 1000) public void test2 () throws Exception {
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
       scanner.set(mainClass, new java.util.Scanner ("0 0 1 2"));
       mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test3 () throws Exception {
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
       scanner.set(mainClass, new java.util.Scanner ("0 0 1 0"));
       mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test4 () throws Exception {
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
       scanner.set(mainClass, new java.util.Scanner ("0 0 3 1"));
       mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test5 () throws Exception {
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
       scanner.set(mainClass, new java.util.Scanner ("0 1 0 0"));
       mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test6 () throws Exception {
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
       scanner.set(mainClass, new java.util.Scanner ("0 1 1 1"));
       mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test7 () throws Exception {
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
       scanner.set(mainClass, new java.util.Scanner ("0 1 1 0"));
       mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test8 () throws Exception {
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
       scanner.set(mainClass, new java.util.Scanner ("0 1 3 1"));
       mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
}
