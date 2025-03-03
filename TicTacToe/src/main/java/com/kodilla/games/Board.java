package com.kodilla.games;

public class Board {
    private final int[][] board;

    public Board() {
        board = new int[3][3]; // Init
    }

    public boolean makeMove(int row, int col, int player) {
        if (board[row][col] == 0) {
            board[row][col] = player;
            return true;
        }
        return false;
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkWin(int player) {
        // checking rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }
        // checking cols
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        // checking diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    public void draw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char symbol = mapIntToChar(board[i][j]);
                System.out.print(" " + symbol + " ");
                if (j < 2) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("---|---|---");
            }
        }
    }

    public boolean isCellAvailable(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw new IllegalArgumentException("Invalid Input: (0-2)");
        }
        return board[row][col] == 0;
    }
    public int getSize() {
        return board.length;
    }

    public void undoMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw new IllegalArgumentException("Invalid input. Row and column must be between 0 and 2.");
        }
        board[row][col] = 0;
    }

    private char mapIntToChar(int value) {
        return switch (value) {
            case 1 -> 'X';
            case 2 -> 'O';
            default -> ' ';
        };
    }

    public boolean markCell(int row, int col, int player) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw new IllegalArgumentException("Invalid input. Row and column must be between 0 and 2.");
        }
        if (board[row][col] != 0) {
            return false;
        }
        board[row][col] = player;
        return true;
    }
}