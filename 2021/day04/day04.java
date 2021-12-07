import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class day04 {

    // this class exists purely so that countWin
    // method can return "multiple" variables
    public static class Winner {
        int counter;
        Boolean[] boardState = new Boolean[25];
    };

    public static ArrayList<int[]> readInput(String filename) {
        Scanner sc = null;
        ArrayList<int[]> gameBoards = new ArrayList<int[]>();
        int drawnNumbers[] = new int[100];
        int currentBoard[] = new int[25];

        try {
            sc = new Scanner(new File(filename)).useDelimiter(",|\\n");

            // scan first line of input aka "randomly drawn numbers"
            for (int i = 0; i < drawnNumbers.length; i++) {
                drawnNumbers[i] = sc.nextInt();
            }
            gameBoards.add(drawnNumbers);
            // reset delimiter since commas are no longer used
            sc.reset();
            // scan the rest of the input aka "all game boards"
            while (sc.hasNext()) {
                for (int j = 0; j < 25; j++) {
                    currentBoard[j] = sc.nextInt();
                }
                gameBoards.add(currentBoard.clone());
            }
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        } finally {
            sc.close();
        }
        return gameBoards;
    }

    public static boolean boardHasWon(Boolean[] boardState) {
        // first check if there is horizontal bingo...
        for (int j = 0; j < 21; j += 5) {
            if ((boardState[j] == true) && (boardState[j + 1] == true) && (boardState[j + 2] == true)
                    && (boardState[j + 3] == true)
                    && (boardState[j + 4] == true))
                return true;
        }
        // ...then check if there is a vertical one
        for (int i = 0; i < 5; i++) {
            if ((boardState[i] == true) && (boardState[i + 5] == true) && (boardState[i + 10] == true)
                    && (boardState[i + 15] == true) && (boardState[i + 20] == true))
                return true;
        }
        return false;
    }

    public static Winner countWin(int[] board, int[] drawn) {
        int counter = 0;
        boolean flag = false; // used to break out of double nested loop
        Winner x = new Winner();
        Boolean[] boardState = new Boolean[25];
        Arrays.fill(boardState, Boolean.FALSE);

        // simulate "drawing" items and checking for bingo
        for (int i = 0; i < drawn.length && !flag; i++) {
            counter++;
            for (int j = 0; j < board.length; j++) {
                if (board[j] == drawn[i])
                    boardState[j] = true;
                if (boardHasWon(boardState)) {
                    flag = true;
                    break;
                }
            }
        }
        // update the winner
        x.boardState = boardState;
        x.counter = counter;
        return x;
    }

    public static int part1(boolean isWinner) {
        // first element of input is int array with drawn numbers
        // all other elements of input are int arrays of boards
        ArrayList<int[]> input = readInput("data.txt");
        int winningBoard[] = new int[25];
        Boolean[] winningBoardState = new Boolean[25];
        int currentCounter = 0;
        int winnersCounter;
        // if we are looking for winning board, then
        // we assign 999999, if not then 0
        winnersCounter = isWinner ? 999999 : 0;
        int score = 0;
        // ...for all the boards in play...
        for (int i = 1; i < input.size(); i++) {
            // ...check when the board has won...
            currentCounter = countWin(input.get(i), input.get(0)).counter;
            // ...and if it won after lower number of draws than
            // currently winning board, update the winning board...
            if ((currentCounter < winnersCounter) && isWinner) {
                winningBoard = input.get(i);
                winningBoardState = countWin(input.get(i), input.get(0)).boardState;
                winnersCounter = currentCounter;
            }
            // ...unless of course we are looking for loosing board
            // then it's better to update it in different situations
            else if ((currentCounter > winnersCounter) && !isWinner) {
                winningBoard = input.get(i);
                winningBoardState = countWin(input.get(i), input.get(0)).boardState;
                winnersCounter = currentCounter;
            }
        }
        // calculate final score
        for (int i = 0; i < winningBoardState.length; i++) {
            if (!winningBoardState[i])
                score += winningBoard[i];
        }
        return score * input.get(0)[winnersCounter - 1];
    }

    public static void main(String[] args) {
        System.out.println("Winning board final score is " + part1(true));
        System.out.println("Loosing board final score is " + part1(false));
    }
}
