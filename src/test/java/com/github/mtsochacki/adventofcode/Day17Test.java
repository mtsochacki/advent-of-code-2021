package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test {
    private final Day17 underTest = new Day17();
    private static final List<String> INPUT = List.of(
            "target area: x=20..30, y=-10..-5"
    );

    @Test
    void test1() {
        String expected = "45";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "112";

        String result = underTest.part2(INPUT);

        assertThat(result).isEqualTo(expected);
    }
}