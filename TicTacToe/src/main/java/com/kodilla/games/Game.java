package com.kodilla.games;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Board board;
    private final Player player1;
    private Player player2;
    private Player currentPlayer;
    private final boolean playerVsComputer;
    private final Scanner scanner; // Pole Scanner
    private final Random random; // Pole Random

    // Domyślny konstruktor (do działania aplikacji)
    public Game() {
        this(new Board(), new Scanner(System.in), new Random());
    }

    // Konstruktor z zależnościami (do testów)
    public Game(Board board, Scanner scanner, Random random) {
        this.board = board;
        this.scanner = scanner;
        this.random = random;

        // Wywołanie obsługi języka
        selectLanguage(scanner);

        // Wybór trybu gry
        playerVsComputer = selectGameMode(scanner);

        // Inicjalizacja graczy
        System.out.print(Translations.translate("Enter name for Player 1 (X):"));
        String name1 = scanner.nextLine();
        player1 = new Player(1, 'X', name1);

        if (playerVsComputer) {
            player2 = new Player(2, 'O', Translations.translate("Computer"));
        } else {
            System.out.print(Translations.translate("Enter name for Player 2 (O):"));
            String name2 = scanner.nextLine();
            player2 = new Player(2, 'O', name2);
        }

        currentPlayer = player1; // Pierwszy gracz rozpoczyna grę
    }

    // Metoda główna gry
    public void play() {
        while (true) {
            board.draw();

            // Wyświetl informacje o bieżącym graczu
            System.out.println(Translations.translate("Current Player: ") + currentPlayer.getName()
                    + " (" + currentPlayer.getSymbol() + ")");

            int row, col;

            if (playerVsComputer && currentPlayer == player2) {
                // Jeśli to ruch komputera
                int[] computerMove = getComputerMove();
                row = computerMove[0];
                col = computerMove[1];
                System.out.println(Translations.translate("Computer chooses row") + ": " + row + ", "
                        + Translations.translate("col") + ": " + col);
            } else {
                // Jeśli to ruch gracza
                row = getInput(scanner, Translations.translate("Enter row (0-2):"));
                col = getInput(scanner, Translations.translate("Enter col (0-2):"));
            }

            // Wykonywanie ruchu
            if (!board.makeMove(row, col, currentPlayer.getNumber())) {
                System.out.println(Translations.translate("Invalid move! Try again."));
                continue;
            }

            // Sprawdzanie warunków wygranej
            if (board.checkWin(currentPlayer.getNumber())) {
                board.draw();
                System.out.println(Translations.translate("Player ") + currentPlayer.getName()
                        + " (" + currentPlayer.getSymbol() + ") " + Translations.translate("wins!"));
                break;
            }

            // Sprawdzanie remisu
            if (board.isFull()) {
                board.draw();
                System.out.println(Translations.translate("It's a draw!"));
                break;
            }

            // Zmiana gracza
            switchPlayer();
        }
    }

    // Obsługa języka
    private void selectLanguage(Scanner scanner) {
        boolean validLanguageSelected = false;
        while (!validLanguageSelected) {
            try {
                System.out.println(Translations.translate("1. English"));
                System.out.println(Translations.translate("2. Polski"));
                System.out.print(Translations.translate("Choose language/Wybierz język (1/2): "));

                int languageChoice = scanner.nextInt();
                scanner.nextLine(); // Czyszczenie bufora wejścia

                switch (languageChoice) {
                    case 1 -> {
                        Translations.setLanguage("EN");
                        validLanguageSelected = true;
                    }
                    case 2 -> {
                        Translations.setLanguage("PL");
                        validLanguageSelected = true;
                    }
                    default -> System.out.println(Translations.translate("Invalid choice. Please choose 1 or 2."));
                }
            } catch (Exception e) {
                System.out.println(Translations.translate("Invalid input. Please enter a valid number (1 or 2)."));
                scanner.nextLine(); // Czyszczenie błędnego wejścia
            }
        }
    }

    private boolean selectGameMode(Scanner scanner) {
        while (true) {
            try {
                System.out.println("1. " + Translations.translate("Player vs Computer"));
                System.out.println("2. " + Translations.translate("Player vs Player"));
                System.out.print(Translations.translate("Choose mode (1/2): "));

                int modeChoice = scanner.nextInt();
                scanner.nextLine(); // Czyszczenie bufora

                return modeChoice == 1; // true - Player vs Computer, false - Player vs Player
            } catch (Exception e) {
                System.out.println(Translations.translate("Invalid input. Please enter a valid number (1 or 2)."));
                scanner.nextLine(); // Czyszczenie błędów
            }
        }
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
                scanner.nextLine(); // Czyszczenie błędnego wejścia
            }
        }
    }

    public int[] getComputerMove() {
        int row, col;

        if (board.isFull()) {
            throw new IllegalStateException("Brak dostępnych ruchów. Plansza jest pełna.");
        }

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