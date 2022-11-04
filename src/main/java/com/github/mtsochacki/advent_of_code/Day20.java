package com.github.mtsochacki.advent_of_code;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Day20 {
    public static String readAlgorithm(String filename) {
        String algorithm = null;
        try {
            Scanner sc = new Scanner(new File(filename));
            algorithm = sc.nextLine();
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        }
        return algorithm;
    }

    public static ArrayList<ArrayList<Character>> readImage(String filename) {
        ArrayList<ArrayList<Character>> image = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(filename));
            sc.nextLine();
            sc.nextLine();
            sc.useDelimiter("");
            while (sc.hasNextLine()) {
                image.add(stringToList(sc.nextLine()));
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        }
        return image;
    }

    public static ArrayList<ArrayList<Character>> surroundWithDots(ArrayList<ArrayList<Character>> image) {
        ArrayList<ArrayList<Character>> newImage = new ArrayList<>();
        newImage.add(new ArrayList<>());
        for (ArrayList<Character> line : image) {
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

    public static ArrayList<Character> stringToList(String line) {
        ArrayList<Character> result = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            result.add(line.charAt(i));
        }
        return result;
    }

    public static int determineOutputPixelIndex(ArrayList<ArrayList<Character>> image, int x, int y) {
        StringBuilder stringIndex = new StringBuilder();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                stringIndex.append(image.get(y + i).get(x + j));
            }
        }
        String binaryString = stringIndex.toString().replace("#", "1").replace(".", "0");
        return Integer.parseInt(binaryString, 2);
    }

    public static int countLightPixels(int steps) {
        String algorithm = readAlgorithm("/Users/mateusz/Java/advent-of-code/2021/day20/data.txt");
        ArrayList<ArrayList<Character>> image = readImage("/Users/mateusz/Java/advent-of-code/2021/day20/data.txt");
        for (int i = 0; i < steps * 2; i++) {
            image = surroundWithDots(image);
        }
        for (int i = 0; i < steps; i++) {
            image = surroundWithDots(image);
            ArrayList<ArrayList<Character>> tmpImage = new ArrayList<>();
            for (ArrayList<Character> line : image) {
                ArrayList<Character> tmpLine = new ArrayList<>(line);
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

    public static void main(String[] args) {
        System.out.println(countLightPixels(2));
        System.out.println(countLightPixels(50));
    }
}