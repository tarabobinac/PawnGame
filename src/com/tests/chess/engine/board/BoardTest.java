package com.tests.chess.engine.board;

import com.chess.engine.board.Board;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void initialBoard() {
        final Board board = Board.createStandardBoard();
        assertEquals(board.currentPlayer().getLegalMoves().size(), 16);
        assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 16);
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
    }
}