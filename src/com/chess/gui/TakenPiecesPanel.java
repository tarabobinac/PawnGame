package com.chess.gui;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.google.common.primitives.Ints;
import javax.imageio.ImageIO;
import com.chess.gui.Table.MoveLog;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TakenPiecesPanel extends JPanel {

    private final JPanel westPanel;
    private final JPanel eastPanel;
    private static final Color PANEL_COLOR = Color.decode("#d9b382");
    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(200, 80);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    public TakenPiecesPanel() {
        super(new BorderLayout());
        this.setBackground(PANEL_COLOR);
        this.setBorder(PANEL_BORDER);
        this.westPanel= new JPanel(new GridLayout(15, 1));
        this.eastPanel = new JPanel (new GridLayout(15,1));
        this.westPanel.setBackground(PANEL_COLOR);
        this.eastPanel.setBackground(PANEL_COLOR);
        this.add(this.westPanel, BorderLayout.WEST);
        this.add(this.eastPanel, BorderLayout.EAST);
        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }

    public void redo(final MoveLog moveLog) {
        eastPanel.removeAll();
        westPanel.removeAll();

        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();

        for (final Move move : moveLog.getMoves()) {
            if (move.isAttack()) {
                final Piece takenPiece = move.getAttackedPiece();
                if (takenPiece.getPieceColor().isWhite()) {
                    whiteTakenPieces.add(takenPiece);
                } else if (takenPiece.getPieceColor().isBlack()) {
                    blackTakenPieces.add(takenPiece);
                } else {
                    throw new RuntimeException("should not reach this point.");
                }
            }
        }

        whiteTakenPieces.sort((o1, o2) -> Ints.compare(o1.getPieceValue(), o2.getPieceValue()));
        blackTakenPieces.sort((o1, o2) -> Ints.compare(o1.getPieceValue(), o2.getPieceValue()));

        for (final Piece takenPiece : whiteTakenPieces) {
            try {
                final BufferedImage image = ImageIO.read(getClass().getResource("/pieces/" + takenPiece.getPieceColor().toString().charAt(0) + "" + takenPiece.toString() + ".png"));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth()-40, icon.getIconWidth()-40, Image.SCALE_SMOOTH)));
                this.eastPanel.add(imageLabel);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        for (final Piece takenPiece : blackTakenPieces) {
            try {
                final BufferedImage image = ImageIO.read(getClass().getResource("/pieces/" + takenPiece.getPieceColor().toString().charAt(0) + "" + takenPiece.toString() + ".png"));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth()-40, icon.getIconWidth()-40, Image.SCALE_SMOOTH)));
                this.westPanel.add(imageLabel);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        validate();
    }
}