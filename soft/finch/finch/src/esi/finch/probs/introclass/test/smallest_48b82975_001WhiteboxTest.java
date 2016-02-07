package esi.finch.probs.introclass.test;

import org.junit.Test;

import esi.finch.probs.IntroClassEvaluator;
import esi.finch.probs.introclass.src.smallest_48b82975_001;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
public class smallest_48b82975_001WhiteboxTest {
	
	smallest_48b82975_001 mainClass;
	
	@Before
	public void getClassFromEvalutaor(){
		try {
			mainClass =(smallest_48b82975_001) IntroClassEvaluator.getGeneratedClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
		System.out.println("fail to get GeneratedClass : "+e);
		}
		
	}

    @Test (timeout = 1000) public void test1 () throws Exception {
        mainClass = new smallest_48b82975_001 ();
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
        mainClass.scanner = new java.util.Scanner ("0 0 0 0");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test2 () throws Exception {
        mainClass = new smallest_48b82975_001 ();
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
        mainClass.scanner = new java.util.Scanner ("0 0 1 2");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test3 () throws Exception {
        mainClass = new smallest_48b82975_001 ();
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
        mainClass.scanner = new java.util.Scanner ("0 0 1 0");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test4 () throws Exception {
        mainClass = new smallest_48b82975_001 ();
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
        mainClass.scanner = new java.util.Scanner ("0 0 3 1");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test5 () throws Exception {
        mainClass = new smallest_48b82975_001 ();
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
        mainClass.scanner = new java.util.Scanner ("0 1 0 0");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test6 () throws Exception {
        mainClass = new smallest_48b82975_001 ();
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
        mainClass.scanner = new java.util.Scanner ("0 1 1 1");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test7 () throws Exception {
        mainClass = new smallest_48b82975_001 ();
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
        mainClass.scanner = new java.util.Scanner ("0 1 1 0");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test8 () throws Exception {
        mainClass = new smallest_48b82975_001 ();
        String expected =
            "Please enter 4 numbers separated by spaces > 0 is the smallest";
        mainClass.scanner = new java.util.Scanner ("0 1 3 1");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
}
