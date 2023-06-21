package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {
    private final Day13 underTest = new Day13();
    private static final List<String> INPUT = List.of(
            "6,10",
            "0,14",
            "9,10",
            "0,3",
            "10,4",
            "4,11",
            "6,0",
            "6,12",
            "4,1",
            "0,13",
            "10,12",
            "3,4",
            "3,0",
            "8,4",
            "1,10",
            "2,14",
            "8,10",
            "9,0",
            "",
            "fold along y=7",
            "fold along x=5"
    );

    @Test
    void test1() {
        String expected = "17";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "103";

        String result = underTest.part2(INPUT);

        assertThat(result).isEqualTo(expected);
    }

}