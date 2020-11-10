package com.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSetup extends JDialog {

    private String gameType;

    GameSetup(final JFrame frame, final boolean modal) {
        super(frame, modal);
        final JPanel myPanel = new JPanel(new GridLayout(0, 1));

        final JRadioButton queensYes = new JRadioButton("Yes");
        final JRadioButton queensNo = new JRadioButton("No");
        final JRadioButton rooksYes = new JRadioButton("Yes");
        final JRadioButton rooksNo = new JRadioButton("No");
        final JRadioButton knightsYes = new JRadioButton("Yes");
        final JRadioButton knightsNo = new JRadioButton("No");
        final JRadioButton bishopsYes = new JRadioButton("Yes");
        final JRadioButton bishopsNo = new JRadioButton("No");

        getContentPane().add(myPanel);
        myPanel.add(new JLabel("Welcome to the Pawn Game! What other standard pieces would you like to add?"));

        final ButtonGroup queens = new ButtonGroup();
        queens.add(queensYes);
        queens.add(queensNo);
        queensNo.setSelected(true);

        final ButtonGroup rooks = new ButtonGroup();
        rooks.add(rooksYes);
        rooks.add(rooksNo);
        rooksNo.setSelected(true);

        final ButtonGroup knights = new ButtonGroup();
        knights.add(knightsYes);
        knights.add(knightsNo);
        knightsNo.setSelected(true);

        final ButtonGroup bishops = new ButtonGroup();
        bishops.add(bishopsYes);
        bishops.add(bishopsNo);
        bishopsNo.setSelected(true);

        myPanel.add(new JLabel("Queens?"));
        myPanel.add(queensYes);
        myPanel.add(queensNo);
        myPanel.add(new JLabel("Rooks?"));
        myPanel.add(rooksYes);
        myPanel.add(rooksNo);
        myPanel.add(new JLabel("Knights?"));
        myPanel.add(knightsYes);
        myPanel.add(knightsNo);
        myPanel.add(new JLabel("Bishops?"));
        myPanel.add(bishopsYes);
        myPanel.add(bishopsNo);

        final JButton cancelButton = new JButton("Cancel");
        final JButton okButton = new JButton("OK");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rooksNo.isSelected() && knightsNo.isSelected() && bishopsNo.isSelected() && queensNo.isSelected()) {
                    gameType = "P";
                } else if (rooksYes.isSelected() && knightsNo.isSelected() && bishopsNo.isSelected() && queensNo.isSelected()) {
                    gameType = "R";
                } else if (rooksNo.isSelected() && knightsYes.isSelected() && bishopsNo.isSelected() && queensNo.isSelected()) {
                    gameType = "N";
                } else if (rooksNo.isSelected() && knightsNo.isSelected() && bishopsYes.isSelected() && queensNo.isSelected()) {
                    gameType = "B";
                } else if (rooksNo.isSelected() && knightsNo.isSelected() && bishopsNo.isSelected() && queensYes.isSelected()) {
                    gameType = "Q";
                } else if (rooksYes.isSelected() && knightsYes.isSelected() && bishopsNo.isSelected() && queensNo.isSelected()) {
                    gameType = "RN";
                } else if (rooksYes.isSelected() && knightsYes.isSelected() && bishopsYes.isSelected() && queensNo.isSelected()) {
                    gameType = "RNB";
                } else if (rooksYes.isSelected() && knightsYes.isSelected() && bishopsYes.isSelected() && queensYes.isSelected()) {
                    gameType = "RNBQ";
                } else if (rooksYes.isSelected() && knightsNo.isSelected() && bishopsYes.isSelected() && queensNo.isSelected()) {
                    gameType = "RB";
                } else if (rooksYes.isSelected() && knightsNo.isSelected() && bishopsNo.isSelected() && queensYes.isSelected()) {
                    gameType = "RQ";
                } else if (rooksYes.isSelected() && knightsNo.isSelected() && bishopsYes.isSelected() && queensYes.isSelected()) {
                    gameType = "RBQ";
                } else if (rooksYes.isSelected() && knightsYes.isSelected() && bishopsNo.isSelected() && queensYes.isSelected()) {
                    gameType = "RNQ";
                } else if (rooksNo.isSelected() && knightsYes.isSelected() && bishopsYes.isSelected() && queensNo.isSelected()) {
                    gameType = "NB";
                } else if (rooksNo.isSelected() && knightsYes.isSelected() && bishopsYes.isSelected() && queensYes.isSelected()) {
                    gameType = "NBQ";
                } else if (rooksNo.isSelected() && knightsNo.isSelected() && bishopsYes.isSelected() && queensYes.isSelected()) {
                    gameType = "BQ";
                } else if (rooksNo.isSelected() && knightsYes.isSelected() && bishopsNo.isSelected() && queensYes.isSelected()) {
                    gameType = "NQ";
                }

                GameSetup.this.setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Cancel");
                GameSetup.this.setVisible(false);
            }
        });
        myPanel.add(cancelButton);
        myPanel.add(okButton);

        setLocationRelativeTo(frame);
        pack();
        setVisible(false);
    }

    void promptUser() {
        setVisible(true);
        repaint();
    }

    String getGameType() {
        return gameType;
    }
}