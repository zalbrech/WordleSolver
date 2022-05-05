package com.company;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {
    Solver solver = new Solver();
    Set<Character> singles = new HashSet<>();
    Set<Character> duplicates = new HashSet<>();
    int first = 0, second = 0;

    @BeforeAll
    static void setup() {

    }

    @Before
    public void init() {
        solver = new Solver();
        first = solver.processResult("sassy","GYBBB");
        second = solver.processResult("malls","BYYGY");
        singles = solver.getSingles();
        duplicates = solver.getDuplicates();
//        System.out.println(this.singles);
//        System.out.println("LLLLJLSKDFJSFLKSDJ");
    }

    @Test
    public void checkSingles() {
        assertTrue(this.singles.contains('s'));
    }
//
    @Test
    public void checkDuplicates() {
        assertTrue(this.duplicates.contains('l'));
    }

    @Test
    public void processResult() {
        assertAll(() -> assertEquals(1,first),
                  () -> assertEquals(1,second));
    }
}