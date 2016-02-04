package esi.finch.probs;

import esi.util.IntObj;

public class syllables_fcf701e8_002 {
    public java.util.Scanner scanner;
    public String output = "";

    public static void main (String[]args) throws Exception {
        syllables_fcf701e8_002 mainClass = new syllables_fcf701e8_002 ();
        String output;
        if (args.length > 0) {
            mainClass.scanner = new java.util.Scanner (args[0]);
        } else {
            mainClass.scanner = new java.util.Scanner (System.in);
        }
        mainClass.exec ();
        System.out.println (mainClass.output);
    }

    public static int MAX = 20;
    public void exec () throws Exception {
        char[] input = new char[MAX];
        IntObj i = new IntObj (), s = new IntObj (), len = new IntObj ();
        output += (String.format ("Please enter a string > "));
        input = scanner.next ().toCharArray ();
        len.value = input.length;
        s.value = 0;
        for (i.value = 0; i.value < len.value; i.value++) {
            switch (input[i.value]) {
            case 'a':
                s.value++;
                break;
            case 'e':
                s.value++;
                break;
            case 'i':
                s.value++;
                break;
            case 'o':
                s.value++;
                break;
            case 'u':
                s.value++;
                break;
            case 'y':
                s.value++;
                break;
            default:
                break;
            }
        }
        output += (String.format ("The number of syllables is %d.\n", s.value));
        if (true)
            return;;
    }
}
