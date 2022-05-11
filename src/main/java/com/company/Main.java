//TODO: User Tester class to ensure Solver can solve all words
//TODO: Optimize guessing algorithm

package com.company;

import com.company.Solver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
//        System.out.println("LAST UPDATED 5/3 8:02 PM");
        Solver solver = new Solver();
        Tester tester = new Tester("");
        WordList wordList = new WordList();
        Scanner scanner = new Scanner(System.in);

        List<String> words = wordList.getSolutionList();
        List<String> possibleGuesses = wordList.getPossibleGuesses();

        int guesses = 0, wins = 0, loss = 0, total = 0, n = 2315, maxGuesses = 6;
        List<String> missedWords = new ArrayList<>();
        int index = 0;
        String guess;
        String result;
        boolean won = false;
        Map<String, Integer> guessMap = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> pqScore = new PriorityQueue<>((a,b) -> b.getValue() - a.getValue());

        try {
            BufferedWriter output = new BufferedWriter(new FileWriter("src/main/java/firstguess.txt",true));
//            String s = "slate";
            for(String s : possibleGuesses) {
                System.out.println(++index + " " + new Date());
                for(int i = 0; i < wordList.getSolutionList().size(); i++) {
                    tester.setSolution(wordList.getSolutionList().get(i));
                    result = tester.processGuess(s);
                    solver.processResult(s,result);
                    guessMap.put(s, guessMap.getOrDefault(s,0) + solver.getMasterSize());
                    solver.populateMaster();
                }

            }

            for(Map.Entry<String, Integer> e : guessMap.entrySet()) {
                pqScore.offer(e);
                if(pqScore.size() > 20) pqScore.remove();
            }

            while(!pqScore.isEmpty()) {
                Map.Entry<String,Integer> e = pqScore.remove();
                double d = e.getValue();
                output.write( e.getKey() + ": " + (d/n));
            }
            output.close();


        } catch(Exception e) {
            System.out.println("**** SOMETHING WENT WRONG - PRINTING PARTIAL RESULTS ****");
            for(Map.Entry<String, Integer> entry : guessMap.entrySet()) {
                pqScore.offer(entry);
                if(pqScore.size() > 20) pqScore.remove();
            }

            while(!pqScore.isEmpty()) {
                Map.Entry<String,Integer> entry = pqScore.remove();
                double d = entry.getValue();
                System.out.println(entry.getKey() + ": " + (d/guessMap.size()));
            }
            e.printStackTrace();
        }


//        String s = wordList.getRandomSolution();
//        for(int i = 0; i < 10000; i++) {
//            tester.setSolution(wordList.getRandomSolution());
////            System.out.println(tester.getSolution());
//            result = tester.processGuess(s);
//            solver.processResult(s,result);
//            guessMap.put(s, guessMap.getOrDefault(s,0) + solver.getMasterSize());
//            solver.populateMaster();
//        }


        System.out.println(new Date());

//        auto test
//        try {
//            for(String s : words) {
//                won = false;
//                guesses = 0;
//                solver = new Solver();
//                tester = new Tester(s);
//
//                while (guesses < maxGuesses && !won) {
//                    guess = solver.getBest();
//                    result = tester.processGuess(guess);
//
//
//                    guesses++;
//
//                    int greenCount = solver.processResult(guess, result);
//
//                    if (greenCount == 5) {
////                        System.out.println("Congrats - you won in " + guesses + " guesses!");
//                        won = true;
//                        wins++;
//                        total += guesses;
//                    }
//                }
//                if (guesses == maxGuesses && !won) {
////                    System.out.println("You lost :(");
//                    loss++;
//                    total += guesses;
//                    missedWords.add(tester.getSolution());
//                }
//            }
//
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("wins: " + wins + " losses: " + loss);
//        System.out.println("average # of guesses = " + (double)total/n);
//        missedWords.forEach(System.out::println);


//        manual test
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
