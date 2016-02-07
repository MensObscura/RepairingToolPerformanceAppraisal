package esi.finch.probs;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import esi.finch.*;
import org.junit.Before;
public class syllables_fcf701e8_002WhiteboxTest {
	syllables_fcf701e8_002 mainClass ;
	
	@Before
	public void getClassFromEvalutaor(){
		try {
			mainClass =(syllables_fcf701e8_002) IntroClassEvaluator.getGeneratedClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
		System.out.println("fail !");
		}
		
	}

    @Test (timeout = 1000) public void test1 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 1.";
        mainClass.scanner = new java.util.Scanner ("a");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test10 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 3.";
        mainClass.scanner = new java.util.Scanner ("snow white 123 ><");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test2 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 2.";
        mainClass.scanner = new java.util.Scanner ("i o");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test3 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 0.";
        mainClass.scanner = new java.util.Scanner ("mnhd");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test4 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 3.";
        mainClass.scanner = new java.util.Scanner ("hello world");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test5 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 5.";
        mainClass.scanner = new java.util.Scanner ("aeiou");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test6 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 6.";
        mainClass.scanner = new java.util.Scanner ("seasons greetings!");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test7 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 4.";
        mainClass.scanner = new java.util.Scanner ("which witch is which?");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test8 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 0.";
        mainClass.scanner = new java.util.Scanner ("!@#$%^,");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
    @Test (timeout = 1000) public void test9 () throws Exception {
        String expected = "Please enter a string > The number of syllables is 0.";
        mainClass.scanner = new java.util.Scanner ("123zdh");
        mainClass.exec ();
        String out = mainClass.output.replace ("\n", " ").trim ();
        assertEquals (expected.replace (" ", ""), out.replace (" ", ""));
    }
}
