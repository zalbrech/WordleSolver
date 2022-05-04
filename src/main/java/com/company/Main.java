package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("LAST UPDATED 5/2 8:18 PM");
        Solver solver = new Solver();
        Scanner scanner = new Scanner(System.in);

        int guesses = 0, wins = 0, loss = 0;
        double mean;
        String guess;
        String result;
        boolean won = false;

//        Tester tester = new Tester("slate");
//        tester.processGuess("exits");
//        tester.processGuess("snips");
//        tester.processGuess("abode");

//        for (int t = 0; t < 10000; t++) {
//            Tester tester = new Tester("foo");
//
//            while (guesses < 6 && !won) {
////                guess = solver.getBest();
////                result = tester.processGuess(guess);
//
//                System.out.println("\nEnter a guess " + "(" + "I suggest " + solver.getBest() + ")");
//                guess = scanner.nextLine().toLowerCase();
//                guesses++;
//                System.out.println("\nEnter:\n" +
//                        "B for black square\n" +
//                        "Y for yellow square\n" +
//                        "G for green square\n");
//                result = scanner.nextLine().toUpperCase();
//                int greenCount = solver.processResult(guess, result);
//
//                if (greenCount == 5) {
//                    System.out.println("Congrats - you won!");
//                    won = true;
//                    break;
//                }
//            }
//            if (guesses == 6 && !won) System.out.println("You lost :(");
//        }

    }
}
