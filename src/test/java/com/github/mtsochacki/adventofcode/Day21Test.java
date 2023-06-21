package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day21Test {
    private final Day21 underTest = new Day21();
    private static final List<String> INPUT = List.of(
            "Player 1 starting position: 4",
            "Player 2 starting position: 8"
    );

    @Test
    void test1() {
        String expected = "739785";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "444356092776315";

        String result = underTest.part2(INPUT);

        assertThat(result).isEqualTo(expected);
    }
}