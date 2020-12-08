package com.chess.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameOptionsPanel extends JPanel {

    private static final Dimension GAME_OPTIONS_PANEL_DIMENSION = new Dimension(600, 110);
    private static final Color PANEL_COLOR = Color.decode("#D8CACA");
    private JButton resetButton;
    private JPanel westPanel;
    private JPanel centerPanel;
    private JPanel eastPanel;
    private JCheckBox queens;
    private JCheckBox rooks;
    private JCheckBox knights;
    private JCheckBox bishops;
    private JRadioButton blackPlayer;
    private JRadioButton whitePlayer;

    GameOptionsPanel() {
        super(new BorderLayout());
        this.setPreferredSize(GAME_OPTIONS_PANEL_DIMENSION);
        this.setBackground(PANEL_COLOR);
        this.validate();
        this.setVisible(false);
    }

    public void promptStandardGameServer() {
        this.removeAll();
        this.standardGameServer();
        this.revalidate();
        this.repaint();
    }

    public void promptStandardGameClient() {
        this.removeAll();
        this.standardGameClient();
        this.revalidate();
        this.repaint();
    }

    public void promptFENGameServer() {
        this.removeAll();
        this.FENGameServer();
        this.revalidate();
        this.repaint();
    }

    public void promptFENGameClient() {
        this.removeAll();
        this.FENGameClient();
        this.revalidate();
        this.repaint();
    }

    public void promptLoadedGameServer() {
        this.removeAll();
        this.LoadedGameServer();
        this.revalidate();
        this.repaint();
    }

    public void FENGameClient() {

        Font font = new Font("sans-serif", Font.BOLD, 20);

        westPanel = new JPanel (new GridLayout(1, 3));
        westPanel.setPreferredSize(new Dimension(600, 150));
        westPanel.setBackground(PANEL_COLOR);

        this.add(westPanel, BorderLayout.CENTER);

        whitePlayer = new JRadioButton("White");
        whitePlayer.setFont(font);
        whitePlayer.setBackground(Color.decode("#D8CACA"));

        blackPlayer = new JRadioButton("Black");
        blackPlayer.setFont(font);
        blackPlayer.setBackground(Color.decode("#D8CACA"));

        JLabel playerColor = new JLabel("         Your current color:");
        playerColor.setFont(font);
        westPanel.add(playerColor);

        final ButtonGroup color = new ButtonGroup();
        color.add(whitePlayer);
        color.add(blackPlayer);
        whitePlayer.setSelected(true);

        westPanel.add(whitePlayer);
        westPanel.add(blackPlayer);

        this.setVisible(true);
    }

    public void LoadedGameServer() {

        Font font = new Font("sans-serif", Font.BOLD, 20);

        westPanel = new JPanel (new GridLayout(1, 3));
        eastPanel = new JPanel (new GridLayout(1, 1));

        westPanel.setPreferredSize(new Dimension(400, 150));
        eastPanel.setPreferredSize(new Dimension(200, 150));

        westPanel.setBackground(PANEL_COLOR);

        this.add(westPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);

        whitePlayer = new JRadioButton("White");
        whitePlayer.setFont(font);
        whitePlayer.setBackground(Color.decode("#D8CACA"));

        blackPlayer = new JRadioButton("Black");
        blackPlayer.setFont(font);
        blackPlayer.setBackground(Color.decode("#D8CACA"));

        JLabel playerColor = new JLabel("       Pick your color:   ");
        playerColor.setFont(font);
        westPanel.add(playerColor);

        final ButtonGroup color = new ButtonGroup();
        color.add(whitePlayer);
        color.add(blackPlayer);
        whitePlayer.setSelected(true);

        westPanel.add(whitePlayer);
        westPanel.add(blackPlayer);

        this.setVisible(true);
    }

    public void FENGameServer() {

        Font font = new Font("sans-serif", Font.BOLD, 20);

        westPanel = new JPanel (new GridLayout(3, 1));
        centerPanel = new JPanel (new GridLayout(1,1));
        eastPanel = new JPanel (new GridLayout(1, 1));

        westPanel.setPreferredSize(new Dimension(200, 150));
        centerPanel.setPreferredSize(new Dimension(200, 150));
        eastPanel.setPreferredSize(new Dimension(200, 150));

        westPanel.setBackground(PANEL_COLOR);

        this.add(westPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);

        whitePlayer = new JRadioButton("White");
        whitePlayer.setFont(font);
        whitePlayer.setBackground(Color.decode("#D8CACA"));

        blackPlayer = new JRadioButton("Black");
        blackPlayer.setFont(font);
        blackPlayer.setBackground(Color.decode("#D8CACA"));

        JLabel playerColor = new JLabel("Pick your color:");
        playerColor.setFont(font);
        westPanel.add(playerColor);

        final ButtonGroup color = new ButtonGroup();
        color.add(whitePlayer);
        color.add(blackPlayer);
        whitePlayer.setSelected(true);

        westPanel.add(whitePlayer);
        westPanel.add(blackPlayer);

        resetButton = new JButton("RESET");
        resetButton.setFont(new Font("sans-serif", Font.BOLD, 30));
        centerPanel.add(resetButton);

        this.setVisible(true);
    }

    public void standardGameClient() {

        Font smallerFont = new Font("sans-serif", Font.BOLD, 20);

        westPanel = new JPanel (new GridLayout(3, 1));
        centerPanel = new JPanel (new GridLayout(2,4));

        westPanel.setBackground(PANEL_COLOR);
        centerPanel.setBackground(PANEL_COLOR);

        this.add(westPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);

        whitePlayer = new JRadioButton("White");
        whitePlayer.setFont(smallerFont);
        whitePlayer.setBackground(Color.decode("#D8CACA"));

        blackPlayer = new JRadioButton("Black");
        blackPlayer.setFont(smallerFont);
        blackPlayer.setBackground(Color.decode("#D8CACA"));

        JLabel playerColor = new JLabel("Your current color:");
        playerColor.setFont(smallerFont);
        westPanel.add(playerColor);

        final ButtonGroup color = new ButtonGroup();
        color.add(whitePlayer);
        color.add(blackPlayer);
        whitePlayer.setSelected(true);

        westPanel.add(whitePlayer);
        westPanel.add(blackPlayer);

        try {

            Font font = new Font("sans-serif", Font.BOLD, 25);

            queens = new JCheckBox("Queens", false);
            queens.setFont(font);
            queens.setBackground(Color.decode("#D8CACA"));

            BufferedImage q = ImageIO.read(getClass().getResource("/pieces/BQ.png"));
            ImageIcon qIcon = new ImageIcon(q);
            JLabel queen = new JLabel(new ImageIcon(qIcon.getImage().getScaledInstance(qIcon.getIconWidth()-20, qIcon.getIconWidth()-20, Image.SCALE_SMOOTH)));

            rooks = new JCheckBox("Rooks", false);
            rooks.setFont(font);
            rooks.setBackground(Color.decode("#D8CACA"));

            BufferedImage r = ImageIO.read(getClass().getResource("/pieces/WR.png"));
            ImageIcon rIcon = new ImageIcon(r);
            JLabel rook = new JLabel(new ImageIcon(rIcon.getImage().getScaledInstance(rIcon.getIconWidth()-20, rIcon.getIconWidth()-20, Image.SCALE_SMOOTH)));

            knights = new JCheckBox("Knights", false);
            knights.setFont(font);
            knights.setBackground(Color.decode("#D8CACA"));

            BufferedImage n = ImageIO.read(getClass().getResource("/pieces/BN.png"));
            ImageIcon nIcon = new ImageIcon(n);
            JLabel knight = new JLabel(new ImageIcon(nIcon.getImage().getScaledInstance(nIcon.getIconWidth()-20, nIcon.getIconWidth()-20, Image.SCALE_SMOOTH)));

            bishops = new JCheckBox("Bishops", false);
            bishops.setFont(font);
            bishops.setBackground(Color.decode("#D8CACA"));

            BufferedImage b = ImageIO.read(getClass().getResource("/pieces/WB.png"));
            ImageIcon bIcon = new ImageIcon(b);
            JLabel bishop = new JLabel(new ImageIcon(bIcon.getImage().getScaledInstance(bIcon.getIconWidth()-20, bIcon.getIconWidth()-20, Image.SCALE_SMOOTH)));

            centerPanel.add(queens);
            centerPanel.add(rooks);
            centerPanel.add(knights);
            centerPanel.add(bishops);
            centerPanel.add(queen);
            centerPanel.add(rook);
            centerPanel.add(knight);
            centerPanel.add(bishop);

        } catch (IOException e) {
                e.printStackTrace();
        }

        this.setVisible(true);
    }

    public void standardGameServer() {

        Font smallerFont = new Font("sans-serif", Font.BOLD, 20);

        westPanel = new JPanel (new GridLayout(3, 1));
        centerPanel = new JPanel (new GridLayout(2,4));
        eastPanel = new JPanel (new GridLayout(1, 1));

        eastPanel.setPreferredSize(new Dimension(200, 150));

        westPanel.setBackground(PANEL_COLOR);
        centerPanel.setBackground(PANEL_COLOR);

        this.add(westPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);

        whitePlayer = new JRadioButton("White");
        whitePlayer.setFont(smallerFont);
        whitePlayer.setBackground(Color.decode("#D8CACA"));

        blackPlayer = new JRadioButton("Black");
        blackPlayer.setFont(smallerFont);
        blackPlayer.setBackground(Color.decode("#D8CACA"));

        JLabel playerColor = new JLabel("Pick your color:");
        playerColor.setFont(smallerFont);
        westPanel.add(playerColor);

        final ButtonGroup color = new ButtonGroup();
        color.add(whitePlayer);
        color.add(blackPlayer);
        whitePlayer.setSelected(true);

        westPanel.add(whitePlayer);
        westPanel.add(blackPlayer);

        try {

            Font font = new Font("sans-serif", Font.BOLD, 25);

            queens = new JCheckBox("Queens", false);
            queens.setFont(font);
            queens.setBackground(Color.decode("#D8CACA"));

            BufferedImage q = ImageIO.read(getClass().getResource("/pieces/BQ.png"));
            ImageIcon qIcon = new ImageIcon(q);
            JLabel queen = new JLabel(new ImageIcon(qIcon.getImage().getScaledInstance(qIcon.getIconWidth()-20, qIcon.getIconWidth()-20, Image.SCALE_SMOOTH)));

            rooks = new JCheckBox("Rooks", false);
            rooks.setFont(font);
            rooks.setBackground(Color.decode("#D8CACA"));

            BufferedImage r = ImageIO.read(getClass().getResource("/pieces/WR.png"));
            ImageIcon rIcon = new ImageIcon(r);
            JLabel rook = new JLabel(new ImageIcon(rIcon.getImage().getScaledInstance(rIcon.getIconWidth()-20, rIcon.getIconWidth()-20, Image.SCALE_SMOOTH)));

            knights = new JCheckBox("Knights", false);
            knights.setFont(font);
            knights.setBackground(Color.decode("#D8CACA"));

            BufferedImage n = ImageIO.read(getClass().getResource("/pieces/BN.png"));
            ImageIcon nIcon = new ImageIcon(n);
            JLabel knight = new JLabel(new ImageIcon(nIcon.getImage().getScaledInstance(nIcon.getIconWidth()-20, nIcon.getIconWidth()-20, Image.SCALE_SMOOTH)));

            bishops = new JCheckBox("Bishops", false);
            bishops.setFont(font);
            bishops.setBackground(Color.decode("#D8CACA"));

            BufferedImage b = ImageIO.read(getClass().getResource("/pieces/WB.png"));
            ImageIcon bIcon = new ImageIcon(b);
            JLabel bishop = new JLabel(new ImageIcon(bIcon.getImage().getScaledInstance(bIcon.getIconWidth()-20, bIcon.getIconWidth()-20, Image.SCALE_SMOOTH)));

            centerPanel.add(queens);
            centerPanel.add(rooks);
            centerPanel.add(knights);
            centerPanel.add(bishops);
            centerPanel.add(queen);
            centerPanel.add(rook);
            centerPanel.add(knight);
            centerPanel.add(bishop);

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setVisible(true);

    }

    public void setQueens(boolean implemented) {
        queens.setSelected(implemented);
    }

    public void setRooks(boolean implemented) {
        rooks.setSelected(implemented);
    }

    public void setKnights(boolean implemented) {
        knights.setSelected(implemented);
    }

    public void setBishops(boolean implemented) {
        bishops.setSelected(implemented);
    }

    public void setColor(String color) {
        if (color.equals("w")) {
            whitePlayer.setSelected(true);
        } else if (color.equals("b")) {
            blackPlayer.setSelected(true);
        }
    }

    public void addToEastPanel(JButton button) {
        this.eastPanel.add(button);
    }

    public JButton getResetButton() {
        return this.resetButton;
    }

    private void disableQueens() {
        this.queens.setEnabled(false);
    }

    private void disableRooks() {
        this.rooks.setEnabled(false);
    }

    private void disableKnights() {
        this.knights.setEnabled(false);
    }

    private void disableBishops() {
        this.bishops.setEnabled(false);
    }

    private void disableWhitePlayer() {
        this.whitePlayer.setEnabled(false);
    }

    private void disableBlackPlayer() {
        this.blackPlayer.setEnabled(false);
    }

    public void disableFENButtons() {
        disableWhitePlayer();
        disableBlackPlayer();
    }

    public void disableStandardButtons() {
        disableQueens();
        disableRooks();
        disableKnights();
        disableBishops();
        disableWhitePlayer();
        disableBlackPlayer();
    }

    public JCheckBox getQueens() {
        return this.queens;
    }

    public JCheckBox getRooks() {
        return this.rooks;
    }

    public JCheckBox getKnights() {
        return this.knights;
    }

    public JCheckBox getBishops() {
        return this.bishops;
    }

    public Color getPlayerColor() {
        if (whitePlayer.isSelected()) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }
}