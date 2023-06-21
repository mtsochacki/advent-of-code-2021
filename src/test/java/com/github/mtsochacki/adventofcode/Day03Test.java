package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day03Test {
    private final Day03 underTest = new Day03();
    private static final List<String> INPUT = List.of("00100", "11110", "10110", "10111", "10101", "01111", "00111", "11100", "10000", "11001", "00010", "01010");

    @Test
    void test1() {
        String expected = "198";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "230";

        String result = underTest.part2(INPUT);

        assertThat(result).isEqualTo(expected);
    }
}