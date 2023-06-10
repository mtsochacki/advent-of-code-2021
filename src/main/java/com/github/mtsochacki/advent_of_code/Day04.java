package com.github.mtsochacki.advent_of_code;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Day04 implements Day {
    private List<Integer> readDrawnNumbers(String filename) {
        List<Integer> drawnNumbers = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            sc.useDelimiter(",|\n");
            while (sc.hasNextInt()) {
                drawnNumbers.add(sc.nextInt());
            }
        } catch (Exception e) {
            log.error("Something went horribly wrong: {}", e.getMessage());
        }
        return drawnNumbers;
    }

    public String part1() {
        return calculateBoard(true);
    }

    public String part2() {
        return calculateBoard(false);
    }

    private List<List<List<Integer>>> readBoards(String filename) {
        List<List<List<Integer>>> boards = new ArrayList<>();
        List<List<Integer>> board;
        try (Scanner sc = new Scanner(new File(filename))) {
            sc.nextLine();
            while (sc.hasNext()) {
                sc.nextLine();
                board = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    ArrayList<Integer> row = new ArrayList<>();
                    for (int j = 0; j < 5; j++) {
                        row.add(sc.nextInt());
                    }
                    board.add(row);
                }
                boards.add(board);
            }
        } catch (Exception e) {
            log.error("Something went horribly wrong: {}", e.getMessage());
        }
        return boards;
    }

    private boolean boardHasWon(List<List<Integer>> board) {
        for (List<Integer> row : board) {
            boolean won = true;
            for (int col = 0; col < 5; col++) {
                if (row.get(col) != 0) {
                    won = false;
                    break;
                }
            }
            if (won) {
                return true;
            }
        }
        for (int col = 0; col < 5; col++) {
            boolean won = true;
            for (List<Integer> row : board) {
                if (row.get(col) != 0) {
                    won = false;
                    break;
                }
            }
            if (won) {
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
                    if (board.get(col).get(row) == number) {
                        board.get(col).set(row, 0);
                    }
                }
            }
            if (boardHasWon(board)) {
                break;
            }
        }
        return counter;
    }

    private String calculateBoard(boolean isWinner) {
        List<Integer> drawn = readDrawnNumbers("data.txt");
        List<List<List<Integer>>> boards = readBoards("data.txt");
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
        int score = 0;
        for (List<Integer> row : winningBoard) {
            for (int i = 0; i < 5; i++) {
                score += row.get(i);
            }
        }
        return String.valueOf(score * drawn.get(winnersCounter - 1));
    }
}
