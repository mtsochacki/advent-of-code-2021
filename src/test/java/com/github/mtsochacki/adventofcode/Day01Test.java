package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test {

    private final Day01 underTest = new Day01();
    private static final List<String> INPUT = List.of("199", "200", "208", "210", "200", "207", "240", "269", "260", "263");

    @Test
    void testCalculatingPart1() {
        String expected = "7";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testCalculatingPart2() {
        String expected = "5";

        String result = underTest.part2(INPUT);

        assertThat(result).isEqualTo(expected);
    }
}