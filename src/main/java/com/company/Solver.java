//TODO: organize method order
//TODO: clean up comments

package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.*;

public class Solver {

    private final char[] ALPHABET = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    private Set<Character> blackLetters; // if blackLetters contains letter in word, discard it
    private Set<Character> yellowLetters; // candidate must contain all yellow letters
    private Set<Character> greenLetters; // candidate must contain all green letters


    private List<Set<Character>> yellowList;
    private List<Set<Character>> greenList;
    private List<Set<Character>> blackList;
    private List<Map<Character, Integer>> posList;

    private List<String> top25Guesses;


    private Map<String, Integer> dict;
    private Map<Character, Integer> freq;
    private final Set<String> master;

    private Set<Character> duplicates;
    private Set<Character> singles;

    private final PriorityQueue<Map.Entry<Character, Integer>> freqPq;

    private final Set<String> allWords;
    private Map<String, Integer> allWordsDict;

    private String best;

    public Solver() {
        this.blackLetters = new HashSet<>(); // if blackLetters contains letter in word, discard it
        this.yellowLetters = new HashSet<>(); // candidate must contain all yellow letters
        this.greenLetters = new HashSet<>(); // candidate must contain all green letters

//        System.out.println("Letters lists created");

        // List of letters for each index with respect to their color
        this.yellowList = List.of(new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>());
        this.greenList = List.of(new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>());
        this.blackList = List.of(new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>());

//        System.out.println("Index lists created");



        this.freq = new HashMap<>(); // count frequency of each letter
        this.master = new HashSet<>(); // master list of possible words, used to repopulate dict & freq after each guess

        this.freqPq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        this.duplicates = new HashSet<>();
        this.singles = new HashSet<>();

        this.allWords = new HashSet<>();

//        System.out.println("All words created");

        String s;
        // populate master list
        this.populateMaster();
        this.populateAllWords();
        this.populateLists();
        this.findBest();
    }


