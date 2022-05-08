//TODO: User Tester class to ensure Solver can solve all words
//TODO: Optimize guessing algorithm

package com.company;

import com.company.Solver;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("LAST UPDATED 5/3 8:02 PM");
        Solver solver = new Solver();
        Tester tester = new Tester("");
        WordList wordList = new WordList();
        Scanner scanner = new Scanner(System.in);

        List<String> words = wordList.getSolutionList();
        int guesses = 0, wins = 0, loss = 0, total = 0, n = 2315, maxGuesses = 8;
        List<String> missedWords = new ArrayList<>();

        String guess;
        String result;
        boolean won = false;

        try {
            for(String s : words) {
                won = false;
                guesses = 0;
                solver = new Solver();
                tester = new Tester(s);

                while (guesses < maxGuesses && !won) {
                    guess = solver.getBest();
                    result = tester.processGuess(guess);


                    guesses++;

                    int greenCount = solver.processResult(guess, result);

                    if (greenCount == 5) {
//                        System.out.println("Congrats - you won in " + guesses + " guesses!");
                        won = true;
                        wins++;
                        total += guesses;
                    }
                }
                if (guesses == maxGuesses && !won) {
//                    System.out.println("You lost :(");
                    loss++;
                    total += guesses;
                    missedWords.add(tester.getSolution());
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("wins: " + wins + " losses: " + loss);
        System.out.println("average # of guesses = " + (double)total/n);
        missedWords.forEach(System.out::println);

//        while (guesses < maxGuesses && !won) {
//            System.out.println("\nEnter a guess " + "(" + "I suggest " + solver.getBest() + ")");
//            guess = scanner.nextLine().toLowerCase();
//            guesses++;
//            System.out.println("\nEnter:\n" +
//                    "B for black square\n" +
//                    "Y for yellow square\n" +
//                    "G for green square\n");
//            result = scanner.nextLine().toUpperCase();
//            int greenCount = solver.processResult(guess, result);
//
//            if (greenCount == 5) {
//                System.out.println("Congrats - you won in " + guesses + " guesses!");
//                won = true;
//                wins++;
//            }
//        }
//        if (guesses == maxGuesses && !won) {
//            System.out.println("You lost :(");
//            loss++;
//        }
    }
}
