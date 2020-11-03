package com.chess.engine.board;
import com.chess.engine.pieces.Piece;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    protected final int tileCoordinate;

    private static final Map<Integer, UnoccupiedTile> EMPTY_TILES = createAllEmptyTiles();

    private static Map<Integer, UnoccupiedTile> createAllEmptyTiles() {

        final Map<Integer, UnoccupiedTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new UnoccupiedTile(i));
        }

        return Collections.unmodifiableMap(emptyTileMap);
    }

    //do not allow new empty tiles
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES.get(tileCoordinate);
    }

    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isOccupiedTile();

    public abstract Piece getPiece();

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }

    public static final class UnoccupiedTile extends Tile {

        private UnoccupiedTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public boolean isOccupiedTile() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile {

        private final Piece tilePiece;

        private OccupiedTile(int tileCoordinate, final Piece tilePiece) {
            super(tileCoordinate);
            this.tilePiece = tilePiece;
        }

        @Override
        public String toString() {
            return getPiece().getPieceColor().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }

        @Override
        public boolean isOccupiedTile() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.tilePiece;
        }
    }
}