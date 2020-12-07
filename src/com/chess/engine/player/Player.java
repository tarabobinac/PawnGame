package com.chess.engine.player;
import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

import java.io.Serializable;
import java.util.Collection;

public abstract class Player implements Serializable {
    protected final Board board;
    protected final Collection<Move> legalMoves;

    Player(final Board board,
           final Collection<Move> legalMoves) {

        this.board = board;
        this.legalMoves = legalMoves;
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public MoveTransition makeMove(final Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionBoard = move.execute();

        return new MoveTransition(transitionBoard, MoveStatus.DONE);
    }


    public abstract Collection<Piece> getActivePieces();

    public abstract Color getColor();

    public abstract Player getOpponent();
}