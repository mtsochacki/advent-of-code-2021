package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {
    private final Day12 underTest = new Day12();
    private static final List<String> INPUT = List.of(
            "dc-end",
            "HN-start",
            "start-kj",
            "dc-start",
            "dc-HN",
            "LN-dc",
            "HN-end",
            "kj-sa",
            "kj-HN",
            "kj-dc"
    );

    @Test
    void test1() {
        String expected = "19";

        String result = underTest.part1(INPUT);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "103";

        String result = underTest.part2(INPUT);

        assertThat(result).isEqualTo(expected);
    }
}