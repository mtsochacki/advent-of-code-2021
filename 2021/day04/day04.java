package day04;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class day04 {
    public static ArrayList<Integer> readFirstLine(String filename) {
        Scanner sc = null;
        ArrayList<Integer> result = new ArrayList<>();

        try {
            sc = new Scanner(new File(filename));
            String firstLine = sc.nextLine();
            result = Arrays.stream(firstLine.split(","))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        } finally {
            if (sc != null)
                sc.close();
        }

        return result;
    }

    public static ArrayList<ArrayList<ArrayList<Integer>>> readBoards(String filename) {
        Scanner sc = null;
        ArrayList<ArrayList<ArrayList<Integer>>> boards = new ArrayList<>();
        ArrayList<ArrayList<Integer>> board;

        try {
            sc = new Scanner(new File(filename));
            sc.nextLine();

            while (sc.hasNext()) {
                sc.nextLine();
                board = new ArrayList<>();

                for (int i = 0; i < 5; ++i) {
                    String line = sc.nextLine();
                    board.add(
                        Arrays.stream(line.trim().split("\\s+"))
                            .mapToInt(Integer::parseInt)
                            .boxed()
                            .collect(Collectors.toCollection(ArrayList::new))
                    );
                }

                boards.add(board);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        } finally {
            if (sc != null)
                sc.close();
        }

        return boards;
    }

    public static boolean boardHasWon(ArrayList<ArrayList<Integer>> board) {
        // first check if there is horizontal bingo...
        for (int row = 0; row < 5; ++row) {
            boolean won = true;
            for (int col = 0; col < 5; ++col) {
                if (board.get(col).get(row) != 0) {
                    won = false;
                    break;
                }
            }

            if (won) {
                return true;
            }
        }

        // ...then check if there is a vertical one
        for (int col = 0; col < 5; ++col) {
            boolean won = true;
            for (int row = 0; row < 5; ++row) {
                if (board.get(col).get(row) != 0) {
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

    public static int countWin(ArrayList<ArrayList<Integer>> board, ArrayList<Integer> drawn) {
        int counter = 0;

        // simulate "drawing" items and checking for bingo
        for (int d: drawn) {
            counter++;
            for (int row = 0; row < 5; ++row) {
                for (int col = 0; col < 5; ++col) {
                    if (board.get(col).get(row) == d) {
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

    public static int part1(boolean isWinner) {
        // first element of input is int array with drawn numbers
        // all other elements of input are int arrays of boards
        ArrayList<Integer> drawn = readFirstLine("data.txt");
        ArrayList<ArrayList<ArrayList<Integer>>> boards = readBoards("data.txt");
        ArrayList<ArrayList<Integer>> winningBoard = new ArrayList<>();
        int currentCounter;
        int winnersCounter;
        // if we are looking for winning board, then
        // we assign 999999, if not then 0
        winnersCounter = isWinner ? 999999 : 0;
        // ...for all the boards in play...
        for (ArrayList<ArrayList<Integer>> board: boards) {
            // ...check when the board has won...
            currentCounter = countWin(board, drawn);
            // ...and if it won after lower number of draws than
            // currently winning board, update the winning board...
            if ((currentCounter < winnersCounter) && isWinner) {
                winningBoard = board;
                winnersCounter = currentCounter;
            }
            // ...unless of course we are looking for loosing board
            // then it's better to update it in different situations
            else if ((currentCounter > winnersCounter) && !isWinner) {
                winningBoard = board;
                winnersCounter = currentCounter;
            }
        }
        // calculate final score
        int score = 0;
        for (int row = 0; row < 5; ++row) {
            for (int col = 0; col < 5; ++col) {
                score += winningBoard.get(col).get(row);
            }
        }
        return score * drawn.get(winnersCounter - 1);
    }

    public static void main(String[] args) {
        System.out.println("Winning board final score is " + part1(true));
        System.out.println("Losing board final score is " + part1(false));
    }
}
