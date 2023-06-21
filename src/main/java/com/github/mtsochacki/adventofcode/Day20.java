package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day20 implements Day {
    @Override
    public String part1(List<String> input) {
        return String.valueOf(countLightPixels(2, input));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(countLightPixels(50, input));
    }

    private int countLightPixels(int steps, List<String> input) {
        String algorithm = readAlgorithm(input);
        List<List<Character>> image = readImage(input);
        for (int i = 0; i < steps * 2; i++) {
            image = surroundWithDots(image);
        }
        for (int i = 0; i < steps; i++) {
            image = surroundWithDots(image);
            List<List<Character>> tmpImage = new ArrayList<>();
            for (List<Character> line : image) {
                List<Character> tmpLine = new ArrayList<>(line);
                tmpImage.add(tmpLine);
            }
            for (int y = 1; y < tmpImage.size() - 1; y++) {
                for (int x = 1; x < tmpImage.get(0).size() - 1; x++) {
                    tmpImage.get(y).set(x, algorithm.charAt(determineOutputPixelIndex(image, x, y)));
                }
            }
            image = tmpImage;
        }
        int count = 0;
        for (int y = steps * 2; y < image.size() - steps * 2; y++) {
            for (int x = steps * 2; x < image.get(0).size() - steps * 2; x++) {
                if (image.get(y).get(x) == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    private String readAlgorithm(List<String> input) {
        return input.get(0);
    }

    private List<List<Character>> readImage(List<String> input) {
        return input.stream()
                .skip(2)
                .map(this::stringToList)
                .toList();
    }

    private List<List<Character>> surroundWithDots(List<List<Character>> image) {
        List<List<Character>> newImage = new ArrayList<>();
        newImage.add(new ArrayList<>());
        for (List<Character> line : image) {
            ArrayList<Character> newLine = new ArrayList<>();
            newLine.add('.');
            newLine.addAll(line);
            newLine.add('.');
            newImage.add(newLine);
        }
        newImage.add(new ArrayList<>());
        for (int i = 0; i < newImage.get(1).size(); i++) {
            newImage.get(0).add('.');
            newImage.get(newImage.size() - 1).add('.');
        }
        return newImage;
    }

    private int determineOutputPixelIndex(List<List<Character>> image, int x, int y) {
        StringBuilder stringIndex = new StringBuilder();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                stringIndex.append(image.get(y + i).get(x + j));
            }
        }
        String binaryString = stringIndex.toString().replace("#", "1").replace(".", "0");
        return Integer.parseInt(binaryString, 2);
    }

    private List<Character> stringToList(String line) {
        List<Character> result = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            result.add(line.charAt(i));
        }
        return result;
    }
}