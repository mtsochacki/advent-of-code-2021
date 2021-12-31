import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class day04 {
    public static ArrayList<Integer> readDrawnNumbers(String filename) {
        Scanner sc;
        ArrayList<Integer> drawnNumbers = new ArrayList<>();
        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter(",|\n");
            while (sc.hasNextInt()) {
                drawnNumbers.add(sc.nextInt());
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong drawing numbers" + e);
        }
        return drawnNumbers;
    }

    public static ArrayList<ArrayList<ArrayList<Integer>>> readBoards(String filename) {
        Scanner sc;
        ArrayList<ArrayList<ArrayList<Integer>>> boards = new ArrayList<>();
        ArrayList<ArrayList<Integer>> board;
        try {
            sc = new Scanner(new File(filename));
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
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        }
        return boards;
    }

    public static boolean boardHasWon(ArrayList<ArrayList<Integer>> board) {
        for (ArrayList<Integer> row : board) {
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
            for (ArrayList<Integer> row : board) {
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

    public static int countWin(ArrayList<ArrayList<Integer>> board, ArrayList<Integer> drawn) {
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

    public static int calculateBoard(boolean isWinner) {
        ArrayList<Integer> drawn = readDrawnNumbers("data.txt");
        ArrayList<ArrayList<ArrayList<Integer>>> boards = readBoards("data.txt");
        ArrayList<ArrayList<Integer>> winningBoard = new ArrayList<>();
        int currentCounter;
        int winnersCounter;
        winnersCounter = isWinner ? 999999 : 0;
        for (ArrayList<ArrayList<Integer>> board : boards) {
            currentCounter = countWin(board, drawn);
            if ((currentCounter < winnersCounter) && isWinner) {
                winningBoard = board;
                winnersCounter = currentCounter;
            } else if ((currentCounter > winnersCounter) && !isWinner) {
                winningBoard = board;
                winnersCounter = currentCounter;
            }
        }
        int score = 0;
        for (ArrayList<Integer> row : winningBoard) {
            for (int i = 0; i < 5; i++) {
                score += row.get(i);
            }
        }
        return score * drawn.get(winnersCounter - 1);
    }

    public static void main(String[] args) {
        System.out.println("Winning board final score is " + calculateBoard(true));
        System.out.println("Loosing board final score is " + calculateBoard(false));
    }
}
