package com.chess.engine.pieces;
import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import java.util.Collection;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final int position;
    protected final Color color;
    protected final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(final PieceType pieceType, final int position, final Color color) {
        this.pieceType = pieceType;
        this.position = position;
        this.color = color;
        this.isFirstMove = false;
        this.cachedHashCode = computeHashCode();
    }

    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + position;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return position == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() && color == otherPiece.getPieceColor() && isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }

    public int getPiecePosition() {
        return this.position;
    }

    public Color getPieceColor() {
        return this.color;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public enum PieceType {

        PAWN("P") {
            @Override
            public boolean isPawn() {
                return true;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isPawn() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isPawn() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isPawn() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isPawn() {
                return false;
            }
        };

        private String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public abstract boolean isPawn();
    }
}