package com.company;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TesterTest {
    Tester tester = new Tester("stall");

    // Test that the proper result string is returned
    // Test cases:
    // 1) All green
    // 2) All yellow
    // 3) Singles
    // 4) Duplicates
    @DisplayName("Process guess input")
    @Test
    public void processGuess() {
        assertAll(() -> assertEquals("GGGGG", tester.processGuess("stall")),
                () -> assertEquals("YYYYY", tester.processGuess("tllsa")),
                () -> assertEquals("GYBBB", tester.processGuess("sassy")),
                () -> assertEquals("YBYYY", tester.processGuess("lilts")),
                () -> assertEquals("BYYGY", tester.processGuess("malls"))
        );
    }
}