package com.kodilla.games;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Board board;
    private final Player player1;
    private Player player2;
    private Player currentPlayer;
    private final boolean playerVsComputer;
    private final Scanner scanner;
    private final Random random;
    private int computerDifficultyLevel;

    // Domyślny konstruktor (do działania aplikacji)
    public Game() {
        this(new Board(), new Scanner(System.in), new Random());
    }

    // Konstruktor z zależnościami (do testów)
    public Game(Board board, Scanner scanner, Random random) {
        this.board = board;
        this.scanner = scanner;
        this.random = random;

        selectLanguage(scanner);

        playerVsComputer = selectGameMode(scanner);

        System.out.print(Translations.translate("Enter name for Player 1 (X):"));
        String name1 = scanner.nextLine();
        player1 = new Player(1, 'X', name1);

        if (playerVsComputer) {
            player2 = new Player(2, 'O', Translations.translate("Computer"));
            computerDifficultyLevel = selectComputerDifficulty(scanner);
        } else {
            System.out.print(Translations.translate("Enter name for Player 2 (O):"));
            String name2 = scanner.nextLine();
            player2 = new Player(2, 'O', name2);
        }
        currentPlayer = player1;
    }

    // Run method to play game
    public void play() {
        while (true) {
            int row, col;
            board.draw();
            System.out.println(Translations.translate("Current Player: ") + currentPlayer.getName()
                    + " (" + currentPlayer.getSymbol() + ")");

            if (playerVsComputer && currentPlayer == player2) {
                // If move of computer
                int[] computerMove;

                // Decision of computer which level is set
                if (computerDifficultyLevel == 1) {
                    computerMove = getComputerMove(); // Level easy
                } else if (computerDifficultyLevel == 2) {
                    computerMove = getComputerMoveMedium(); // level medium
                } else {
                    computerMove = getComputerMoveHard(); // level hard
                }
                row = computerMove[0];
                col = computerMove[1];
                System.out.println(Translations.translate("Computer chooses row") + ": " + row + ", "
                        + Translations.translate("col") + ": " + col);
            } else {
                // if move of player
                row = getInput(scanner, Translations.translate("Enter row (0-2):"));
                col = getInput(scanner, Translations.translate("Enter col (0-2):"));
            }

            // making move
            if (!board.makeMove(row, col, currentPlayer.getNumber())) {
                System.out.println(Translations.translate("Invalid move! Try again."));
                continue;
            }

            // checking the winning conditions
            if (board.checkWin(currentPlayer.getNumber())) {
                board.draw();
                System.out.println(Translations.translate("Player ") + currentPlayer.getName()
                        + " (" + currentPlayer.getSymbol() + ") " + Translations.translate("wins!"));
                break;
            }

            // checking tie
            if (board.isFull()) {
                board.draw();
                System.out.println(Translations.translate("It's a draw!"));
                break;
            }
            switchPlayer();
        }
    }

    private void selectLanguage(Scanner scanner) {
        boolean validLanguageSelected = false;
        while (!validLanguageSelected) {
            try {
                System.out.println(Translations.translate("1. English"));
                System.out.println(Translations.translate("2. Polski"));
                System.out.print(Translations.translate("Choose language/Wybierz język (1/2): "));

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
                    default -> System.out.println(Translations.translate("Invalid choice. Please choose 1 or 2."));
                }
            } catch (Exception e) {
                System.out.println(Translations.translate("Invalid input. Please enter a valid number (1 or 2)."));
                scanner.nextLine();
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
                scanner.nextLine();

                return modeChoice == 1; // true - Player vs Computer, false - Player vs Player
            } catch (Exception e) {
                System.out.println(Translations.translate("Invalid input. Please enter a valid number (1 or 2)."));
                scanner.nextLine();
            }
        }
    }

    // Obsługa wyboru poziomu trudności
    private int selectComputerDifficulty(Scanner scanner) {
        while (true) {
            try {
                System.out.println("1. " + Translations.translate("Easy"));
                System.out.println("2. " + Translations.translate("Medium"));
                System.out.println("3. " + Translations.translate("Hard"));
                System.out.print(Translations.translate("Choose difficulty level (1-3): "));

                int difficulty = scanner.nextInt();
                scanner.nextLine();

                if (difficulty < 1 || difficulty > 3) {
                    throw new IllegalArgumentException(Translations.translate("Invalid choice. Choose between 1 and 3."));
                }

                return difficulty;
            } catch (Exception e) {
                System.out.println(Translations.translate("Invalid input. Please enter a valid number (1-3)."));
                scanner.nextLine();
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
                scanner.nextLine();
            }
        }
    }

    // level easy (random move)
    public int[] getComputerMove() {
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!board.isCellAvailable(row, col));
        return new int[]{row, col};
    }

    // level medium (predicting blocks of players)
    public int[] getComputerMoveMedium() {
        try {
            // 1. checking possibility of computer win
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board.isCellAvailable(row, col)) {
                        board.markCell(row, col, currentPlayer.getNumber()); // simulation of move
                        if (board.checkWin(currentPlayer.getNumber())) {
                            board.undoMove(row, col); // undo simulation
                            return new int[]{row, col}; // make winning move
                        }
                        board.undoMove(row, col);
                    }
                }
            }

            // 2. checking possibility of blocking player
            Player opponent = (currentPlayer == player1) ? player2 : player1;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board.isCellAvailable(row, col)) {
                        board.markCell(row, col, opponent.getNumber());
                        if (board.checkWin(opponent.getNumber())) {
                            board.undoMove(row, col);
                            return new int[]{row, col}; // make blocking move
                        }
                        board.undoMove(row, col);
                    }
                }
            }

            // 3. make random move if no options
            return getComputerMove();
        } catch (Exception e) {
            System.err.println("An error occurred while calculating the computer's move: " + e.getMessage());
            return getFallbackMove(); // Bezpieczny ruch rezerwowy w razie błędu
        }
    }

    // level hard (MiniMax)
    public int[] getComputerMoveHard() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;

        try {
            // 1. checking board to find the best move
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    if (board.isCellAvailable(row, col)) {
                        board.makeMove(row, col, player2.getNumber());
                        int score = miniMax(false, 0);
                        board.undoMove(row, col);

                        if (score > bestScore) {
                            bestScore = score;
                            bestMove = new int[]{row, col};
                        }
                    }
                }
            }
            // 2. validation the best move and try to return first available move
            if (bestMove == null) {
                bestMove = getFirstAvailableMove();
            }
            // 3. checking move with correct decision
            if (bestMove == null || bestMove.length != 2) {
                throw new IllegalStateException("Unable to determine a valid move for the computer.");
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input detected during move search: " + e.getMessage());
            bestMove = getFallbackMove(); // move to fall back
        } catch (IllegalStateException e) {
            System.err.println("State error during move calculation: " + e.getMessage());
            bestMove = new int[]{0, 0}; // Zwraca domyślny ruch, aby zapobiec awarii
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
            bestMove = new int[]{0, 0}; // default move
        }

        return bestMove;
    }

    // method return reserve move
    private int[] getFallbackMove() {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.isCellAvailable(row, col)) {
                    return new int[]{row, col};
                }
            }
        }
        return new int[]{0, 0};
    }

    private int[] getFirstAvailableMove() {
        try {
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    if (board.isCellAvailable(row, col)) {
                        return new int[]{row, col}; // Zwraca pierwsze wolne pole
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid cell detected during search for available moves: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error occurred while calculating available moves: " + e.getMessage());
        }
        return null;
    }
    // alghoritm MiniMax
    private int miniMax(boolean isMaximizing, int depth) {
        if (board.checkWin(player2.getNumber())) {
            return 10 - depth; // computer wining faster.
        } else if (board.checkWin(player1.getNumber())) {
            return depth - 10; // computer avoid quick loose
        } else if (board.isFull()) {
            return 0;
        }

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.isCellAvailable(row, col)) {
                    int currentSymbol = isMaximizing ? player2.getNumber() : player1.getNumber();
                    board.makeMove(row, col, currentSymbol);
                    int score = miniMax(!isMaximizing, depth + 1);
                    board.undoMove(row, col);
                    bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
                }
            }
        }

        return bestScore;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}