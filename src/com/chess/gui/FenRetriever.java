package com.chess.gui;

import javax.swing.*;
import java.awt.*;

public class FenRetriever extends JPanel {

    JPanel eastPanel;
    JRadioButton whitePlayer;
    private static final Dimension GAME_OPTIONS_PANEL_DIMENSION = new Dimension(600, 150);

    FenRetriever() {
        super(new GridLayout(1, 4));
        this.setPreferredSize(GAME_OPTIONS_PANEL_DIMENSION);
        Font font = new Font("sans-serif", Font.BOLD, 20);

        JPanel westPanel = new JPanel (new GridLayout(3, 1));
        eastPanel = new JPanel (new GridLayout(1, 1));
        this.add(westPanel, BorderLayout.WEST);
        this.add(eastPanel, BorderLayout.EAST);

        whitePlayer = new JRadioButton("White");
        whitePlayer.setFont(font);

        JRadioButton blackPlayer = new JRadioButton("Black");
        blackPlayer.setFont(font);

        JLabel playerColor = new JLabel("           Which color to play?:");
        playerColor.setFont(font);
        westPanel.add(playerColor);

        final ButtonGroup color = new ButtonGroup();
        color.add(whitePlayer);
        color.add(blackPlayer);
        whitePlayer.setSelected(true);

        westPanel.add(whitePlayer);
        westPanel.add(blackPlayer);

        this.validate();
        this.setVisible(true);
    }

    public void addToEastPanel(JButton button) {
        eastPanel.add(button);
    }

    public boolean isWhiteSelected() {
        return whitePlayer.isSelected();
    }
}
