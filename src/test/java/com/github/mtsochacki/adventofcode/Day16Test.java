package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day16Test {
    private final Day16 underTest = new Day16();
    private static final List<String> INPUT1 = List.of(
            "A0016C880162017C3686B18A3D4780"
    );

    private static final List<String> INPUT2 = List.of(
            "04005AC33890"
    );

    @Test
    void test1() {
        String expected = "31";

        String result = underTest.part1(INPUT1);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "54";

        String result = underTest.part2(INPUT2);

        assertThat(result).isEqualTo(expected);
    }

}