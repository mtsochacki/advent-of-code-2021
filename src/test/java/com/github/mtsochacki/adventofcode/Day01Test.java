package com.github.mtsochacki.adventofcode;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    private final Day01 day01 = new Day01();

    @Test
    void testCalculatingPart1() throws URISyntaxException {
        String expected = "7";
        URL res = getClass().getClassLoader().getResource("input1.txt");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();

        String result = day01.part1(absolutePath);

        assertEquals(expected, result);
    }

    @Test
    void testCalculatingPart2() throws URISyntaxException {
        String expected = "5";
        URL res = getClass().getClassLoader().getResource("input1.txt");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();

        String result = day01.part2(absolutePath);

        assertEquals(expected, result);
    }
}