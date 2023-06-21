package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day09Test {
    private final Day09 underTest = new Day09();
    private static final List<String> INPUT = List.of("2199943210", "3987894921", "9856789892", "8767896789", "9899965678");

    @Test
    void test1() {
        String expected = "15";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "1134";

        String result = underTest.part2(INPUT);

        assertThat(result).isEqualTo(expected);
    }

}