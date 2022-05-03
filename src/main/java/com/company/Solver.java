//TODO: make appropriate fields final
//TODO: organize method order
//TODO: clean up comments
//TODO: write a Tester class

package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Solver {

    private Set<Character> blackLetters; // if blackLetters contains letter in word, discard it
    private Set<Character> yellowLetters; // candidate must contain all yellow letters
    private Set<Character> greenLetters; // candidate must contain all green letters


    private final List<Set<Character>> yellowList;
    private final List<Set<Character>> greenList;
    private List<Map<Character, Integer>> posList;

    private Map<String, Integer> dict;
    private Map<Character, Integer> freq;
    private final Set<String> master;

    private final Set<Character> duplicates;
    private final Set<Character> singles;

    private final PriorityQueue<Map.Entry<Character, Integer>> freqPq;

    private String best;

    public Solver() {
        this.blackLetters = new HashSet<>(); // if blackLetters contains letter in word, discard it
        this.yellowLetters = new HashSet<>(); // candidate must contain all yellow letters
        this.greenLetters = new HashSet<>(); // candidate must contain all green letters

        this.yellowList = List.of(new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>());
        this.greenList = List.of(new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>());

        this.freq = new HashMap<>(); // count frequency of each letter
        this.master = new HashSet<>(); // master list of possible words, used to repopulate dict & freq after each guess

        this.freqPq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        this.duplicates = new HashSet<>();
        this.singles = new HashSet<>();

        String s;
        // populate master list
        try {
            BufferedReader input = new BufferedReader(new FileReader("src/main/java/words.txt"));
            while ((s = input.readLine()) != null) {
                master.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.populate();
        this.findBest();
    }

    private void populate() {
        this.dict = new HashMap<>(); // list of possible words with scores
        this.freq = new HashMap<>();
        this.posList = List.of(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());

        for (String s : this.master) {
            for (int i = 0; i < s.length(); i++) {
                this.posList.get(i).put(s.charAt(i), this.posList.get(i).getOrDefault(s.charAt(i), 0) + 1); // count index position of each letter
                this.freq.put(s.charAt(i), this.freq.getOrDefault(s.charAt(i), 0) + 1); // count frequency of each letter
            }
        }
        for (String s : this.master) {
            dict.put(s, this.score(s)); // add word to dictionary
        }

        System.out.println(this.master);
        this.printScores();
        this.findBest();
    }

    private int score(String s) {
        int score = 0;
        for (int i = 0; i < s.length(); i++) {
            score += posList.get(i).get(s.charAt(i)) + (freq.get(s.charAt(i)) / 8);
        }
        return score;
    }

    // internal method for testing only
    private void printScores() {
        PriorityQueue<Map.Entry<String, Integer>> pqScore = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        for(Map.Entry<String, Integer> e : dict.entrySet()) {
            pqScore.offer(e);
        }

        while(!pqScore.isEmpty()) {
            System.out.println(pqScore.remove());
        }
    }

    public Set<String> getMaster() {
        return this.master;
    }

    private String findBest() {
        PriorityQueue<Map.Entry<String, Integer>> pqScore = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        for (Map.Entry<String, Integer> e : dict.entrySet()) {
            pqScore.offer(e);
            if (pqScore.size() > 1) pqScore.remove();
        }

        this.best = pqScore.remove().getKey();
        return this.best;
    }

    public void removeWord(String word) {
        this.master.remove(word);
        this.dict.remove(word);
    }

    public void addBlackLetter(char c) {
        this.blackLetters.add(c);
    }

    public void addYellowLetter(char c) {
        this.yellowLetters.add(c);
    }

    public void addGreenLetter(char c) {
        this.greenLetters.add(c);
    }

    public void addDuplicate(char c) {this.duplicates.add(c);}

    public void addYellowIndex(int i, char c) { this.yellowList.get(i).add(c); }

    public void addGreenIndex(int i, char c) { this.greenList.get(i).add(c); }

    public void addSingle(char c) { this.singles.add(c); }

    // duplicate code - might be a way to consolidate into one check method
    public boolean checkYellow(Set<Character> set, String st) {
        for (char c : set) {
            if (st.indexOf(c) < 0) return false;
        }
        return true;
    }

    public boolean checkGreen(Set<Character> set, String st) {
        for (char c : set) {
            if (st.indexOf(c) < 0) return false;
        }
        return true;
    }

    public String getBest() {
        return this.best;
    }

    //boolean 'check' methods return true if the passed word should be removed
    private boolean checkGreenYellowLetters(String s) { // if s doesn't contain all yellow and green letters it is removed
        return !checkYellow(this.yellowLetters, s) || !checkGreen(this.greenLetters, s);
    }

    private boolean checkIndex(String s) { // if s has letters in the wrong index it is removed
        for (int i = 0; i < s.length(); i++) {
            if (yellowList.get(i).contains(s.charAt(i)) ||
                    (!greenList.get(i).isEmpty() && !greenList.get(i).contains(s.charAt(i)))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkBlackAndSingleLetters(String s) {
        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            if(blackLetters.contains(s.charAt(i))) {
                if(!greenLetters.contains(s.charAt(i)) && !yellowLetters.contains(s.charAt(i))) { // letter is black and solution contains only 1
                    return true;
                } if(checkSingles(s.charAt(i), false)) { // remove words with duplicate letters
                    flag = countSingles(s);
                }
            }
        }
        return flag;
    }

    public boolean checkSingles(char c, boolean isBlack) {
        if (isBlack) {
            return yellowLetters.contains(c) || greenLetters.contains(c);
        }
        return blackLetters.contains(c);
    }

    public boolean countSingles(String s) {
        for(char c : singles) {
            int count = 0;
            for(int i = 0; i < s.length(); i++) {
                if(s.charAt(i) == c) {
                    count++;
                }
            }
            if(count > 1) {
//                System.out.println(s + " contains " + count + " of the letter " + c);
                return true;
            }
        }
        return false;
    }

    public boolean checkDuplicates(char c) {
        return yellowLetters.contains(c) || greenLetters.contains(c);
    }

    private boolean countDuplicates(String s) {
        for(char c : duplicates) {
            int count = 0;
            for(int i = 0; i < s.length(); i++) {
                if(s.charAt(i) == c) count++;
            }
            if(count < 2) {
                return true;
            }
        }
        return false;
    }

    public int processResult(String guess, String result) {
        int greenCount = 0;
        for (int i = 0; i < result.length(); i++) {
            switch (result.charAt(i)) {
                case 'B':
                    this.addBlackLetter(guess.charAt(i));
                    if(this.checkSingles(guess.charAt(i), true)) {
                        this.addSingle(guess.charAt(i));
                    }
//                    System.out.println("adding " + guess.charAt(i) + " to black letters");
//                    System.out.println(this.blackLetters);
                    break;
                case 'Y':
                    if(this.checkDuplicates(guess.charAt(i))) {
                        this.addDuplicate(guess.charAt(i));
                    }
                    if(this.checkSingles(guess.charAt(i), false)) {
                        this.addSingle(guess.charAt(i));
                    }
                    this.addYellowIndex(i, guess.charAt(i));
                    this.addYellowLetter(guess.charAt(i));
                    break;
                case 'G':
                    if(this.checkDuplicates(guess.charAt(i))) {
                        this.addDuplicate(guess.charAt(i));
                    }
                    if(this.checkSingles(guess.charAt(i), false)) {
                        this.addSingle(guess.charAt(i));
                    }
                    this.addGreenIndex(i, guess.charAt(i));
                    this.addGreenLetter(guess.charAt(i));
                    // TODO: THIS MIGHT BE A BUG. TRYING TO SOLVE FOLLOWING ISSUE:
                    //  s l ate (b y bbg) -> be ll e (gb bg g)
                    //  [l] was added to duplicates and singles, but should only be in singles
//                    yellowLetters.remove(guess.charAt(i));
                    greenCount++;
                    break;
                default:
                    break;
            }
        }

        if(this.master.size() < 2) return greenCount;

        System.out.println(this.blackLetters + "\n" + this.yellowLetters + "\n" + this.greenLetters);
        System.out.println("duplicates " + this.duplicates + "\n" + "singles: " + this.singles);
        this.update();
        return greenCount;
    }

    public void update() {
        master.removeIf(s -> checkGreenYellowLetters(s) || checkIndex(s) || checkBlackAndSingleLetters(s) || countDuplicates(s));

        this.greenLetters = new HashSet<>();
        this.yellowLetters = new HashSet<>();
        this.blackLetters = new HashSet<>();
        //TODO -> Keep old code until more thorough testing has been done
//        for (Iterator<String> iterator = master.iterator(); iterator.hasNext(); ) {
//            String st = iterator.next();
//            for (int i = 0; i < st.length(); i++) {
//                if (!checkYellow(this.yellowLetters, st) || !checkGreen(this.greenLetters, st)) {
//                    iterator.remove();
//                    dict.remove(st);
//                    break;
//                }
//                if (blackLetters.contains(st.charAt(i))) {
//                    if (!greenLetters.contains(st.charAt(i)) && !yellowLetters.contains(st.charAt(i))) {
//                        iterator.remove();
//                        dict.remove(st);
//                        break;
//                    } else if (yellowLetters.contains(st.charAt(i))) {
//                        char target = st.charAt(i);
//                        for (Iterator<String> multIterator = master.iterator(); multIterator.hasNext(); ) {
//                            int count = 0;
//                            String s2 = multIterator.next();
//                            for (int j = 0; j < s2.length(); j++) {
//                                if (s2.charAt(j) == target) count++;
//                            }
//                            if (count > 1) {
//                                iterator.remove();
//                                dict.remove(s2);
//                                break;
//                            }
//                        }
//                    }
//                } else if (yellowList.get(i).contains(st.charAt(i))) {
//                    iterator.remove();
//                    dict.remove(st);
//                    break;
//                } else if (!greenList.get(i).isEmpty() && !greenList.get(i).contains(st.charAt(i))) {
//                    iterator.remove();
//                    dict.remove(st);
//                    break;
//
//                }
//            }
//        }
        this.populate();
    }
}
