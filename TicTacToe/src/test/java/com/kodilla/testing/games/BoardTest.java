package com.kodilla.testing.games;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.TestInfo;
import com.kodilla.games.Board;


public class BoardTest {

    private boolean testPassed;

    @BeforeEach
    void beforeEachTest(TestInfo testInfo) {
        testPassed = true;
        System.out.println("Starting test: " + testInfo.getDisplayName());
    }

    @AfterEach
    void afterEachTest(TestInfo testInfo) {
        if (testPassed) {
            System.out.println("Test passed: " + testInfo.getDisplayName()+ "\n");
        } else {
            System.out.println("Test failed: " + testInfo.getDisplayName() + "\n");
        }
    }



    @Test
    void testMakeMove_validMove() {
        Board board = new Board();
        boolean moveMade = board.makeMove(1, 1, 1);
        assertTrue(moveMade);
        assertFalse(board.isCellAvailable(1, 1));
    }

    @Test
    void testMakeMove_invalidMove() {
        Board board = new Board();
        board.makeMove(1, 1, 1);
        boolean moveMade = board.makeMove(1, 1, 2);
        assertFalse(moveMade);
    }

    @Test
    void testIsFull_emptyBoard() {
        Board board = new Board();
        boolean full = board.isFull();
        assertFalse(full);
    }

    @Test
    void testIsFull_fullBoard() {
        Board board = new Board();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.makeMove(i, j, 1);
            }
        }
        boolean full = board.isFull();
        assertTrue(full);
    }

    @Test
    void testCheckWin_horizontalWin() {
        Board board = new Board();
        board.makeMove(0, 0, 1);
        board.makeMove(0, 1, 1);
        board.makeMove(0, 2, 1);
        boolean win = board.checkWin(1);
        assertTrue(win);
    }

    @Test
    void testCheckWin_verticalWin() {
        Board board = new Board();
        board.makeMove(0, 0, 2);
        board.makeMove(1, 0, 2);
        board.makeMove(2, 0, 2);

        boolean win = board.checkWin(2);
        assertTrue(win);
    }

    @Test
    void testCheckWin_diagonalWin() {
        Board board = new Board();
        board.makeMove(0, 0, 1);
        board.makeMove(1, 1, 1);
        board.makeMove(2, 2, 1);
        boolean win = board.checkWin(1);
        assertTrue(win);
    }

    @Test
    void testCheckWin_noWin() {
        Board board = new Board();
        board.makeMove(0, 0, 1);
        board.makeMove(0, 1, 2);
        board.makeMove(0, 2, 1);
        boolean win = board.checkWin(1);
        assertFalse(win);
    }

    @Test
    void testIsCellAvailable_cellIsAvailable() {
        Board board = new Board();
        boolean available = board.isCellAvailable(0, 0);
        assertTrue(available);
    }

    @Test
    void testIsCellAvailable_cellUnavailable() {
        Board board = new Board();
        board.makeMove(0, 0, 1);
        boolean available = board.isCellAvailable(0, 0);
        assertFalse(available);
    }

    @Test
    void testIsCellAvailable_invalidInput() {
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> board.isCellAvailable(3, 3));
    }
}