package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test {
    private final Day25 underTest = new Day25();
    private static final List<String> INPUT = List.of(
            "v...>>.vv>",
            ".vv>>.vv..",
            ">>.>v>...v",
            ">>v>>.>.v.",
            "v>v.vv.v..",
            ">.>>..v...",
            ".vv..>.>v.",
            "v.v..>>v.v",
            "....v..v.>"
    );

    @Test
    void test1() {
        String expected = "58";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }
}