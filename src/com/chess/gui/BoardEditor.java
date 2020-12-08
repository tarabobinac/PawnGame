package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.chess.engine.board.Tile;
import com.chess.engine.pieces.*;
import com.chess.fen.FenUtilities;
import com.chess.engine.board.Board.Builder;

import static com.chess.engine.Color.BLACK;
import static com.chess.engine.Color.WHITE;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class BoardEditor {

    private final JFrame gameFrame;
    private final FenRetriever fenRetrieverPanel;
    private final PiecePanel piecePanel;
    private Board chessBoard;
    private Tile sourceTile;
    private boolean blackPawnSelected;
    private boolean whitePawnSelected;
    private boolean blackRookSelected;
    private boolean whiteRookSelected;
    private boolean blackBishopSelected;
    private boolean whiteBishopSelected;
    private boolean blackKnightSelected;
    private boolean whiteKnightSelected;
    private boolean blackQueenSelected;
    private boolean whiteQueenSelected;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(900, 1300);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(800, 700);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(20, 20);
    private final static String defaultPieceImagesPath = "/pieces/";
    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#80C98E");
    private final Font font;

    public BoardEditor() {

        this.font = new Font("sans-serif", Font.PLAIN, 40);

        this.gameFrame = new JFrame("Board Editor");
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        BoardPanel boardPanel = new BoardPanel();
        this.piecePanel = new PiecePanel();
        this.fenRetrieverPanel = new FenRetriever();
        this.chessBoard = Board.createBlankBoard();
        boardPanel.drawBoard(chessBoard);
        this.gameFrame.add(boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(fenRetrieverPanel, BorderLayout.SOUTH);
        this.gameFrame.add(piecePanel, BorderLayout.NORTH);
        this.gameFrame.setVisible(true);
        this.blackPawnSelected = false;
        this.whitePawnSelected = false;
        this.blackRookSelected = false;
        this.whiteRookSelected = false;
        this.blackBishopSelected = false;
        this.whiteBishopSelected = false;
        this.blackKnightSelected = false;
        this.whiteKnightSelected = false;
        this.blackQueenSelected = false;
        this.whiteQueenSelected = false;
    }

    public void promptBoardPanel() {
        JButton ready = new JButton("Retrieve FEN");
        ready.setFont(font);
        ready.addActionListener(e -> {
            String color;
            if (fenRetrieverPanel.isWhiteSelected()) {
                color = "w";
            } else {
                color = "b";
            }
            String fen = FenUtilities.createFENFromGame(chessBoard, color);

            JTextPane f1 = new JTextPane();
            f1.setContentType("text");
            f1.setText(fen);
            f1.setBackground(null);
            f1.setFont(font);
            f1.setBorder(null);

            JOptionPane.showMessageDialog(gameFrame, f1);
        });

        JButton blackPawn = new JButton();
        JButton whitePawn = new JButton();
        JButton blackRook = new JButton();
        JButton whiteRook = new JButton();
        JButton blackKnight = new JButton();
        JButton whiteKnight = new JButton();
        JButton blackBishop = new JButton();
        JButton whiteBishop = new JButton();
        JButton blackQueen = new JButton();
        JButton whiteQueen = new JButton();

        try {
            blackPawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BP.png"))));
            whitePawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WP.png"))));
            blackRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BR.png"))));
            whiteRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WR.png"))));
            blackKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BN.png"))));
            whiteKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WN.png"))));
            blackBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BB.png"))));
            whiteBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WB.png"))));
            blackQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BQ.png"))));
            whiteQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WQ.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.piecePanel.addToPiecePanel(blackPawn);
        this.piecePanel.addToPiecePanel(blackRook);
        this.piecePanel.addToPiecePanel(blackKnight);
        this.piecePanel.addToPiecePanel(blackBishop);
        this.piecePanel.addToPiecePanel(blackQueen);
        this.piecePanel.addToPiecePanel(whitePawn);
        this.piecePanel.addToPiecePanel(whiteRook);
        this.piecePanel.addToPiecePanel(whiteKnight);
        this.piecePanel.addToPiecePanel(whiteBishop);
        this.piecePanel.addToPiecePanel(whiteQueen);

        blackPawn.addActionListener(e -> {
            try {
                if (blackPawnSelected) {
                    blackPawnSelected = false;
                    blackPawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BP.png"))));
                } else {
                    blackPawnSelected = true;
                    blackPawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BPG.png"))));
                }
                blackRookSelected = false;
                blackBishopSelected = false;
                blackKnightSelected = false;
                blackQueenSelected = false;
                whitePawnSelected = false;
                whiteRookSelected = false;
                whiteBishopSelected = false;
                whiteKnightSelected = false;
                whiteQueenSelected = false;
                whitePawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WP.png"))));
                blackRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BR.png"))));
                whiteRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WR.png"))));
                blackKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BN.png"))));
                whiteKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WN.png"))));
                blackBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BB.png"))));
                whiteBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WB.png"))));
                blackQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BQ.png"))));
                whiteQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WQ.png"))));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        blackRook.addActionListener(e1 -> {
            try {
                if (blackRookSelected) {
                    blackRookSelected = false;
                    blackRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BR.png"))));
                } else {
                    blackRookSelected = true;
                    blackRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BRG.png"))));
                }
                blackPawnSelected = false;
                blackBishopSelected = false;
                blackKnightSelected = false;
                blackQueenSelected = false;
                whitePawnSelected = false;
                whiteRookSelected = false;
                whiteBishopSelected = false;
                whiteKnightSelected = false;
                whiteQueenSelected = false;
                blackPawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BP.png"))));
                whitePawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WP.png"))));
                whiteRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WR.png"))));
                blackKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BN.png"))));
                whiteKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WN.png"))));
                blackBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BB.png"))));
                whiteBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WB.png"))));
                blackQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BQ.png"))));
                whiteQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WQ.png"))));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        blackKnight.addActionListener(e2 -> {
            try {
                if (blackKnightSelected) {
                    blackKnightSelected = false;
                    blackKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BN.png"))));
                } else {
                    blackKnightSelected = true;
                    blackKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BNG.png"))));
                }
                blackRookSelected = false;
                blackBishopSelected = false;
                blackPawnSelected = false;
                blackQueenSelected = false;
                whitePawnSelected = false;
                whiteRookSelected = false;
                whiteBishopSelected = false;
                whiteKnightSelected = false;
                whiteQueenSelected = false;
                blackPawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BP.png"))));
                whitePawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WP.png"))));
                blackRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BR.png"))));
                whiteRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WR.png"))));
                whiteKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WN.png"))));
                blackBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BB.png"))));
                whiteBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WB.png"))));
                blackQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BQ.png"))));
                whiteQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WQ.png"))));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        blackBishop.addActionListener(e3 -> {
            try {
                if (blackBishopSelected) {
                    blackBishopSelected = false;
                    blackBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BB.png"))));
                } else {
                    blackBishopSelected = true;
                    blackBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BBG.png"))));
                }
                blackRookSelected = false;
                blackPawnSelected = false;
                blackKnightSelected = false;
                blackQueenSelected = false;
                whitePawnSelected = false;
                whiteRookSelected = false;
                whiteBishopSelected = false;
                whiteKnightSelected = false;
                whiteQueenSelected = false;
                blackPawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BP.png"))));
                whitePawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WP.png"))));
                blackRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BR.png"))));
                whiteRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WR.png"))));
                blackKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BN.png"))));
                whiteKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WN.png"))));
                whiteBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WB.png"))));
                blackQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BQ.png"))));
                whiteQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WQ.png"))));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        blackQueen.addActionListener(e4 -> {
            try {
                if (blackQueenSelected) {
                    blackQueenSelected = false;
                    blackQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BQ.png"))));
                } else {
                    blackQueenSelected = true;
                    blackQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BQG.png"))));
                }
                blackRookSelected = false;
                blackBishopSelected = false;
                blackKnightSelected = false;
                blackPawnSelected = false;
                whitePawnSelected = false;
                whiteRookSelected = false;
                whiteBishopSelected = false;
                whiteKnightSelected = false;
                whiteQueenSelected = false;
                blackPawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BP.png"))));
                whitePawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WP.png"))));
                blackRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BR.png"))));
                whiteRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WR.png"))));
                blackKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BN.png"))));
                whiteKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WN.png"))));
                blackBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BB.png"))));
                whiteBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WB.png"))));
                whiteQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WQ.png"))));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        whitePawn.addActionListener(e5 -> {
            try {
                if (whitePawnSelected) {
                    whitePawnSelected = false;
                    whitePawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WP.png"))));
                } else {
                    whitePawnSelected = true;
                    whitePawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WPG.png"))));
                }
                blackRookSelected = false;
                blackBishopSelected = false;
                blackKnightSelected = false;
                blackQueenSelected = false;
                blackPawnSelected = false;
                whiteRookSelected = false;
                whiteBishopSelected = false;
                whiteKnightSelected = false;
                whiteQueenSelected = false;
                blackPawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BP.png"))));
                blackRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BR.png"))));
                whiteRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WR.png"))));
                blackKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BN.png"))));
                whiteKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WN.png"))));
                blackBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BB.png"))));
                whiteBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WB.png"))));
                blackQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BQ.png"))));
                whiteQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WQ.png"))));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        whiteRook.addActionListener(e6 -> {
            try {
                if (whiteRookSelected) {
                    whiteRookSelected = false;
                    whiteRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WR.png"))));
                } else {
                    whiteRookSelected = true;
                    whiteRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WRG.png"))));
                }
                blackRookSelected = false;
                blackBishopSelected = false;
                blackKnightSelected = false;
                blackQueenSelected = false;
                whitePawnSelected = false;
                blackPawnSelected = false;
                whiteBishopSelected = false;
                whiteKnightSelected = false;
                whiteQueenSelected = false;
                blackPawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BP.png"))));
                whitePawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WP.png"))));
                blackRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BR.png"))));
                blackKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BN.png"))));
                whiteKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WN.png"))));
                blackBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BB.png"))));
                whiteBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WB.png"))));
                blackQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BQ.png"))));
                whiteQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WQ.png"))));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        whiteKnight.addActionListener(e7 -> {
            try {
                if (whiteKnightSelected) {
                    whiteKnightSelected = false;
                    whiteKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WN.png"))));
                } else {
                    whiteKnightSelected = true;
                    whiteKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WNG.png"))));
                }
                blackRookSelected = false;
                blackBishopSelected = false;
                blackKnightSelected = false;
                blackQueenSelected = false;
                whitePawnSelected = false;
                whiteRookSelected = false;
                whiteBishopSelected = false;
                blackPawnSelected = false;
                whiteQueenSelected = false;
                blackPawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BP.png"))));
                whitePawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WP.png"))));
                blackRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BR.png"))));
                whiteRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WR.png"))));
                blackKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BN.png"))));
                blackBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BB.png"))));
                whiteBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WB.png"))));
                blackQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BQ.png"))));
                whiteQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WQ.png"))));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        whiteBishop.addActionListener(e8 -> {
            try {
                if (whiteBishopSelected) {
                    whiteBishopSelected = false;
                    whiteBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WB.png"))));
                } else {
                    whiteBishopSelected = true;
                    whiteBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WBG.png"))));
                }
                blackRookSelected = false;
                blackBishopSelected = false;
                blackKnightSelected = false;
                blackQueenSelected = false;
                whitePawnSelected = false;
                whiteRookSelected = false;
                blackPawnSelected = false;
                whiteKnightSelected = false;
                whiteQueenSelected = false;
                blackPawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BP.png"))));
                whitePawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WP.png"))));
                blackRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BR.png"))));
                whiteRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WR.png"))));
                blackKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BN.png"))));
                whiteKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WN.png"))));
                blackBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BB.png"))));
                blackQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BQ.png"))));
                whiteQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WQ.png"))));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        whiteQueen.addActionListener(e9 -> {
            try {
                if (whiteQueenSelected) {
                    whiteQueenSelected = false;
                    whiteQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WQ.png"))));
                } else {
                    whiteQueenSelected = true;
                    whiteQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WQG.png"))));
                }
                blackRookSelected = false;
                blackBishopSelected = false;
                blackKnightSelected = false;
                blackQueenSelected = false;
                whitePawnSelected = false;
                whiteRookSelected = false;
                whiteBishopSelected = false;
                whiteKnightSelected = false;
                blackPawnSelected = false;
                blackPawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BP.png"))));
                whitePawn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WP.png"))));
                blackRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BR.png"))));
                whiteRook.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WR.png"))));
                blackKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BN.png"))));
                whiteKnight.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WN.png"))));
                blackBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BB.png"))));
                whiteBishop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/WB.png"))));
                blackQueen.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/BQ.png"))));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        fenRetrieverPanel.addToEastPanel(ready);
    }

    private static class PiecePanel extends JPanel {

        PiecePanel() {
            super(new GridLayout(1, 10));
            Dimension GAME_OPTIONS_PANEL_DIMENSION = new Dimension(900, 100);
            this.setPreferredSize(GAME_OPTIONS_PANEL_DIMENSION);
            Color PANEL_COLOR = Color.decode("#D8CACA");
            this.setBackground(PANEL_COLOR);
            this.validate();
        }

        private void addToPiecePanel(JButton button) {
            this.add(button);
        }
    }
    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;
        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Board board) {
            removeAll();
            for (final TilePanel tilePanel : boardTiles) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel {
        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (isRightMouseButton(e)) {
                        sourceTile = null;
                    } else if (isLeftMouseButton(e)) {
                        if (sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileId);
                            Builder builder = new Builder();
                            if (blackPawnSelected) {
                                builder.setPiece(new Pawn(tileId, BLACK));
                            } else if (blackRookSelected) {
                                builder.setPiece(new Rook(tileId, BLACK));
                            } else if (blackKnightSelected) {
                                builder.setPiece(new Knight(tileId, BLACK));
                            } else if (blackBishopSelected) {
                                builder.setPiece(new Bishop(tileId, BLACK));
                            } else if (blackQueenSelected) {
                                builder.setPiece(new Queen(tileId, BLACK));
                            } else if (whitePawnSelected) {
                                builder.setPiece(new Pawn(tileId, WHITE));
                            } else if (whiteRookSelected) {
                                builder.setPiece(new Rook(tileId, WHITE));
                            } else if (whiteKnightSelected) {
                                builder.setPiece(new Knight(tileId, WHITE));
                            } else if (whiteBishopSelected) {
                                builder.setPiece(new Bishop(tileId, WHITE));
                            } else if (whiteQueenSelected) {
                                builder.setPiece(new Queen(tileId, WHITE));
                            }
                            int i = 0;
                            while (i < tileId) {
                                if (chessBoard.getTile(i).isOccupiedTile()) {
                                    builder.setPiece(chessBoard.getTile(i).getPiece());
                                }
                                i++;
                            }
                            int j = 63;
                            while (j > tileId) {
                                if (chessBoard.getTile(j).isOccupiedTile()) {
                                    builder.setPiece(chessBoard.getTile(j).getPiece());
                                }
                                j--;
                            }
                            builder.setMoveMaker(WHITE);
                            chessBoard = builder.build();
                            boardPanel.drawBoard(chessBoard);
                        }
                        sourceTile = null;
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });

            validate();
        }

        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            validate();
            repaint();
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if (board.getTile(this.tileId).isOccupiedTile()) {
                try {
                    final BufferedImage image = ImageIO.read(getClass().getResource(defaultPieceImagesPath + board.getTile(this.tileId).getPiece().getPieceColor().toString().charAt(0) +
                            board.getTile(this.tileId).getPiece().toString() + ".png"));
                    ImageIcon icon = new ImageIcon(image);
                    JLabel im = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth()-11, icon.getIconWidth()-11, Image.SCALE_SMOOTH)));
                    im.setPreferredSize(new Dimension(70,70));
                    add(im);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void assignTileColor() {
            if (BoardUtils.EIGHTH_RANK[this.tileId] ||
                    BoardUtils.SIXTH_RANK[this.tileId] ||
                    BoardUtils.FOURTH_RANK[this.tileId] ||
                    BoardUtils.SECOND_RANK[this.tileId]) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (BoardUtils.SEVENTH_RANK[this.tileId] ||
                    BoardUtils.FIFTH_RANK[this.tileId] ||
                    BoardUtils.THIRD_RANK[this.tileId] ||
                    BoardUtils.FIRST_RANK[this.tileId]) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }
}