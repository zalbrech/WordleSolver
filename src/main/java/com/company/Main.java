//TODO: Optimize guessing algorithm

package com.company;

import com.company.Solver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Solver solver = new Solver();
        Tester tester = new Tester("");
        WordList wordList = new WordList();

        List<String> words = wordList.getSolutionList();
        List<String> possibleGuesses = wordList.getPossibleGuesses();
        List<String> solutionList = wordList.getSolutionList();

        int guesses = 0, wins = 0, loss = 0, total = 0, n = 2315, maxGuesses = 6;
        List<String> missedWords = new ArrayList<>();
        int index = 0;
        String guess;
        String result;
        boolean won = false;
        Map<String, Integer> guessMap = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> pqScore = new PriorityQueue<>((a,b) -> b.getValue() - a.getValue());

        List<String> top25Guesses = solver.getTop25Guesses();


        // FIND BEST FIRST GUESS

//        try {
//            BufferedWriter output = new BufferedWriter(new FileWriter("src/main/java/firstguess.txt",true));
////            String s = "slate";
//            for(String s : top25Guesses) {
//                System.out.println(++index + " " + new Date());
//                for(int i = 0; i < solutionList.size(); i++) {
//                    tester.setSolution(solutionList.get(i));
//                    result = tester.processGuess(s);
//                    solver.processResult(s,result);
//                    guessMap.put(s, guessMap.getOrDefault(s,0) + solver.getMasterSize());
//                    solver.populateMaster();
//                }
//            }
//
//            for(Map.Entry<String, Integer> e : guessMap.entrySet()) {
//                pqScore.offer(e);
//                if(pqScore.size() > 25) pqScore.remove();
//            }
//
//            while(!pqScore.isEmpty()) {
//                Map.Entry<String,Integer> e = pqScore.remove();
//                double d = e.getValue();
//                output.write( e.getKey() + ": " + (d/n) + "\n");
//            }
//            output.close();
//
//
//        } catch(Exception e) {
//            System.out.println("**** SOMETHING WENT WRONG - PRINTING PARTIAL RESULTS ****");
//            for(Map.Entry<String, Integer> entry : guessMap.entrySet()) {
//                pqScore.offer(entry);
//                if(pqScore.size() > 20) pqScore.remove();
//            }
//
//            while(!pqScore.isEmpty()) {
//                Map.Entry<String,Integer> entry = pqScore.remove();
//                double d = entry.getValue();
//                System.out.println(entry.getKey() + ": " + (d/guessMap.size()));
//            }
//            e.printStackTrace();
//        }


//        OLD TEST - DON'T UNCOMMENT

//        String s = wordList.getRandomSolution();
//        for(int i = 0; i < 10000; i++) {
//            tester.setSolution(wordList.getRandomSolution());
////            System.out.println(tester.getSolution());
//            result = tester.processGuess(s);
//            solver.processResult(s,result);
//            guessMap.put(s, guessMap.getOrDefault(s,0) + solver.getMasterSize());
//            solver.populateMaster();
//        }

//        System.out.println(new Date());

        System.out.println("Welcome to Wordle Solver!\n" +
                           "Select an option:\n" +
                           "1) Manual Solve\n" +
                           "2) Auto Test (test scoring algorithm)");

        boolean valid = false;
        int input = 0;
        while(!valid) {
            input = scanner.nextInt();
            if(input == 1 || input == 2) {
                valid = true;
            }
            System.out.println("Please select either 1 or 2");
        }

        if(input == 1) {
            manualSolve();
        } else {
            autoTest();
        }

//        manualSolve();
//        autoTest();

    }

//    manual solve - 99.4% solve rate

    public static void manualSolve() {

        String guess, result;

        Solver solver = new Solver();
        boolean won = false;
        int guesses = 0, maxGuesses = 6, wins = 0, loss = 0, total = 0, n = 2315;

        while (guesses < maxGuesses && !won) {
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
                System.out.println("Congrats - you won in " + guesses + " guesses!");
                won = true;
                wins++;
            }
        }
        if (guesses == maxGuesses && !won) {
            System.out.println("You lost :(");
        }
    }

    // Test that uses scoring system to attempt to solve all possible solutions
    // Currently 99.37% solve rate, average # of guesses = 3.6
    public static void autoTest() {
        int guesses = 0, maxGuesses = 6, wins = 0, loss = 0, total = 0, n = 2315;
        List<String> missedWords = new ArrayList<>();
        try {
            boolean won;
            Solver solver;
            Tester tester;
            String guess,result;

            WordList wordList = new WordList();
            List<String> words = wordList.getSolutionList();

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
                    loss++;
                    total += guesses;
                    missedWords.add(tester.getSolution());
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Wins: " + wins + " Losses: " + loss);
        System.out.println("average # of guesses = " + (double)total/n);
        missedWords.forEach(System.out::println);
    }

}
