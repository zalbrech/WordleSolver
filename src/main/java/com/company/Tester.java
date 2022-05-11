package com.company;

//TODO: include all possible guesses or only possible solutions?

import java.util.HashMap;
import java.util.Map;

public class Tester {

    private double mean;
    private String solution;
    private Map<Character, Integer> quantMap = new HashMap<>();

    public Tester(String solution) {
        this.mean = 0;
        this.solution = solution;
//        System.out.println(this.solution);
        for(int i = 0; i < solution.length(); i++) {
            quantMap.put(solution.charAt(i), quantMap.getOrDefault(solution.charAt(i),0) +1);
        }
    }

    public void setSolution(String newSolution) {
        this.solution = newSolution;
        quantMap = new HashMap<>();
        for(int i = 0; i < solution.length(); i++) {
            quantMap.put(solution.charAt(i), quantMap.getOrDefault(solution.charAt(i),0) +1);
        }
    }

    public String getSolution() {
        return solution;
    }

    public double getMean() {
        return this.mean;
    }

    public String processGuess(String guess) {
        Map<Character, Integer> copyMap = new HashMap<>(quantMap);
        char[] grid = new char[5];
        for(int i = 0; i < guess.length(); i++) {
            if(solution.indexOf(guess.charAt(i)) < 0) grid[i] = 'B';
            if(guess.charAt(i) == solution.charAt(i)) {
                grid[i] = 'G';
                copyMap.put(guess.charAt(i), copyMap.get(guess.charAt(i))-1);
                if(copyMap.get(guess.charAt(i)) == 0) {
                    copyMap.remove(guess.charAt(i));
                }
            }
        }

        for(int i = 0; i < guess.length(); i++) {
            if(grid[i] == 'G' || grid[i] == 'B') continue;
            if(solution.indexOf(guess.charAt(i)) > -1) {
                if(copyMap.containsKey(guess.charAt(i))) {
                    grid[i] = 'Y';
                    copyMap.put(guess.charAt(i), copyMap.get(guess.charAt(i))-1);
                    if(copyMap.get(guess.charAt(i)) == 0) {
                        copyMap.remove(guess.charAt(i));
                    }
                } else grid[i] = 'B';
            }
        }

//        System.out.println(new String(grid));
        return new String(grid);

    }



}
