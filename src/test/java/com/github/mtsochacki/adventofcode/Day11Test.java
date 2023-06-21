package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {
    private final Day11 underTest = new Day11();
    private static final List<String> INPUT = List.of(
            "5483143223",
            "2745854711",
            "5264556173",
            "6141336146",
            "6357385478",
            "4167524645",
            "2176841721",
            "6882881134",
            "4846848554",
            "5283751526"
    );

    @Test
    void test1() {
        String expected = "1656";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "195";

        String result = underTest.part2(INPUT);

        assertThat(result).isEqualTo(expected);
    }
}