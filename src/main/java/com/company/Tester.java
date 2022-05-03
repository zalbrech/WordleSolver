package com.company;

//TODO: how to best store and track solution? array + letter count?
//TODO: iterate through word list to ensure Solver can solve all words in 6 guesses or less
//TODO: random trials (t = 10,000) and adjust scoring algorithm
//TODO: include all possible guesses or only possible solutions?

public class Tester {

    private double mean;
    private String solution;

    public Tester(String solution) {
        this.mean = 0;
        this.solution = solution;
    }

    public double getMean() {
        return this.mean;
    }

    public String processGuess(String guess) {
        return "foo";
    }


}
