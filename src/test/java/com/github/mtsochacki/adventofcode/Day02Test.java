package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day02Test {
    private final Day02 underTest = new Day02();
    private static final List<String> INPUT = List.of("forward 5", "down 5", "forward 8", "up 3", "down 8", "forward 2");

    @Test
    void test1() {
        String expected = "150";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "900";

        String result = underTest.part2(INPUT);

        assertThat(result).isEqualTo(expected);
    }
}