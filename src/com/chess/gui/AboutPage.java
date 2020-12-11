package com.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutPage {

    private final JFrame gameFrame;
    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(1250, 500);
    private final static Font plainFont = new Font("sans-serif", Font.PLAIN, 25);
    private final static Font boldFont = new Font("sans-serif", Font.BOLD, 25);

    public AboutPage() {

        this.gameFrame = new JFrame("Board Editor");
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);

        JPanel northPanel = new JPanel(new GridBagLayout());
        northPanel.setBackground(Color.decode("#efeee0"));
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel welcomeToThePawnGame = new JLabel("Welcome to the Pawn Game!");
        welcomeToThePawnGame.setForeground(Color.decode("#396495"));
        welcomeToThePawnGame.setFont(new Font("sans-serif", Font.BOLD, 30));

        JLabel thisGameIs = new JLabel("This game was created by the U.S. Chess Center to start kids off on their chess-playing journey.");
        thisGameIs.setFont(plainFont);

        JLabel name = new JLabel("The software was developed by Tara Bobinac.");
        name.setFont(plainFont);

        JLabel nothing2 = new JLabel("-");
        JLabel nothing3 = new JLabel("-");
        JLabel nothing4 = new JLabel("-");
        nothing2.setFont(plainFont);
        nothing3.setFont(plainFont);
        nothing4.setFont(plainFont);

        JLabel toLearnMore = new JLabel("To learn more about the U.S. Chess Center, visit their website: ");
        toLearnMore.setFont(boldFont);

        JLabel USChessCenterLink = new JLabel("www.chessctr.org/");
        USChessCenterLink.setFont(plainFont);
        USChessCenterLink.setForeground(Color.BLUE.darker());
        USChessCenterLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        USChessCenterLink.addMouseListener(new MouseAdapter() {
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

        JLabel githubLink = new JLabel("To see the code for the game on Github.com, click here: ");
        githubLink.setFont(boldFont);

        JLabel github = new JLabel("www.github.com/tarabobinac/PawnGame");
        github.setFont(plainFont);
        github.setForeground(Color.BLUE.darker());
        github.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        github.addMouseListener(new MouseAdapter() {
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

        JLabel toLearnMoreAboutTara = new JLabel("To learn more about Tara, take a look at her LinkedIn profile: ");
        toLearnMoreAboutTara.setFont(boldFont);

        JLabel linkedIn = new JLabel("www.linkedin.com/in/tarabobinac");
        linkedIn.setFont(plainFont);
        linkedIn.setForeground(Color.BLUE.darker());
        linkedIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkedIn.addMouseListener(new MouseAdapter() {
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

        constraints.gridy = 0;
        northPanel.add(welcomeToThePawnGame, constraints);
        constraints.gridy = 1;
        northPanel.add(thisGameIs, constraints);
        constraints.gridy = 2;
        northPanel.add(name, constraints);
        constraints.gridy = 3;
        northPanel.add(nothing2, constraints);
        constraints.gridy = 4;
        northPanel.add(toLearnMore, constraints);
        constraints.gridy = 5;
        northPanel.add(USChessCenterLink, constraints);
        constraints.gridy = 6;
        northPanel.add(nothing3, constraints);
        constraints.gridy = 7;
        northPanel.add(githubLink, constraints);
        constraints.gridy = 8;
        northPanel.add(github, constraints);
        constraints.gridy = 9;
        northPanel.add(nothing4, constraints);
        constraints.gridy = 10;
        northPanel.add(toLearnMoreAboutTara, constraints);
        constraints.gridy = 11;
        northPanel.add(linkedIn, constraints);

        gameFrame.add(northPanel, BorderLayout.CENTER);
    }

    public void promptAboutPage() {
        this.gameFrame.setVisible(true);
    }
}
