package com.kodilla.games;

public class Translations {

    private static String currentLanguage = "EN";

    public static void setLanguage(String language) {
        currentLanguage = language;
    }

    public static String translate(String message) {
        return switch (currentLanguage) {
            case "PL" -> switch (message) {
                case "Enter name for Player 1 (X):" -> "Podaj imię dla Gracza 1 (X):";
                case "Enter name for Player 2 (O):" -> "Podaj imię dla Gracza 2 (O):";
                case "Current Player: " -> "Aktualny gracz: ";
                case "Enter row (0-2):" -> "Podaj wiersz (0-2):";
                case "Enter col (0-2):" -> "Podaj kolumnę (0-2):";
                case "Invalid move! Try again." -> "Nieprawidłowy ruch! Spróbuj ponownie.";
                case "Player " -> "Gracz ";
                case "wins!" -> "wygrywa!";
                case "It's a draw!" -> "Remis!";
                case "Input must be between 0 and 2." -> "Wartość musi mieścić się w zakresie od 0 do 2.";
                case "Invalid input. Please, try type again." -> "Nieprawidłowe dane. Proszę, spróbuj ponownie.";
                case "1. English" -> "1. English";
                case "2. Polski" -> "2. Polski";
                case "Choose language (1/2): " -> "Wybierz język (1/2): ";
                case "Invalid input. Please enter a valid number (1 or 2)." -> "Nieprawidłowe dane. Podaj poprawną liczbę (1 lub 2).";
                case "Invalid choice. Please choose 1 or 2." -> "Nieprawidłowy wybór. Wybierz 1 lub 2.";
                default -> message;
            };
            default -> switch (message) {
                case "Enter name for Player 1 (X):" -> "Enter name for Player 1 (X):";
                case "Enter name for Player 2 (O):" -> "Enter name for Player 2 (O):";
                case "Current Player: " -> "Current Player: ";
                case "Enter row (0-2):" -> "Enter row (0-2):";
                case "Enter col (0-2):" -> "Enter col (0-2):";
                case "Invalid move! Try again." -> "Invalid move! Try again.";
                case "Player " -> "Player ";
                case "wins!" -> "wins!";
                case "It's a draw!" -> "It's a draw!";
                case "Input must be between 0 and 2." -> "Input must be between 0 and 2.";
                case "Invalid input. Please, try type again." -> "Invalid input. Please, try type again.";
                case "1. English" -> "1. English";
                case "2. Polski" -> "2. Polish";
                case "Choose language (1/2): " -> "Choose language (1/2): ";
                case "Invalid input. Please enter a valid number (1 or 2)." -> "Invalid input. Please enter a valid number (1 or 2).";
                case "Invalid choice. Please choose 1 or 2." -> "Invalid choice. Please choose 1 or 2.";
                default -> message;
            };
        };
    }
}