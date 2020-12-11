package com.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TitlePanel extends JPanel {

    private static final Dimension GAME_OPTIONS_PANEL_DIMENSION = new Dimension(600, 80);
    private static final Color PANEL_COLOR = Color.decode("#EACACA");

    TitlePanel() {
        super(new BorderLayout());
        this.setPreferredSize(GAME_OPTIONS_PANEL_DIMENSION);
        this.setBackground(PANEL_COLOR);
        this.validate();

        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(PANEL_COLOR);

        JPanel pawnPanel = new JPanel(new GridBagLayout());
        pawnPanel.setBackground(PANEL_COLOR);

        JPanel namePanel = new JPanel(new GridBagLayout());
        namePanel.setBackground(PANEL_COLOR);

        final JLabel chessCenter = new JLabel("U.S. Chess Center");
        final JLabel pawnGame = new JLabel("The Pawn Game");
        final JLabel name = new JLabel("by Tara Bobinac");

        chessCenter.setFont(new Font("sans-serif", Font.BOLD, 21));
        chessCenter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chessCenter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://chessctr.org/"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        pawnGame.setFont(new Font("sans-serif", Font.BOLD, 22));
        pawnGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pawnGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/tarabobinac/PawnGame"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        name.setFont(new Font("sans-serif", Font.PLAIN, 21));
        name.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        name.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.linkedin.com/in/tarabobinac"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        titlePanel.add(chessCenter);
        pawnPanel.add(pawnGame);
        namePanel.add(name);

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(pawnPanel, BorderLayout.CENTER);
        this.add(namePanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }
}