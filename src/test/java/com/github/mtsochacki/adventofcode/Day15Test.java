package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test {
    private final Day15 underTest = new Day15();
    private static final List<String> INPUT = List.of(
            "1163751742",
            "1381373672",
            "2136511328",
            "3694931569",
            "7463417111",
            "1319128137",
            "1359912421",
            "3125421639",
            "1293138521",
            "2311944581"
    );

    @Test
    void test1() {
        String expected = "40";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "315";

        String result = underTest.part2(INPUT);

        assertThat(result).isEqualTo(expected);
    }
}