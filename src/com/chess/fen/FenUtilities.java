package com.chess.fen;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.*;

public class FenUtilities {

    private FenUtilities() {
        throw new RuntimeException("Not instantiable");
    }

    public static Board createGameFromFEN(final String fenString) {
        return parseFEN(fenString);
    }

    public static String createFENFromGame(final Board board, String currentPlayer) {
        return calculateBoardText(board) + " " +
                currentPlayer + " - " +
                calculateEnPassantSquare(board) + " " +
                "0 1";
    }

    private static Board parseFEN(final String fenString) {
        final String[] fenPartitions = fenString.trim().split(" ");
        final Board.Builder builder = new Board.Builder();
        final String gameConfiguration = fenPartitions[0];
        final char[] boardTiles = gameConfiguration.replaceAll("/", "")
                .replaceAll("8", "--------")
                .replaceAll("7", "-------")
                .replaceAll("6", "------")
                .replaceAll("5", "-----")
                .replaceAll("4", "----")
                .replaceAll("3", "---")
                .replaceAll("2", "--")
                .replaceAll("1", "-")
                .toCharArray();
        int i = 0;
        while (i < boardTiles.length) {
            switch (boardTiles[i]) {
                case 'r':
                    builder.setPiece(new Rook(i, Color.BLACK));
                    i++;
                    break;
                case 'n':
                    builder.setPiece(new Knight(i, Color.BLACK));
                    i++;
                    break;
                case 'b':
                    builder.setPiece(new Bishop(i, Color.BLACK));
                    i++;
                    break;
                case 'q':
                    builder.setPiece(new Queen(i, Color.BLACK));
                    i++;
                    break;
                case 'p':
                    builder.setPiece(new Pawn(i, Color.BLACK));
                    i++;
                    break;
                case 'R':
                    builder.setPiece(new Rook(i, Color.WHITE));
                    i++;
                    break;
                case 'N':
                    builder.setPiece(new Knight(i, Color.WHITE));
                    i++;
                    break;
                case 'B':
                    builder.setPiece(new Bishop(i, Color.WHITE));
                    i++;
                    break;
                case 'Q':
                    builder.setPiece(new Queen(i, Color.WHITE));
                    i++;
                    break;
                case 'P':
                    builder.setPiece(new Pawn(i, Color.WHITE));
                    i++;
                    break;
                case '-':
                    i++;
                    break;
                default:
                    throw new RuntimeException("Invalid FEN String " + gameConfiguration);
            }
        }

        if (!fenPartitions[3].equals("-")) {
            if (fenPartitions[3].contains("5")) {
                builder.setEnPassantPawn(new Pawn(BoardUtils.getCoordinateAtPosition(fenPartitions[3]), Color.BLACK, false));
            } else if (fenPartitions[3].contains("4")) {
                builder.setEnPassantPawn(new Pawn(BoardUtils.getCoordinateAtPosition(fenPartitions[3]), Color.WHITE, false));
            }
        }

        if (fenPartitions[1].equals("w")) {
            builder.setMoveMaker(Color.WHITE);
        } else if (fenPartitions[1].equals("b")) {
            builder.setMoveMaker(Color.BLACK);
        }
        return builder.build();
    }

    private static String calculateBoardText(final Board board) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = board.getTile(i).getPiece() == null ? "-" :
                    board.getTile(i).getPiece().getPieceColor().isWhite() ? board.getTile(i).getPiece().toString() :
                            board.getTile(i).getPiece().toString().toLowerCase();
            builder.append(tileText);
        }
        builder.insert(8, "/");
        builder.insert(17, "/");
        builder.insert(26, "/");
        builder.insert(35, "/");
        builder.insert(44, "/");
        builder.insert(53, "/");
        builder.insert(62, "/");
        return builder.toString()
                .replaceAll("--------", "8")
                .replaceAll("-------", "7")
                .replaceAll("------", "6")
                .replaceAll("-----", "5")
                .replaceAll("----", "4")
                .replaceAll("---", "3")
                .replaceAll("--", "2")
                .replaceAll("-", "1");
    }

    private static String calculateEnPassantSquare(final Board board) {
        final Pawn enPassantPawn = board.getEnPassantPawn();
        if(enPassantPawn != null) {
            return BoardUtils.getPositionAtCoordinate(enPassantPawn.getPiecePosition() +
                    (8) * enPassantPawn.getPieceColor().getOppositeDirection());
        }
        return "-";
    }
}
