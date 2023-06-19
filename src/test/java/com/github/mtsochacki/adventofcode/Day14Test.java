package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day14Test {
    private final Day14 underTest = new Day14();
    private static final List<String> INPUT = List.of(
            "NNCB",
            "",
            "CH -> B",
            "HH -> N",
            "CB -> H",
            "NH -> C",
            "HB -> C",
            "HC -> B",
            "HN -> C",
            "NN -> C",
            "BH -> H",
            "NC -> B",
            "NB -> B",
            "BN -> B",
            "BB -> N",
            "BC -> B",
            "CC -> N",
            "CN -> C"
    );

    @Test
    void test1() {
        String expected = "1588";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "2188189693529";

        String result = underTest.part2(INPUT);

        assertThat(result).isEqualTo(expected);
    }
}