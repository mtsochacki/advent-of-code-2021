package com.aoc2021;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        int day = 1;

        Map<Integer, Day> dayMap = new HashMap<>();
        dayMap.put(1, new Day01());
        dayMap.put(2, new Day02());
        dayMap.put(3, new Day03());
        dayMap.put(4, new Day04());
        dayMap.put(5, new Day05());
        dayMap.put(6, new Day06());
        dayMap.put(7, new Day07());
        dayMap.put(8, new Day08());
        dayMap.put(9, new Day09());
        dayMap.put(10, new Day10());
        dayMap.put(11, new Day11());
        dayMap.put(12, new Day12());
        dayMap.put(13, new Day13());
        dayMap.put(14, new Day14());
        dayMap.put(15, new Day15());
        dayMap.put(16, new Day16());
        dayMap.put(17, new Day17());
        dayMap.put(20, new Day20());
        dayMap.put(21, new Day21());

        DataDownloader dataDownloader = new DataDownloader();

        dataDownloader.downloadData(day);
        Day solution = dayMap.get(day);

        TimeUnit.SECONDS.sleep(1);

        System.out.println(solution.part1("src/main/resources/day" + day + ".txt"));
        System.out.println(solution.part2("src/main/resources/day" + day + ".txt"));
    }
}