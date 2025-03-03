
package com.kodilla.testing.games;
/*//mocks questions. how to make choose with mocks?
import com.kodilla.games.Board;
import com.kodilla.games.Game;
import com.kodilla.games.Translations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.util.Random;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
*/

public class GameTest {
}
/*
    private Board boardMock;
    private Scanner scannerMock;
    private Random randomMock;
    private Game game;

    @BeforeEach
    void setUp() {
        boardMock = mock(Board.class);
        scannerMock = mock(Scanner.class);
        randomMock = mock(Random.class);
        Translations.setLanguage("EN");
        mockStatic(Translations.class);
        when(Translations.translate(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
        game = new Game(boardMock, scannerMock, randomMock);
    }

    @Test
    void shouldInitializeGameCorrectly() {
        try (MockedStatic<Translations> translationsMockedStatic = mockStatic(Translations.class)) {
            when(scannerMock.nextInt()).thenReturn(1, 2);
            when(scannerMock.nextLine()).thenReturn("Player1", "Player2");
            translationsMockedStatic.when(() -> Translations.translate(anyString()))
                    .thenAnswer(invocation -> invocation.getArgument(0));
            game = new Game(boardMock, scannerMock, randomMock);
            assertNotNull(game);

            translationsMockedStatic.verify(() -> Translations.setLanguage("EN"), times(1)); // Sprawdzenie ustawienia EN
            translationsMockedStatic.verify(() -> Translations.translate(anyString()), atLeastOnce()); // Weryfikacja tłumaczenia

            verify(scannerMock, times(2)).nextInt(); // 1: wybór języka, 2: wybór trybu gry
            verify(scannerMock, times(2)).nextLine(); // Oczekujemy na nazwę graczy
        }
    }


    @Test
    void shouldEndGameWhenWinConditionIsMet() {
        when(scannerMock.nextInt()).thenReturn(0, 0); // Ruch gracza 1
        when(boardMock.makeMove(0, 0, 1)).thenReturn(true); // Ruch poprawny
        when(boardMock.checkWin(1)).thenReturn(true); // Gracz 1 wygrywa
        when(boardMock.isFull()).thenReturn(false);
        game.play();
        verify(boardMock, times(1)).checkWin(1); // Warunek wygranej sprawdzany raz
        verify(boardMock, times(1)).draw();
    }

    @Test
    void shouldEndGameAsDrawWhenBoardIsFull() {
        when(scannerMock.nextInt()).thenReturn(0, 0); // Ruch
        when(boardMock.makeMove(0, 0, 1)).thenReturn(true); // Ruch poprawny
        when(boardMock.isFull()).thenReturn(true); // Plansza pełna
        game.play();
        verify(boardMock, times(1)).isFull();
        verify(boardMock, times(1)).draw();
    }
}
 */