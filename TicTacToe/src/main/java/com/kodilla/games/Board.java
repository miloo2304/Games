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
                    return false; // If empty zones
                }
            }
        }
        return true; // No empty zones
    }

    public boolean checkWin(int player) {
        // Checking rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }
        // Checking cols
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        // Checking diagonals
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
                    System.out.print("|"); // Vertical separator
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("---|---|---"); // Horizontal separator
            }
        }
    }

    public boolean isCellAvailable(int row, int col) {
        // Sprawdzenie, czy podane współrzędne są w dopuszczalnym zakresie
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw new IllegalArgumentException("Invalid Input: (0-2)");
        }

        // Jeśli wartość wynosi 0, komórka jest dostępna
        return board[row][col] == 0;
    }


    private char mapIntToChar(int value) {
        return switch (value) {
            case 1 -> 'X';
            case 2 -> 'O';
            default -> ' ';
        };
    }
}