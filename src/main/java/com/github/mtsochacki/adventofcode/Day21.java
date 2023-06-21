package com.github.mtsochacki.adventofcode;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day21 implements Day {
    private static class Dice {
        int number = -1;
        int rolls = 0;

        int roll() {
            number += 1;
            rolls++;
            return number % 100 + 1;
        }
    }

    @AllArgsConstructor
    private static class GameResults {
        long wins1;
        long wins2;

        public void setBothWins(long wins1, long wins2) {
            this.wins1 = wins1;
            this.wins2 = wins2;
        }
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    public static class GameState {
        private int position1;
        private int position2;
        private int score1;
        private int score2;
    }

    private final Map<GameState, GameResults> storedResults = new HashMap<>();

    @Override
    public String part1(List<String> input) {
        GameState initialGameState = parseGameState(input);
        int p1Score = initialGameState.score1;
        int p2Score = initialGameState.score2;
        int p1Position = initialGameState.position1;
        int p2Position = initialGameState.position2;

        Dice dice = new Dice();

        while (true) {
            p1Position = (p1Position + dice.roll() + dice.roll() + dice.roll() - 1) % 10 + 1;
            p1Score += p1Position;
            if (p1Score >= 1000)
                return String.valueOf(p2Score * dice.rolls);
            p2Position = (p2Position + dice.roll() + dice.roll() + dice.roll() - 1) % 10 + 1;
            p2Score += p2Position;
            if (p2Score >= 1000)
                return String.valueOf(p1Score * dice.rolls);
        }
    }

    private GameResults countWins(GameState gameState) {
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

    @Override
    public String part2(List<String> input) {
        GameState initialGameState = parseGameState(input);
        GameResults gameResults = countWins(initialGameState);
        return String.valueOf(Math.max(gameResults.wins1, gameResults.wins2));
    }

    private GameState parseGameState(List<String> input) {
        GameState gameState = new GameState();
        input.forEach(
                line -> {
                    String[] tokens = line.replaceAll("[a-zA-Z]|\\s", "").split(":");
                    if (tokens[0].equals("1")) {
                        gameState.setPosition1(Integer.parseInt(tokens[1]));
                    } else {
                        gameState.setPosition2(Integer.parseInt(tokens[1]));
                    }
                }
        );
        return gameState;
    }
}