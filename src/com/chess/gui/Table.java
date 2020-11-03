package com.chess.gui;

import javax.swing.*;
import java.awt.*;

public class Table {
    private final JFrame gameFrame;

    private static Dimension OUTER_FRAME_DIMENSION = new Dimension(1000, 1000);

    public Table() {
        this.gameFrame = new JFrame("PawnGame");
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);
    }
}
