package esi.finch.probs.introclass.src;


import esi.util.*;

public class smallest_97f6b152_003 {
    public java.util.Scanner scanner;
    public String output = "";

    public static void main (String[]args) throws Exception {
        smallest_97f6b152_003 mainClass = new smallest_97f6b152_003 ();
        String output;
        if (args.length > 0) {
            mainClass.scanner = new java.util.Scanner (args[0]);
        } else {
            mainClass.scanner = new java.util.Scanner (System.in);
        }
        mainClass.exec ();
        System.out.println (mainClass.output);
    }

    public void exec () throws Exception {
        IntObj num1 = new IntObj (), num2 = new IntObj (), num3 =
            new IntObj (), num4 = new IntObj (), small = new IntObj ();
        output +=
            (String.format ("Please enter 4 numbers separated by spaces > "));
        num1.value = scanner.nextInt ();
        num2.value = scanner.nextInt ();
        num3.value = scanner.nextInt ();
        num4.value = scanner.nextInt ();
        if (num1.value < num2.value && num1.value < num3.value
                && num1.value < num4.value) {
            small.value = num1.value;
        } else if (num2.value < num1.value && num2.value < num3.value
                   && num2.value < num4.value) {
            small.value = num2.value;
        } else if (num3.value < num1.value && num3.value < num2.value
                   && num3.value < num4.value) {
            small.value = num3.value;
        } else {
            small.value = num4.value;
        }
        output += (String.format ("%d is the smallest\n", small.value));
        if (true)
            return;;
    }
}