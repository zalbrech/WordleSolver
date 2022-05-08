package com.company;

import javax.swing.plaf.basic.BasicFormattedTextFieldUI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class WordList {

    private final List<String> solutionList;
    private final List<String> possibleGuesses;
    private final Map<String, Integer> twoLetter = new HashMap<>();
    private final Map<String, Integer> threeLetter = new HashMap<>();
    private final Map<String, Integer> fourLetter = new HashMap<>();
    public WordList() {
        this.solutionList = new ArrayList<>();
        this.possibleGuesses = new ArrayList<>();
        String s;
        try {
            BufferedReader input = new BufferedReader(new FileReader("src/main/java/words.txt"));
            while ((s = input.readLine()) != null) {
                solutionList.add(s);
            }
            input = new BufferedReader(new FileReader("src/main/java/allwords.txt")); {
                while ((s = input.readLine()) != null) {
                    possibleGuesses.add(s);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
//        populateMaps();
    }

    private void populateMaps() {
        for(String s : solutionList) {
            twoLetter.put(s.substring(s.length()-2), twoLetter.getOrDefault(s.substring(s.length()-2), 0) +1);
            threeLetter.put(s.substring(s.length()-3), threeLetter.getOrDefault(s.substring(s.length()-3), 0) +1);
            fourLetter.put(s.substring(s.length()-4), fourLetter.getOrDefault(s.substring(s.length()-4), 0) +1);
        }

        PriorityQueue<Map.Entry<String, Integer>> pq2 = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        PriorityQueue<Map.Entry<String, Integer>> pq3= new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        PriorityQueue<Map.Entry<String, Integer>> pq4 = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        for(Map.Entry<String, Integer> e : twoLetter.entrySet()) {
            pq2.offer(e);
            if(pq2.size() > 10) pq2.remove();
        }

        for(Map.Entry<String, Integer> e : threeLetter.entrySet()) {
            pq3.offer(e);
            if(pq3.size() > 10) pq3.remove();
        }

        for(Map.Entry<String, Integer> e : fourLetter.entrySet()) {
            pq4.offer(e);
            if(pq4.size() > 10) pq4.remove();
        }

        System.out.println(pq2);
        System.out.println(pq3);
        System.out.println(pq4);

    }


    public String getRandomSolution() {
        Random random = new Random();
        return solutionList.get(random.nextInt(solutionList.size()));
    }
    public List<String> getSolutionList() {
        return solutionList;
    }

    public List<String> getPossibleGuesses() { return possibleGuesses; }
}

