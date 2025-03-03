package com.kodilla.games;

import java.util.Scanner;

public class Game {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;

    public Game() {
        Scanner scanner = new Scanner(System.in);
        boolean validLanguageSelected = false;
        while (!validLanguageSelected) {
            try {
                System.out.println("1. English");
                System.out.println("2. Polski");
                System.out.print("Choose language (1/2): ");

                int languageChoice = scanner.nextInt();
                scanner.nextLine();

                switch (languageChoice) {
                    case 1 -> {
                        Translations.setLanguage("EN");
                        validLanguageSelected = true;
                    }
                    case 2 -> {
                        Translations.setLanguage("PL");
                        validLanguageSelected = true;
                    }
                    default -> System.out.println("Invalid choice. Please choose 1 or 2."); // Nieprawidłowy zakres
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number (1 or 2).");
                scanner.nextLine();
            }
        }

        board = new Board();

        System.out.print(Translations.translate("Enter name for Player 1 (X):"));
        String name1 = scanner.nextLine();
        player1 = new Player(1, 'X', name1);

        System.out.print(Translations.translate("Enter name for Player 2 (O):"));
        String name2 = scanner.nextLine();
        player2 = new Player(2, 'O', name2);

        currentPlayer = player1; // Gracz 1 zaczyna
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            board.draw();
            System.out.println(Translations.translate("Current Player: ") + currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ")");

            int row = getInput(scanner, Translations.translate("Enter row (0-2):"));
            int col = getInput(scanner, Translations.translate("Enter col (0-2):"));

            // Making move
            if (!board.makeMove(row, col, currentPlayer.getNumber())) {
                System.out.println(Translations.translate("Invalid move! Try again."));
                continue;
            }

            // Checking after move
            if (board.checkWin(currentPlayer.getNumber())) {
                board.draw();
                System.out.println(Translations.translate("Player ") + currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ") " + Translations.translate("wins!"));
                break;
            }

            if (board.isFull()) {
                board.draw();
                System.out.println(Translations.translate("It's a draw!"));
                break;
            }

            // Change player
            switchPlayer();
        }
        scanner.close();
    }

    private int getInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();


                if (value < 0 || value > 2) {
                    throw new IllegalArgumentException(Translations.translate("Input must be between 0 and 2."));
                }
                return value;
            } catch (Exception e) {
                System.out.println(Translations.translate("Invalid input. Please, try type again."));
                scanner.nextLine();
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}