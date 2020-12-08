package com.chess.gui;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    private static final Dimension GAME_OPTIONS_PANEL_DIMENSION = new Dimension(600, 85);
    private static final Color PANEL_COLOR = Color.decode("#EACACA");

    TitlePanel() {
        super(new BorderLayout());
        this.setPreferredSize(GAME_OPTIONS_PANEL_DIMENSION);
        this.setBackground(PANEL_COLOR);
        this.validate();

        JPanel titlePanel = new JPanel(new GridLayout(1, 1));
        titlePanel.setPreferredSize(new Dimension(600, 30));
        titlePanel.setBackground(PANEL_COLOR);

        JPanel pawnPanel = new JPanel(new GridLayout(1, 1));
        pawnPanel.setPreferredSize(new Dimension(600, 40));
        pawnPanel.setBackground(PANEL_COLOR);

        JPanel namePanel = new JPanel(new GridLayout(1, 1));
        namePanel.setPreferredSize(new Dimension(600, 30));
        namePanel.setBackground(PANEL_COLOR);

        final JLabel chessCenter = new JLabel("                                                           U.S. Chess Center");
        final JLabel pawnGame = new JLabel("                                              The Pawn Game");
        final JLabel name = new JLabel("                                                              by Tara Bobinac");

        chessCenter.setFont(new Font("sans-serif", Font.BOLD, 24));
        pawnGame.setFont(new Font("sans-serif", Font.BOLD, 30));
        name.setFont(new Font("sans-serif", Font.PLAIN, 25));
        titlePanel.add(chessCenter);
        pawnPanel.add(pawnGame);
        namePanel.add(name);

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(pawnPanel, BorderLayout.CENTER);
        this.add(namePanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }
}