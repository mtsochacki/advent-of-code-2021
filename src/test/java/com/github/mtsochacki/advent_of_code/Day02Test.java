package com.github.mtsochacki.advent_of_code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {
    Day underTest = new Day02();

    @Test
    @DisplayName("Should return correct value for part1")
    void test1() {
        //when
        String receivedAnswer = underTest.part1("src/test/resources/day2test.txt");
        //then
        assertEquals("150", receivedAnswer);
    }

    @Test
    @DisplayName("Should return correct value for part2")
    void test2() {
        //when
        String receivedAnswer = underTest.part2("src/test/resources/day2test.txt");
        //then
        assertEquals("900", receivedAnswer);
    }
}