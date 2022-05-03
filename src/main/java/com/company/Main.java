package com.company;

import com.sun.source.tree.ArrayAccessTree;

import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("LAST UPDATED 4/24/22 7:43 PM");
        Solver solver = new Solver();
        Scanner scanner = new Scanner(System.in);

        int guesses = 0, wins = 0, loss = 0;
        double mean;
        String guess;
        String result;
        boolean won = false;
        for (int t = 0; t < 10000; t++) {
            Tester tester = new Tester("foo");

            while (guesses < 6 && !won) {
//                guess = solver.getBest();
//                result = tester.processGuess(guess);

                System.out.println("\nEnter a guess " + "(" + "I suggest " + solver.getBest() + ")");
                guess = scanner.nextLine().toLowerCase();
                guesses++;
                System.out.println("\nEnter:\n" +
                        "B for black square\n" +
                        "Y for yellow square\n" +
                        "G for green square\n");
                result = scanner.nextLine().toUpperCase();
                int greenCount = solver.processResult(guess, result);

                if (greenCount == 5) {
                    System.out.println("Congrats - you won!");
                    won = true;
                    break;
                }
            }
            if (guesses == 6 && !won) System.out.println("You lost :(");
        }

    }
}
