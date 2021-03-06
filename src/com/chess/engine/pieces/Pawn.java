package com.chess.engine.pieces;
import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.PawnMove;
import com.chess.engine.board.Move.PawnJump;
import com.chess.engine.board.Move.PawnAttackMove;
import com.chess.engine.board.Move.PawnEnPassantAttackMove;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {7, 8, 9, 16};

    public Pawn(final int position, final Color color) {
        super(PieceType.PAWN, position, color, true);
    }

    public Pawn(final int position, final Color color, final boolean isFirstMove) {
        super(PieceType.PAWN, position, color, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinate = this.position + (this.color.getDirection() * currentCandidateOffset);
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isOccupiedTile()) {
                legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.SEVENTH_RANK[this.position] && this.getPieceColor().isBlack()) ||
                    (BoardUtils.SECOND_RANK[this.position] && this.getPieceColor().isWhite()))) {
                final int behindCandidateDestinationCoordinate = this.position + (this.color.getDirection() * 8);
                if (!board.getTile(behindCandidateDestinationCoordinate).isOccupiedTile() &&
                        !board.getTile(candidateDestinationCoordinate).isOccupiedTile()) {
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.position] && this.color.isWhite() ||
                            (BoardUtils.FIRST_COLUMN[this.position] && this.color.isBlack())))) {
                if (board.getTile(candidateDestinationCoordinate).isOccupiedTile()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.color != pieceOnCandidate.getPieceColor()) {
                        legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                } else if (board.getEnPassantPawn() != null) {
                    if (board.getEnPassantPawn().getPiecePosition() == (this.position + (this.color.getOppositeDirection()))) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if (this.color != pieceOnCandidate.getPieceColor()) {
                            legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
            } else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.position] && this.color.isWhite() ||
                            (BoardUtils.EIGHTH_COLUMN[this.position] && this.color.isBlack())))) {
                if (board.getTile(candidateDestinationCoordinate).isOccupiedTile()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.color != pieceOnCandidate.getPieceColor()) {
                        legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                } else if (board.getEnPassantPawn() != null) {
                    if (board.getEnPassantPawn().getPiecePosition() == (this.position - (this.color.getOppositeDirection()))) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if (this.color != pieceOnCandidate.getPieceColor()) {
                            legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor());
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}