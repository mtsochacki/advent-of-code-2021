package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day06Test {
    private final Day06 underTest = new Day06();
    private static final List<String> INPUT = List.of("3,4,3,1,2");

    @Test
    void test1() {
        String expected = "5934";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "26984457539";

        String result = underTest.part2(INPUT);

        assertThat(result).isEqualTo(expected);
    }
}