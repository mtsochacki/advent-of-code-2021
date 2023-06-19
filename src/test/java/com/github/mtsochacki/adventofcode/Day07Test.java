package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day07Test {
    private final Day07 underTest = new Day07();
    private static final List<String> INPUT = List.of("16,1,2,0,4,2,7,1,2,14");

    @Test
    void test1() {
        String expected = "37";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "168";

        String result = underTest.part2(INPUT);

        assertThat(result).isEqualTo(expected);
    }
}