package esi.finch.probs;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;

import esi.finch.*;
import esi.util.Config;

import org.junit.Before;
public class syllables_fcf701e8_002WhiteboxTest {
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
        String expected = "Please enter a string > The number of syllables is 1.";
        scanner.set(mainClass,new java.util.Scanner ("a"));
        mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test10 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 3.";
        scanner.set(mainClass,new java.util.Scanner ("snow white 123 ><"));
        mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test2 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 2.";
        scanner.set(mainClass,new java.util.Scanner ("i o"));
        mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test3 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 0.";
        scanner.set(mainClass,new java.util.Scanner ("mnhd"));
        mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test4 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 3.";
        scanner.set(mainClass,new java.util.Scanner ("hello world"));
        mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test5 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 5.";
        scanner.set(mainClass,new java.util.Scanner ("aeiou"));
        mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test6 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 6.";
        scanner.set(mainClass,new java.util.Scanner ("seasons greetings!"));
        mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test7 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 4.";
        scanner.set(mainClass,new java.util.Scanner ("which witch is which?"));
        mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test8 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 0.";
        scanner.set(mainClass,new java.util.Scanner ("!@#$%^,"));
        mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test9 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 0.";
        scanner.set(mainClass,new java.util.Scanner ("123zdh"));
        mainMethod.invoke(mainClass);
        String out = ((String)output.get(mainClass)).replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
}
