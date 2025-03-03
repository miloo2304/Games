package com.kodilla.games;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Board board;
    private final Player player1;
    private Player player2;
    private Player currentPlayer;
    private final boolean playerVsComputer;

    public Game() {
        Scanner scanner = new Scanner(System.in);

        // Wybierz język gry
        selectLanguage(scanner);

        // Wyświetl główne menu i wybierz tryb gry
        playerVsComputer = selectGameMode(scanner);

        board = new Board();

        // Pobierz nazwę dla gracza 1
        System.out.print(Translations.translate("Enter name for Player 1 (X):"));
        String name1 = scanner.nextLine();
        player1 = new Player(1, 'X', name1);

        // Jeśli tryb to Player vs Computer, ustaw komputer jako gracza 2
        if (playerVsComputer) {
            player2 = new Player(2, 'O', Translations.translate("Computer"));
        } else {
            // Jeśli tryb to Player vs Player, pobierz nazwę dla gracza 2
            System.out.print(Translations.translate("Enter name for Player 2 (O):"));
            String name2 = scanner.nextLine();
            player2 = new Player(2, 'O', name2);
        }

        currentPlayer = player1; // Gracz 1 zaczyna
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            board.draw();

            // Wyświetl informacje o bieżącym graczu
            System.out.println(Translations.translate("Current Player: ") + currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ")");

            int row, col;

            if (playerVsComputer && currentPlayer == player2) {
                // Jeśli gra przeciwko komputerowi, generuj ruch komputera
                int[] computerMove = getComputerMove();
                row = computerMove[0];
                col = computerMove[1];
                System.out.println(Translations.translate("Computer chooses row") + ": " + row + ", " + Translations.translate("col") + ": " + col);
            } else {
                // W przeciwnym razie, zapytaj gracza o ruch
                row = getInput(scanner, Translations.translate("Enter row (0-2):"));
                col = getInput(scanner, Translations.translate("Enter col (0-2):"));
            }

            // Wykonanie ruchu
            if (!board.makeMove(row, col, currentPlayer.getNumber())) {
                System.out.println(Translations.translate("Invalid move! Try again."));
                continue;
            }

            // Sprawdzenie warunków zakończenia
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

            // Zmiana tury
            switchPlayer();
        }

        scanner.close();
    }

    private void selectLanguage(Scanner scanner) {
        boolean validLanguageSelected = false;
        while (!validLanguageSelected) {
            try {
                System.out.println(Translations.translate("1. English"));
                System.out.println(Translations.translate("2. Polski"));
                System.out.print(Translations.translate("Choose language/Wybierz język (1/2): "));

                int languageChoice = scanner.nextInt();
                scanner.nextLine(); // Oczyszczenie bufora wejścia

                switch (languageChoice) {
                    case 1 -> {
                        Translations.setLanguage("EN"); // Poprawna wielkość liter
                        validLanguageSelected = true;
                    }
                    case 2 -> {
                        Translations.setLanguage("PL"); // Poprawna wielkość liter
                        validLanguageSelected = true;
                    }
                    default -> System.out.println(Translations.translate("Invalid choice. Please choose 1 or 2."));
                }
            } catch (Exception e) {
                System.out.println(Translations.translate("Invalid input. Please enter a valid number (1 or 2)."));
                scanner.nextLine(); // Wyczyść błędne wejście
            }
        }
    }


    private boolean selectGameMode(Scanner scanner) {
        boolean validModeSelected = false;
        while (!validModeSelected) {
            try {
                System.out.println("1. " + Translations.translate("Player vs Computer"));
                System.out.println("2. " + Translations.translate("Player vs Player"));
                System.out.print(Translations.translate("Choose mode (1/2): "));

                int modeChoice = scanner.nextInt();
                scanner.nextLine();

                switch (modeChoice) {
                    case 1 -> {
                        return true;
                    }
                    case 2 -> {
                        return false;
                    }
                    default -> System.out.println(Translations.translate("Invalid choice. Please choose 1 or 2."));
                }
            } catch (Exception e) {
                System.out.println(Translations.translate("Invalid input. Please enter a valid number (1 or 2)."));
                scanner.nextLine();
            }
        }
        return false; // Domyślnie Player vs Player
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

    private int[] getComputerMove() {
        Random random = new Random();
        int row, col;

        // Sprawdź, czy plansza jest pełna
        if (board.isFull()) {
            throw new IllegalStateException("Brak dostępnych ruchów. Plansza jest pełna.");
        }

        // Znajdź dostępne pole
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!board.isCellAvailable(row, col));

        return new int[]{row, col};
    }


    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}