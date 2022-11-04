package com.github.mtsochacki.advent_of_code;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day21 {
    public static class Dice {
        int number = -1;
        int rolls = 0;

        int roll() {
            number += 1;
            rolls++;
            return number % 100 + 1;
        }
    }

    public static class GameResults {
        long wins1;
        long wins2;

        public GameResults(long wins1, long wins2) {
            this.wins1 = wins1;
            this.wins2 = wins2;
        }

        public void setBothWins(long wins1, long wins2) {
            this.wins1 = wins1;
            this.wins2 = wins2;
        }
    }

    public static class GameState {
        int position1;
        int position2;
        int score1;
        int score2;

        public GameState(int position1, int position2, int score1, int score2) {
            this.position1 = position1;
            this.position2 = position2;
            this.score1 = score1;
            this.score2 = score2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameState gameState = (GameState) o;
            return position1 == gameState.position1 && position2 == gameState.position2 && score1 == gameState.score1 && score2 == gameState.score2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(position1, position2, score1, score2);
        }
    }

    static Map<GameState, GameResults> storedResults = new HashMap<>();

    static int part1() {
        int p1Score = 0;
        int p2Score = 0;
        int p1Position = 4;
        int p2Position = 10;

        Dice dice = new Dice();

        while (true) {
            p1Position = (p1Position + dice.roll() + dice.roll() + dice.roll() - 1) % 10 + 1;
            p1Score += p1Position;
            if (p1Score >= 1000)
                return p2Score * dice.rolls;
            p2Position = (p2Position + dice.roll() + dice.roll() + dice.roll() - 1) % 10 + 1;
            p2Score += p2Position;
            if (p2Score >= 1000)
                return p1Score * dice.rolls;
        }
    }

    static GameResults countWins(GameState gameState) {
        if (gameState.score1 >= 21)
            return new GameResults(1, 0);
        if (gameState.score2 >= 21)
            return new GameResults(0, 1);
        if (storedResults.containsKey(gameState))
            return storedResults.get(gameState);
        GameResults gameResults = new GameResults(0, 0);
        for (int d1 = 1; d1 < 4; d1++) {
            for (int d2 = 1; d2 < 4; d2++) {
                for (int d3 = 1; d3 < 4; d3++) {
                    int newPosition1 = (gameState.position1 + d1 + d2 + d3 - 1) % 10 + 1;
                    int newScore1 = gameState.score1 + newPosition1;
                    GameState tmpGameState = new GameState(gameState.position2, newPosition1, gameState.score2, newScore1);
                    GameResults tmpGameResults = countWins(tmpGameState);
                    gameResults.setBothWins(gameResults.wins1 + tmpGameResults.wins2, gameResults.wins2 + tmpGameResults.wins1);
                }
            }
        }
        storedResults.put(gameState, gameResults);
        return gameResults;
    }

    static long part2() {
        GameState initialGameState = new GameState(4, 10, 0, 0);
        GameResults gameResults = countWins(initialGameState);
        return Math.max(gameResults.wins1, gameResults.wins2);
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}