package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class Day04 implements Day {

    @Override
    public String part1(List<String> input) {
        return calculateBoard(true, input);
    }

    @Override
    public String part2(List<String> input) {
        return calculateBoard(false, input);
    }

    private List<List<List<Integer>>> readBoards(List<String> input) {
        final AtomicInteger counter = new AtomicInteger();
        return input.stream()
                .skip(1)
                .filter(it -> !it.isEmpty())
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 5))
                .values()
                .stream()
                .map(this::transformIntoBoard)
                .toList();
    }

    private List<List<Integer>> transformIntoBoard(List<String> list) {
        return list.stream()
                .map(this::transformLineIntoNumbers)
                .toList();
    }

    private boolean boardHasWon(List<List<Integer>> board) {
        for (List<Integer> row : board) {
            Set<Integer> rowValues = new HashSet<>(row);
            if (rowValues.size() == 1) {
                return true;
            }
        }
        for (int col = 0; col < 5; col++) {
            Set<Integer> columnValues = new HashSet<>();
            for (List<Integer> row : board) {
                columnValues.add(row.get(col));
            }
            if (columnValues.size() == 1) {
                return true;
            }
        }
        return false;
    }

    private int countWin(List<List<Integer>> board, List<Integer> drawn) {
        int counter = 0;
        for (int number : drawn) {
            counter++;
            for (int row = 0; row < 5; ++row) {
                for (int col = 0; col < 5; ++col) {
                    if (board.get(row).get(col) == number) {
                        board.get(row).set(col, 0);
                    }
                }
            }
            if (boardHasWon(board)) {
                break;
            }
        }
        return counter;
    }

    private String calculateBoard(boolean isWinner, List<String> input) {
        List<Integer> drawn = readDrawnNumbers(input);
        List<List<List<Integer>>> boards = readBoards(input);
        List<List<Integer>> winningBoard = new ArrayList<>();
        int currentCounter;
        int winnersCounter;
        winnersCounter = isWinner ? 999999 : 0;
        for (List<List<Integer>> board : boards) {
            currentCounter = countWin(board, drawn);
            if (((currentCounter < winnersCounter) && isWinner)
                    || ((currentCounter > winnersCounter) && !isWinner)) {
                winningBoard = board;
                winnersCounter = currentCounter;
            }
        }
        int score = calculateScore(winningBoard);
        return String.valueOf(score * drawn.get(winnersCounter - 1));
    }

    private int calculateScore(List<List<Integer>> board) {
        return board.stream()
                .map(row -> row.stream().reduce(0, Integer::sum))
                .reduce(0, Integer::sum);
    }

    private List<Integer> readDrawnNumbers(List<String> input) {
        String firstLine = input.get(0);
        return transformLineIntoNumbers(firstLine);
    }

    private List<Integer> transformLineIntoNumbers(String line) {
        String[] numbers = line.split("\\s+|,");
        return Arrays.stream(numbers).map(Integer::valueOf).collect(Collectors.toList());
    }
}
