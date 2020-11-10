package com.chess.engine.player;
import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import java.util.Collection;

public abstract class Player {
    protected final Board board;
    protected final Collection<Move> legalMoves;

    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves) {

        this.board = board;
        this.legalMoves = legalMoves;
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    //video 19 explains check if i end up needing it

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() { //not necessary
        return false;
    }

    public boolean isInCheckMate() { //not necessary
        return false;
    }

    public boolean isInStaleMate() { //not necessary
        return false;
    }

    public boolean isCastled() { //not necessary
        return false;
    }

    public MoveTransition makeMove(final Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionBoard = move.execute();

        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }


    public abstract Collection<Piece> getActivePieces();

    public abstract Color getColor();

    public abstract Player getOpponent();
}