    private void populateAllWords() {
        String s;
        try(BufferedReader input = new BufferedReader(new FileReader("src/main/java/allwords.txt"))) {
            while ((s = input.readLine()) != null) {
                allWords.add(s);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void populateMaster() {
//        System.out.println("In populateMaster()");
        String s;
        try {
            BufferedReader input = new BufferedReader(new FileReader("src/main/java/words.txt"));
            while ((s = input.readLine()) != null) {
                master.add(s);
//                System.out.println(s + " added");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("Populate master finished");
    }

    private void populateLists() {
//        System.out.println("In populateLists()");
        this.dict = new HashMap<>(); // list of possible words with scores
        this.allWordsDict = new HashMap<>();
        this.top25Guesses = new ArrayList<>();
        this.freq = new HashMap<>();


        this.posList = List.of(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());

        // initialize to avoid error from words starting with 'x'
        for(Map<Character,Integer> m : posList) {
            for(char c : ALPHABET) {
                m.put(c,0);
            }
        }

//        System.out.println("Lists created");


        for (String s : this.master) {
            for (int i = 0; i < s.length(); i++) {
                // count index position of each letter
                this.posList.get(i).put(s.charAt(i), this.posList.get(i).getOrDefault(s.charAt(i), 0) + 1);
                // count frequency of each letter
                this.freq.put(s.charAt(i), this.freq.getOrDefault(s.charAt(i), 0) + 1);
//                this.allList.get(i).put(s.charAt(i), this.allList.get(i).getOrDefault(s.charAt(i), 0) + 1); // count index position of each letter

            }
        }
//        for (String s : this.allWords) {
//            for(int i = 0; i < s.length(); i++) {
//                this.posList.get(i).put(s.charAt(i), this.posList.get(i).getOrDefault(s.charAt(i), 0) + 1); // count index position of each letter
//            }
//        }
        for (String s : this.master) {
            // add word to dictionary with relevant score
            dict.put(s, this.score(s));
        }

//        System.out.println("master scored");


        for (String s : this.allWords) {
//            System.out.println(s);
            allWordsDict.put(s, this.score(s));
        }

//        System.out.println("Scoring completed");

        PriorityQueue<Map.Entry<String, Integer>> pqScore = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        for(Map.Entry<String, Integer> e : allWordsDict.entrySet()) {
            pqScore.offer(e);
            if(pqScore.size() > 25) pqScore.remove();
        }

        for(Map.Entry<String, Integer> e : pqScore) {
            this.top25Guesses.add(e.getKey());
        }


//        System.out.println(this.master);
//        this.printScores(allWordsDict);

        //TODO: UNCOMMENT findBest()!!!!
    }

    private int score(String s) {
        int score = 0;
        for (int i = 0; i < s.length(); i++) {
//            score += allList.get(i).get(s.charAt(i));
            score += posList.get(i).get(s.charAt(i));
        }
        return score;
    }

    // internal method for testing only
    private void printScores(Map<String, Integer> map) {
        PriorityQueue<Map.Entry<String, Integer>> pqScore = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        for(Map.Entry<String, Integer> e : map.entrySet()) {
            pqScore.offer(e);
            if(pqScore.size() > 25) pqScore.remove();
        }

        while(!pqScore.isEmpty()) {
            System.out.println(pqScore.remove());
        }
    }

    public Set<String> getMaster() {
        return this.master;
    }

    public List<String> getTop25Guesses() {
        return this.top25Guesses;
    }

    // uses the scoring algorithm to compare all remaining possible valid guesses
    private String findBest() {

//        this.best = Collections.min(dict.entrySet(), Map.Entry.comparingByValue()).getKey();
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

    public int getMasterSize() {
        return this.master.size();
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

    public void addBlackIndex(int i, char c) { this.blackList.get(i).add(c); }

    public void addYellowIndex(int i, char c) { this.yellowList.get(i).add(c); }

    public void addGreenIndex(int i, char c) { this.greenList.get(i).add(c); }

    public void addSingle(char c) { this.singles.add(c); }

    public Set<Character> getSingles() { return new HashSet<>(this.singles); }

    public Set<Character> getDuplicates() { return new HashSet<>(this.duplicates); }

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

    // if passed String s has letters in the wrong index it is removed
    private boolean checkIndex(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (yellowList.get(i).contains(s.charAt(i)) ||
                blackList.get(i).contains(s.charAt(i)) ||
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
                } if(checkSingles(s.charAt(i))) { // remove words with duplicate letters
                    flag = countSingles(s);
                }
            }
        }
        return flag;
    }

    public boolean checkSingles(char c) {
        if(duplicates.contains(c)) return false;
        return blackLetters.contains(c) && (greenLetters.contains(c) || yellowLetters.contains(c));
    }

//    public boolean checkSingles(char c, boolean isBlack) {
//        if (isBlack) {
//            return yellowLetters.contains(c) || greenLetters.contains(c);
//        }
//        return blackLetters.contains(c);
//    }

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

    // processes result return from previous guess
    public int processResult(String guess, String result) {
        int greenCount = 0;
        for (int i = 0; i < result.length(); i++) {
            switch (result.charAt(i)) {
                case 'B':
                    this.addBlackLetter(guess.charAt(i));
                    this.addBlackIndex(i, guess.charAt(i));
                    break;
                case 'Y':
                    if(this.checkDuplicates(guess.charAt(i))) {
                        this.addDuplicate(guess.charAt(i));
                    }
                    this.addYellowIndex(i, guess.charAt(i));
                    this.addYellowLetter(guess.charAt(i));
                    break;
                case 'G':
                    if(this.checkDuplicates(guess.charAt(i))) {
                        this.addDuplicate(guess.charAt(i));
                    }
                    this.addGreenIndex(i, guess.charAt(i));
                    this.addGreenLetter(guess.charAt(i));
                    greenCount++;
                    break;
                default:
                    break;
            }
        }
        for(int i = 0; i < guess.length(); i++) {
            if(this.checkSingles(guess.charAt(i))) {
                this.addSingle(guess.charAt(i));
            }
        }

//        System.out.println("duplicates: " + this.duplicates);
//        System.out.println("singles: " + this.singles);

        if(this.master.size() < 2) return greenCount;


//        System.out.println(this.blackLetters + "\n" + this.yellowLetters + "\n" + this.greenLetters);
//        System.out.println("duplicates " + this.duplicates + "\n" + "singles: " + this.singles);

        this.update();
        return greenCount;
    }

    //TODO: this architecture is breaking SolverTest
    public void update() {
        master.removeIf(s -> checkGreenYellowLetters(s) || checkIndex(s) || checkBlackAndSingleLetters(s) || countDuplicates(s));

        this.greenLetters = new HashSet<>();
        this.yellowLetters = new HashSet<>();
        this.blackLetters = new HashSet<>();

        this.yellowList = List.of(new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>());
        this.greenList = List.of(new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>());
        this.blackList = List.of(new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>());

        this.duplicates = new HashSet<>();
        this.singles = new HashSet<>();

        this.populateLists();
        this.findBest();
    }


}
