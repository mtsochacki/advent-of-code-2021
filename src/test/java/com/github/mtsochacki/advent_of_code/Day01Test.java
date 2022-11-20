package com.github.mtsochacki.advent_of_code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day01Test {
    Day underTest = new Day01();

    @Test
    @DisplayName("Should return correct value for part1")
    void test1() {
        //when
        String receivedAnswer = underTest.part1("src/test/resources/day1test.txt");
        //then
        assertEquals("7", receivedAnswer);
    }

    @Test
    @DisplayName("Should return correct value for part2")
    void test2() {
        //when
        String receivedAnswer = underTest.part2("src/test/resources/day1test.txt");
        //then
        assertEquals("5", receivedAnswer);
    }
}