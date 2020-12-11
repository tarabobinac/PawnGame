package com.chess.gui;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.*;
import com.chess.engine.player.MoveTransition;
import com.chess.fen.FenUtilities;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.*;

import static com.chess.engine.Color.BLACK;
import static com.chess.engine.Color.WHITE;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {

    private final Font font;
    private final JFrame gameFrame;

    private MoveLog moveLog;
    private Board chessBoard;
    private Board resetBoard;

    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final GameOptionsPanel gameOptionsPanel;
    private final BoardPanel boardPanel;

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;

    private boolean gameDone;
    private boolean blackStarts;
    private int numGames;
    private int completedGames;
    private String winner;
    private String gameType;
    private com.chess.engine.Color color;
    private BoardDirection boardDirection;
    private Server server;
    private Client client;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(1200, 1300);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(800, 800);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(20, 20);
    private final static String defaultPieceImagesPath = "/pieces/";
    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#80C98E");
    private final ArrayList<String> lines;

    public Table() {

        this.font = new Font("sans-serif", Font.PLAIN, 30);

        UIManager.put("Menu.font", new Font("sans-serif", Font.PLAIN, 35));
        UIManager.put("MenuItem.font", new Font("sans-serif", Font.PLAIN, 35));
        UIManager.put("CheckBoxMenuItem.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TextField.font", font);

        this.gameFrame = new JFrame();
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.chessBoard = Board.createStandardBoard();
        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.gameOptionsPanel = new GameOptionsPanel();
        this.boardPanel = new BoardPanel();
        TitlePanel titlePanel = new TitlePanel();
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.add(this.gameOptionsPanel, BorderLayout.SOUTH);
        this.gameFrame.add(titlePanel, BorderLayout.NORTH);
        this.moveLog = new MoveLog();
        this.gameDone = false;
        this.numGames = 0;
        this.completedGames = 0;
        this.gameType = "none";
        this.winner = "none";
        this.gameType = "none";
        this.boardDirection = BoardDirection.NORMAL;
        this.lines = new ArrayList<>();

        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createEditMenu());
        tableMenuBar.add(createOptionsMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            AboutPage aboutPage = new AboutPage();
            aboutPage.promptAboutPage();
        });

        final JMenuItem savePGNItem = new JMenuItem("Save GamePlay File");
        savePGNItem.addActionListener(e -> writeGameToFile(resetBoard, moveLog));

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));

        fileMenu.add(aboutItem);
        fileMenu.add(savePGNItem);
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    public static String getIP() throws Exception {
        URL IPAddress = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(IPAddress.openStream()));
            return in.readLine();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Piece getPiece(String s, int current, boolean attack) {
        Piece piece = null;
        switch (s) {
            case "P":
                if (chessBoard.currentPlayer().getColor().isWhite()) {
                    if (attack) {
                        piece = new Pawn(current, BLACK);
                    } else {
                        piece = new Pawn(current, WHITE);
                    }
                } else if (chessBoard.currentPlayer().getColor().isBlack()) {
                    if (attack) {
                        piece = new Pawn(current, WHITE);
                    } else {
                        piece = new Pawn(current, BLACK);
                    }
                }
                break;
            case "Q":
                if (chessBoard.currentPlayer().getColor().isWhite()) {
                    if (attack) {
                        piece = new Queen(current, BLACK);
                    } else {
                        piece = new Queen(current, WHITE);
                    }
                } else if (chessBoard.currentPlayer().getColor().isBlack()) {
                    if (attack) {
                        piece = new Queen(current, WHITE);
                    } else {
                        piece = new Queen(current, BLACK);
                    }
                }
                break;
            case "R":
                if (chessBoard.currentPlayer().getColor().isWhite()) {
                    if (attack) {
                        piece = new Rook(current, BLACK);
                    } else {
                        piece = new Rook(current, WHITE);
                    }
                } else if (chessBoard.currentPlayer().getColor().isBlack()) {
                    if (attack) {
                        piece = new Rook(current, WHITE);
                    } else {
                        piece = new Rook(current, BLACK);
                    }
                }
                break;
            case "N":
                if (chessBoard.currentPlayer().getColor().isWhite()) {
                    if (attack) {
                        piece = new Knight(current, BLACK);
                    } else {
                        piece = new Knight(current, WHITE);
                    }
                } else if (chessBoard.currentPlayer().getColor().isBlack()) {
                    if (attack) {
                        piece = new Knight(current, WHITE);
                    } else {
                        piece = new Knight(current, BLACK);
                    }
                }
                break;
            case "B":
                if (chessBoard.currentPlayer().getColor().isWhite()) {
                    if (attack) {
                        piece = new Bishop(current, BLACK);
                    } else {
                        piece = new Bishop(current, WHITE);
                    }
                } else if (chessBoard.currentPlayer().getColor().isBlack()) {
                    if (attack) {
                        piece = new Bishop(current, WHITE);
                    } else {
                        piece = new Bishop(current, BLACK);
                    }
                }
                break;
        }
        return piece;
    }

    private void promptServer() {
        if (server != null) {
            if (server.getInputStream() != null && server.getOutputStream() != null && server.getSocket() != null) {
                try {
                    server.clearSome();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                server.getServerSocket().close();
                server = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (client != null) {
            if (client.getInputStream() != null && client.getOutputStream() != null) {
                try {
                    client.clearSome();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                client.getSocket().close();
                client = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        gameDone = false;
        numGames = 0;
        completedGames = 0;
        gameType = "none";
        gameType = "none";
        lines.clear();
        moveLog.clear();
        takenPiecesPanel.redo(moveLog);
        gameHistoryPanel.redo(moveLog);
        boardDirection = BoardDirection.NORMAL;
        winner = "none";
        String[] game = {"Load from file", "Input FEN", "Standard"};
        JLabel gamesMessage = new JLabel("Which type of game would you like to start?");
        gamesMessage.setFont(font);
        int numGame = JOptionPane.showOptionDialog(null, gamesMessage, null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, game, game[0]);
        if (numGame == 0) {
            loadServerGameFromFile();
        } else if (numGame == 1) {
            loadServerGameFromFEN();
        } else if (numGame == 2) {
            loadStandardServerGame();
        }
    }

    private void loadStandardServerGame() {
        gameType = "Standard";
        try {
            String[] options = {"1", "2", "4"};
            JLabel numGamesMessage = new JLabel("How many games will there be in your match?");
            numGamesMessage.setFont(font);
            int numGameArrayList = JOptionPane.showOptionDialog(null, numGamesMessage, null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (numGameArrayList == 0) {
                numGames = 1;
            } else if (numGameArrayList == 1) {
                numGames = 2;
            } else if (numGameArrayList == 2) {
                numGames = 4;
            }
            if (numGames == 1 || numGames == 2 || numGames == 4) {
                server = new Server();
                gameOptionsPanel.promptStandardGameServer();
                JButton ready = new JButton("PLAY");
                ready.setFont(new Font("sans-serif", Font.BOLD, 30));
                ready.addActionListener(e13 -> {
                    if (!gameOptionsPanel.getRooks().isSelected() && !gameOptionsPanel.getKnights().isSelected() && !gameOptionsPanel.getBishops().isSelected() && !gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardBoard();
                    } else if (gameOptionsPanel.getRooks().isSelected() && !gameOptionsPanel.getKnights().isSelected() && !gameOptionsPanel.getBishops().isSelected() && !gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardRookBoard();
                    } else if (!gameOptionsPanel.getRooks().isSelected() && gameOptionsPanel.getKnights().isSelected() && !gameOptionsPanel.getBishops().isSelected() && !gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardKnightBoard();
                    } else if (!gameOptionsPanel.getRooks().isSelected() && !gameOptionsPanel.getKnights().isSelected() && gameOptionsPanel.getBishops().isSelected() && !gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardBishopBoard();
                    } else if (!gameOptionsPanel.getRooks().isSelected() && !gameOptionsPanel.getKnights().isSelected() && !gameOptionsPanel.getBishops().isSelected() && gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardQueenBoard();
                    } else if (gameOptionsPanel.getRooks().isSelected() && gameOptionsPanel.getKnights().isSelected() && !gameOptionsPanel.getBishops().isSelected() && !gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardRookKnightBoard();
                    } else if (gameOptionsPanel.getRooks().isSelected() && gameOptionsPanel.getKnights().isSelected() && gameOptionsPanel.getBishops().isSelected() && !gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardRookKnightBishopBoard();
                    } else if (gameOptionsPanel.getRooks().isSelected() && gameOptionsPanel.getKnights().isSelected() && gameOptionsPanel.getBishops().isSelected() && gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardRookKnightBishopQueenBoard();
                    } else if (gameOptionsPanel.getRooks().isSelected() && !gameOptionsPanel.getKnights().isSelected() && gameOptionsPanel.getBishops().isSelected() && !gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardRookBishopBoard();
                    } else if (gameOptionsPanel.getRooks().isSelected() && !gameOptionsPanel.getKnights().isSelected() && !gameOptionsPanel.getBishops().isSelected() && gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardRookQueenBoard();
                    } else if (gameOptionsPanel.getRooks().isSelected() && !gameOptionsPanel.getKnights().isSelected() && gameOptionsPanel.getBishops().isSelected() && gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardRookBishopQueenBoard();
                    } else if (gameOptionsPanel.getRooks().isSelected() && gameOptionsPanel.getKnights().isSelected() && !gameOptionsPanel.getBishops().isSelected() && gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardRookKnightQueenBoard();
                    } else if (!gameOptionsPanel.getRooks().isSelected() && gameOptionsPanel.getKnights().isSelected() && gameOptionsPanel.getBishops().isSelected() && !gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardKnightBishopBoard();
                    } else if (!gameOptionsPanel.getRooks().isSelected() && gameOptionsPanel.getKnights().isSelected() && gameOptionsPanel.getBishops().isSelected() && gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardKnightBishopQueenBoard();
                    } else if (!gameOptionsPanel.getRooks().isSelected() && !gameOptionsPanel.getKnights().isSelected() && gameOptionsPanel.getBishops().isSelected() && gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardBishopQueenBoard();
                    } else if (!gameOptionsPanel.getRooks().isSelected() && gameOptionsPanel.getKnights().isSelected() && !gameOptionsPanel.getBishops().isSelected() && gameOptionsPanel.getQueens().isSelected()) {
                        chessBoard = Board.createStandardKnightQueenBoard();
                    }
                    resetBoard = chessBoard;
                    blackStarts = false;
                    if (gameOptionsPanel.getPlayerColor() == Color.WHITE) {
                        color = WHITE;
                        boardDirection = BoardDirection.NORMAL;
                    } else {
                        color = BLACK;
                        boardDirection = BoardDirection.FLIPPED;
                    }
                    boardPanel.drawBoard(chessBoard);
                    try {
                        JTextPane standard = new JTextPane();
                        standard.setContentType("text");
                        standard.setText("     Waiting for other player to connect...\n\nPresent this IP address to the other player:\n                  " + getIP());
                        standard.setBackground(null);
                        standard.setFont(font);
                        standard.setBorder(null);
                        JOptionPane.showMessageDialog(gameFrame, standard);
                        server.setSocket(server.getServerSocket());
                        if (color == WHITE) {
                            JLabel success = new JLabel("Connected! White (you) starts the game.");
                            success.setFont(font);
                            JOptionPane.showMessageDialog(gameFrame, success);
                        } else if (color == BLACK) {
                            JTextPane success = new JTextPane();
                            success.setContentType("text");
                            success.setText("Connected! You are black.\n\nClick the center of the board and wait for white to make their turn.");
                            success.setBackground(null);
                            success.setFont(font);
                            success.setBorder(null);
                            JOptionPane.showMessageDialog(gameFrame, success);
                        }
                    } catch (Exception ioException) {
                        JLabel issue = new JLabel("Something went wrong! Exit the program and try again.");
                        issue.setFont(font);
                        JOptionPane.showMessageDialog(gameFrame, issue);
                        ioException.printStackTrace();
                    }
                    try {
                        server.setObjectInputStream(server.getSocket());
                        server.setObjectOutputStream(server.getSocket());
                        server.getOutputStream().writeObject(gameType);
                        server.getOutputStream().writeObject(chessBoard);
                        if (color == WHITE) {
                            server.getOutputStream().writeObject("b");
                        } else {
                            server.getOutputStream().writeObject("w");
                        }
                        server.getOutputStream().writeObject(gameOptionsPanel.getQueens().isSelected());
                        server.getOutputStream().writeObject(gameOptionsPanel.getRooks().isSelected());
                        server.getOutputStream().writeObject(gameOptionsPanel.getKnights().isSelected());
                        server.getOutputStream().writeObject(gameOptionsPanel.getBishops().isSelected());
                        server.getOutputStream().writeObject(numGames);
                    } catch (IOException ioException) {
                        JLabel issue = new JLabel("Something went wrong! Exit the program and try again.");
                        issue.setFont(font);
                        JOptionPane.showMessageDialog(gameFrame, issue);
                        ioException.printStackTrace();
                    }
                    ready.setEnabled(false);
                    gameOptionsPanel.disableStandardButtons();
                });
                gameOptionsPanel.addToEastPanel(ready);
            }
        } catch (IOException ioException) {
            JLabel issue = new JLabel("Something went wrong! It seems there is another program running on the network...");
            issue.setFont(font);
            JOptionPane.showMessageDialog(gameFrame, issue);
            ioException.printStackTrace();
        }
    }

    private void loadServerGameFromFEN() {
        gameType = "FEN";
        try {
            JLabel inputFEN = new JLabel("Input FEN");
            inputFEN.setFont(font);
            String fenString = "";
            fenString = JOptionPane.showInputDialog(inputFEN);
            JLabel error = new JLabel("Invalid FEN");
            error.setFont(font);
            if (fenString == null) {
                return;
            } else if (fenString.equals("")) {
                JLabel emptyFEN = new JLabel("FEN cannot be empty");
                emptyFEN.setFont(font);
                JOptionPane.showMessageDialog(gameFrame, emptyFEN);
                return;
            } else if (fenString.contains("k") || fenString.contains("K") || !fenString.contains("p") || !fenString.contains("P") || !(fenString.contains("w - ") || fenString.contains("b - "))) {
                JOptionPane.showMessageDialog(gameFrame, error);
                return;
            } else {
                Board checkBoard = FenUtilities.createGameFromFEN(fenString);
                if (checkBoard.whitePlayer().getLegalMoves().isEmpty() || checkBoard.blackPlayer().getLegalMoves().isEmpty()) {
                    JOptionPane.showMessageDialog(gameFrame, error);
                    return;
                }
                for (int i = 56; i < 64; i++) {
                    if (checkBoard.getTile(i).isOccupiedTile() && checkBoard.getTile(i).toString().equals("p")) {
                        JOptionPane.showMessageDialog(gameFrame, error);
                        return;
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (checkBoard.getTile(i).isOccupiedTile() && checkBoard.getTile(i).toString().equals("P")) {
                        JOptionPane.showMessageDialog(gameFrame, error);
                        return;
                    }
                }
                server = new Server();
                chessBoard = FenUtilities.createGameFromFEN(fenString);
                resetBoard = chessBoard;
                blackStarts = chessBoard.currentPlayer().getColor() == BLACK;
            }
            String[] options = {"1", "2", "4"};
            JLabel numGamesMessage = new JLabel("How many games will there be in your match?");
            numGamesMessage.setFont(font);
            int numGameArrayList = JOptionPane.showOptionDialog(null, numGamesMessage, null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (numGameArrayList == 0) {
                numGames = 1;
            } else if (numGameArrayList == 1) {
                numGames = 2;
            } else if (numGameArrayList == 2) {
                numGames = 4;
            }

            if (numGames == 1 || numGames == 2 || numGames == 4) {
                gameOptionsPanel.promptFENGameServer();
                JButton ready = new JButton("PLAY");
                ready.setFont(new Font("sans-serif", Font.BOLD, 30));
                ready.addActionListener(e1 -> {
                    if (gameOptionsPanel.getPlayerColor() == Color.WHITE) {
                        color = WHITE;
                        boardDirection = BoardDirection.NORMAL;
                    } else {
                        color = BLACK;
                        boardDirection = BoardDirection.FLIPPED;
                    }
                    boardPanel.drawBoard(chessBoard);
                    try {
                        JTextPane FEN = new JTextPane();
                        FEN.setContentType("text");
                        FEN.setText("     Waiting for other player to connect...\n\nPresent this IP address to the other player:\n                  " + getIP());
                        FEN.setBackground(null);
                        FEN.setFont(font);
                        FEN.setBorder(null);
                        JOptionPane.showMessageDialog(gameFrame, FEN);
                        server.setSocket(server.getServerSocket());
                        if (color == WHITE && chessBoard.currentPlayer().getColor() == WHITE) {
                            JLabel success = new JLabel("Connected! White (you) starts the game.");
                            success.setFont(font);
                            JOptionPane.showMessageDialog(gameFrame, success);
                        } else if (color == WHITE && chessBoard.currentPlayer().getColor() == BLACK) {
                            JTextPane success = new JTextPane();
                            success.setContentType("text");
                            success.setText("Connected! You are white.\nClick the center of the board and wait for black to make their turn.");
                            success.setBackground(null);
                            success.setFont(font);
                            success.setBorder(null);
                            JOptionPane.showMessageDialog(gameFrame, success);
                        } else if (color == BLACK && chessBoard.currentPlayer().getColor() == BLACK) {
                            JLabel success = new JLabel("Connected! Black (you) starts the game.");
                            success.setFont(font);
                            JOptionPane.showMessageDialog(gameFrame, success);
                        } else if (color == BLACK && chessBoard.currentPlayer().getColor() == WHITE) {
                            JTextPane success = new JTextPane();
                            success.setContentType("text");
                            success.setText("Connected! You are black.\nClick the center of the board and wait for white to make their turn.");
                            success.setBackground(null);
                            success.setFont(font);
                            success.setBorder(null);
                            JOptionPane.showMessageDialog(gameFrame, success);
                        }
                    } catch (IOException ioException) {
                        JLabel issue = new JLabel("Something went wrong! Exit the program and try again.");
                        issue.setFont(font);
                        JOptionPane.showMessageDialog(gameFrame, issue);
                        ioException.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        server.setObjectInputStream(server.getSocket());
                        server.setObjectOutputStream(server.getSocket());
                        server.getOutputStream().writeObject(gameType);
                        server.getOutputStream().writeObject(chessBoard);
                        server.getOutputStream().writeObject(moveLog);
                        if (color == WHITE) {
                            server.getOutputStream().writeObject("b");
                        } else {
                            server.getOutputStream().writeObject("w");
                        }
                        server.getOutputStream().writeObject(numGames);
                    } catch (IOException ioException) {
                        JLabel issue = new JLabel("Something went wrong! Exit the program and try again.");
                        issue.setFont(font);
                        JOptionPane.showMessageDialog(gameFrame, issue);
                        ioException.printStackTrace();
                    }
                    ready.setEnabled(false);
                    gameOptionsPanel.disableFENButtons();
                });
                gameOptionsPanel.getResetButton().addActionListener(e12 -> {
                    if (chessBoard.currentPlayer().getColor() == color) {
                        chessBoard = resetBoard;
                        moveLog = new MoveLog();
                        gameHistoryPanel.redo(moveLog);
                        takenPiecesPanel.redo(moveLog);
                        boardPanel.drawBoard(chessBoard);
                        try {
                            server.getOutputStream().writeObject(chessBoard);
                            server.getOutputStream().writeObject(moveLog);
                        } catch (IOException ioException) {
                            JLabel issue = new JLabel("Something went wrong! Exit the program and try again.");
                            issue.setFont(font);
                            JOptionPane.showMessageDialog(gameFrame, issue);
                            ioException.printStackTrace();
                        }
                    }
                });
                gameOptionsPanel.addToEastPanel(ready);
            }
        } catch (IOException ioException) {
            JLabel issue = new JLabel("Something went wrong! It seems someone else is running a program on the same network...");
            issue.setFont(font);
            JOptionPane.showMessageDialog(gameFrame, issue);
            ioException.printStackTrace();
        }
    }

    private void loadServerGameFromFile() {
        try {
            JLabel name = new JLabel("Which .txt file would you like to load? The program will search for files in your desktop.");
            name.setFont(font);
            String fileName = "";
            fileName = JOptionPane.showInputDialog(name);
            if (fileName != null) {
                File file = new File(System.getProperty("user.home") + "/Desktop/" + fileName);
                gameType = "Loaded";
                BufferedReader br = new BufferedReader(new FileReader(file));
                chessBoard = FenUtilities.createGameFromFEN(br.readLine());
                resetBoard = chessBoard;
                boardPanel.drawBoard(chessBoard);
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
                gameOptionsPanel.promptLoadedGameServer();
                JButton ready = new JButton("Start");
                ready.setFont(new Font("sans-serif", Font.BOLD, 30));
                ready.addActionListener(e -> {
                    gameType = "FEN";
                    numGames = 1;
                    resetBoard = chessBoard;
                    try {
                        server = new Server();
                    } catch (IOException ioException) {
                        JLabel issue = new JLabel("Something went wrong! It seems someone else is running a program on the same network...");
                        issue.setFont(font);
                        JOptionPane.showMessageDialog(gameFrame, issue);
                        ioException.printStackTrace();
                    }
                    blackStarts = chessBoard.currentPlayer().getColor() == BLACK;
                    if (gameOptionsPanel.getPlayerColor() == Color.WHITE) {
                        color = WHITE;
                        boardDirection = BoardDirection.NORMAL;
                    } else {
                        color = BLACK;
                        boardDirection = BoardDirection.FLIPPED;
                    }
                    boardPanel.drawBoard(chessBoard);
                    try {
                        JTextPane fileGame = new JTextPane();
                        fileGame.setContentType("text");
                        fileGame.setText("     Waiting for other player to connect...\n\nPresent this IP address to the other player:\n                  " + getIP());
                        fileGame.setBackground(null);
                        fileGame.setFont(font);
                        fileGame.setBorder(null);
                        JOptionPane.showMessageDialog(gameFrame, fileGame);
                        server.setSocket(server.getServerSocket());
                        if (color == WHITE && chessBoard.currentPlayer().getColor() == WHITE) {
                            JLabel success = new JLabel("Connected! White (you) starts the game.");
                            success.setFont(font);
                            JOptionPane.showMessageDialog(gameFrame, success);
                        } else if (color == WHITE && chessBoard.currentPlayer().getColor() == BLACK) {
                            JTextPane success = new JTextPane();
                            success.setContentType("text");
                            success.setText("Connected! You are white.\nClick the center of the board and wait for black to make their turn.");
                            success.setBackground(null);
                            success.setFont(font);
                            success.setBorder(null);
                            JOptionPane.showMessageDialog(gameFrame, success);
                        } else if (color == BLACK && chessBoard.currentPlayer().getColor() == BLACK) {
                            JLabel success = new JLabel("Connected! Black (you) starts the game.");
                            success.setFont(font);
                            JOptionPane.showMessageDialog(gameFrame, success);
                        } else if (color == BLACK && chessBoard.currentPlayer().getColor() == WHITE) {
                            JTextPane success = new JTextPane();
                            success.setContentType("text");
                            success.setText("Connected! You are black.\nClick the center of the board and wait for white to make their turn.");
                            success.setBackground(null);
                            success.setFont(font);
                            success.setBorder(null);
                            JOptionPane.showMessageDialog(gameFrame, success);
                        }
                    } catch (Exception ioException) {
                        JLabel issue = new JLabel("Something went wrong! Exit the program and try again.");
                        issue.setFont(font);
                        JOptionPane.showMessageDialog(gameFrame, issue);
                        ioException.printStackTrace();
                    }
                    try {
                        server.setObjectInputStream(server.getSocket());
                        server.setObjectOutputStream(server.getSocket());
                        server.getOutputStream().writeObject(gameType);
                        server.getOutputStream().writeObject(chessBoard);
                        server.getOutputStream().writeObject(moveLog);
                        if (color == WHITE) {
                            server.getOutputStream().writeObject("b");
                        } else {
                            server.getOutputStream().writeObject("w");
                        }
                        server.getOutputStream().writeObject(numGames);
                    } catch (IOException ioException) {
                        JLabel issue = new JLabel("Something went wrong! Exit the program and try again.");
                        issue.setFont(font);
                        JOptionPane.showMessageDialog(gameFrame, issue);
                        ioException.printStackTrace();
                    }
                    ready.setEnabled(false);
                    gameOptionsPanel.disableFENButtons();
                });
                gameOptionsPanel.addToEastPanel(ready);
            }
        } catch (IOException e) {
            JLabel issue = new JLabel("We couldn't find that file! Try again!");
            issue.setFont(font);
            JOptionPane.showMessageDialog(gameFrame, issue);
            e.printStackTrace();
        }
    }

    private void promptClient() {
        if (server != null) {
            if (server.getInputStream() != null && server.getOutputStream() != null && server.getSocket() != null) {
                try {
                    server.clearSome();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                server.getServerSocket().close();
                server = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (client != null) {
            if (client.getInputStream() != null && client.getOutputStream() != null) {
                try {
                    client.clearSome();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                client.getSocket().close();
                client = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        gameDone = false;
        numGames = 0;
        completedGames = 0;
        gameType = "none";
        gameType = "none";
        lines.clear();
        moveLog.clear();
        takenPiecesPanel.redo(moveLog);
        gameHistoryPanel.redo(moveLog);
        boardDirection = BoardDirection.NORMAL;
        JLabel ipPrompt = new JLabel("Input the IP address of the game you want to join:");
        ipPrompt.setFont(font);
        String IP = JOptionPane.showInputDialog(ipPrompt);
        if (IP != null) {
            try {
                client = new Client(IP);
            } catch (IOException ioException) {
                JLabel error = new JLabel("Error. You may have input an invalid IP address or it is currently occupied. Try again");
                error.setFont(font);
                JOptionPane.showMessageDialog(gameFrame, error);
                ioException.printStackTrace();
                return;
            }
            try {
                client.setObjectInputStream(client.getSocket());
                client.setObjectOutputStream(client.getSocket());
                gameType = (String) client.getInputStream().readObject();
                if (gameType.equals("FEN")) {
                    winner = "none";
                    loadClientFENGame();
                } else if (gameType.equals("Standard")) {
                    winner = "none";
                    loadClientStandardGame();
                }
            } catch (IOException | ClassNotFoundException e) {
                JLabel issue = new JLabel("Something went wrong! Exit the program and try again.");
                issue.setFont(font);
                JOptionPane.showMessageDialog(gameFrame, issue);
                e.printStackTrace();
            }
        }
    }

    private void loadClientFENGame() {
        gameOptionsPanel.promptFENGameClient();
        JLabel success = new JLabel();
        try {
            chessBoard = (Board) client.getInputStream().readObject();
            moveLog = (MoveLog) client.getInputStream().readObject();
            resetBoard = chessBoard;
            blackStarts = chessBoard.currentPlayer().getColor() == BLACK;
            String stringColor = (String) client.getInputStream().readObject();
            if (stringColor.equals("w")) {
                color = WHITE;
                if (chessBoard.currentPlayer().getColor() == WHITE) {
                    success = new JLabel("Connected! White (you) starts the game.");
                } else if (chessBoard.currentPlayer().getColor() == BLACK) {
                    success = new JLabel("Connected! You are white. Click the center of the board and wait for black to make their turn.");
                }
                gameOptionsPanel.setColor(stringColor);
                boardDirection = BoardDirection.NORMAL;
            } else if (stringColor.equals("b")) {
                color = BLACK;
                if (chessBoard.currentPlayer().getColor() == BLACK) {
                    success = new JLabel("Connected! Black (you) starts the game.");
                } else if (chessBoard.currentPlayer().getColor() == WHITE) {
                    success = new JLabel("Connected! You are black. Click the center of the board and wait for white to make their turn.");
                }
                gameOptionsPanel.setColor(stringColor);
                boardDirection = BoardDirection.FLIPPED;
            }
            numGames = (int) client.getInputStream().readObject();
            boardPanel.drawBoard(chessBoard);
            takenPiecesPanel.redo(moveLog);
            gameHistoryPanel.redo(moveLog);
            gameOptionsPanel.disableFENButtons();
            success.setFont(font);
            JOptionPane.showMessageDialog(gameFrame, success);
        } catch (IOException | ClassNotFoundException ioException) {
            JLabel issue = new JLabel("Something went wrong! Exit the program and try again.");
            issue.setFont(font);
            JOptionPane.showMessageDialog(gameFrame, issue);
            ioException.printStackTrace();
        }
    }

    private void loadClientStandardGame() {
        JLabel success = new JLabel();
        gameOptionsPanel.promptStandardGameClient();
        try {
            chessBoard = (Board) client.getInputStream().readObject();
            resetBoard = chessBoard;
            blackStarts = false;
            String stringColor = (String) client.getInputStream().readObject();
            if (stringColor.equals("w")) {
                color = WHITE;
                success = new JLabel("Connected! White (you) starts the game.");
                gameOptionsPanel.setColor(stringColor);
                boardDirection = BoardDirection.NORMAL;
            } else if (stringColor.equals("b")) {
                color = BLACK;
                success = new JLabel("Connected! You are black. Click the center of the board and wait for white to make their turn.");
                gameOptionsPanel.setColor(stringColor);
                boardDirection = BoardDirection.FLIPPED;
            }
            gameOptionsPanel.setQueens((boolean) client.getInputStream().readObject());
            gameOptionsPanel.setRooks((boolean) client.getInputStream().readObject());
            gameOptionsPanel.setKnights((boolean) client.getInputStream().readObject());
            gameOptionsPanel.setBishops((boolean) client.getInputStream().readObject());
            numGames = (int) client.getInputStream().readObject();
            boardPanel.drawBoard(chessBoard);
            gameOptionsPanel.disableStandardButtons();
            success.setFont(font);
            JOptionPane.showMessageDialog(gameFrame, success);
        } catch (IOException | ClassNotFoundException ioException) {
            JLabel issue = new JLabel("Something went wrong! Exit the program and try again.");
            issue.setFont(font);
            JOptionPane.showMessageDialog(gameFrame, issue);
            ioException.printStackTrace();
        }
    }

    private void makeMoveFromLine(String line) {
        String[] split = line.split(" ");
        int current = Integer.parseInt(split[2]);
        int destination = Integer.parseInt(split[3]);

        Piece movedPiece = getPiece(split[4], current, false);
        Piece attackedPiece = getPiece(split[1], destination, true);

        Move move = null;

        switch (split[0]) {
            case "MajorAttackMove":
                move = new Move.MajorAttackMove(chessBoard, movedPiece, destination, attackedPiece);
                break;
            case "MajorMove":
                move = new Move.MajorMove(chessBoard, movedPiece, destination);
                break;
            case "PawnMove":
                move = new Move.PawnMove(chessBoard, movedPiece, destination);
                break;
            case "PawnAttackMove":
                move = new Move.PawnAttackMove(chessBoard, movedPiece, destination, attackedPiece);
                break;
            case "PawnEnPassantAttack":
                move = new Move.PawnEnPassantAttackMove(chessBoard, movedPiece, destination, attackedPiece);
                break;
            case "PawnJump":
                move = new Move.PawnJump(chessBoard, movedPiece, destination);
                break;
        }

        final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
        if (transition.getMoveStatus().isDone()) {
            chessBoard = transition.getTransitionBoard();
            moveLog.addMove(move);
        }

        gameHistoryPanel.redo(moveLog);
        takenPiecesPanel.redo(moveLog);
        boardPanel.drawBoard(chessBoard);
    }

    private void writeGameToFile(Board board, MoveLog moveLog) {
        PrintWriter out;
        try {
            JLabel name = new JLabel("Your file will save to your desktop - what would you like to name your file? Make sure it ends in .txt!");
            name.setFont(font);
            String fileName = JOptionPane.showInputDialog(gameFrame, name);
            if (fileName.isEmpty() || !fileName.endsWith(".txt")) {
                JLabel noFileFound = new JLabel("Please give a valid file name ending in .txt! The file did not save.");
                noFileFound.setFont(font);
                JOptionPane.showMessageDialog(gameFrame, noFileFound);
            } else {
                out = new PrintWriter((new FileOutputStream(System.getProperty("user.home") + "/Desktop/" + fileName, false)));
                if (blackStarts) {
                    out.write(FenUtilities.createFENFromGame(board, "b") + "\n");
                } else {
                    out.write(FenUtilities.createFENFromGame(board, "w") + "\n");
                }
                for (int i = 0; i < moveLog.getMoves().size(); i++) {
                    String moveType = null;
                    if (moveLog.getMoves().get(i) instanceof Move.MajorAttackMove) {
                        moveType = "MajorAttackMove";
                    } else if (moveLog.getMoves().get(i) instanceof Move.MajorMove) {
                        moveType = "MajorMove";
                    } else if (moveLog.getMoves().get(i) instanceof Move.PawnMove) {
                        moveType = "PawnMove";
                    } else if (moveLog.getMoves().get(i) instanceof Move.PawnAttackMove) {
                        moveType = "PawnAttackMove";
                    } else if (moveLog.getMoves().get(i) instanceof Move.PawnEnPassantAttackMove) {
                        moveType = "PawnEnPassantAttackMove";
                    } else if (moveLog.getMoves().get(i) instanceof Move.PawnJump) {
                        moveType = "PawnJump";
                    }
                    out.write(moveType + " " + moveLog.getMoves().get(i).getAttackedPiece() + " " + moveLog.getMoves().get(i).getCurrentCoordinate() + " " + moveLog.getMoves().get(i).getDestinationCoordinate() + " " + moveLog.getMoves().get(i).getMovedPiece() + "\n");
                }
                out.close();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            JLabel issue = new JLabel("Something went wrong! We can't find that file. Try again!");
            issue.setFont(font);
            JOptionPane.showMessageDialog(gameFrame, issue);
            fileNotFoundException.printStackTrace();
        }
    }

    private JMenu createEditMenu() {
        final JMenu editMenu = new JMenu("Edit");
        final JMenuItem boardEditorItem = new JMenuItem("Launch Board Editor");
        boardEditorItem.addActionListener(e -> {
            BoardEditor boardEditor = new BoardEditor();
            boardEditor.promptBoardPanel();
        });
        editMenu.add(boardEditorItem);
        return editMenu;
    }


    private JMenu createOptionsMenu() {
        final JMenu optionsMenu = new JMenu("Options");
        final JMenuItem serverButton = new JMenuItem("Start a new game");
        serverButton.addActionListener(e -> promptServer());
        final JMenuItem clientButton = new JMenuItem("Join a game");
        clientButton.addActionListener(e -> promptClient());
        optionsMenu.add(serverButton);
        optionsMenu.add(clientButton);
        return optionsMenu;
    }

    public enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return boardTiles;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
    }

    private class BoardPanel extends JPanel implements Serializable {
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
            for (final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    public static class MoveLog implements Serializable {
        private final List<Move> moves;

        MoveLog() {
             this.moves = new ArrayList<>();
        }

        public List<Move> getMoves() {
             return this.moves;
        }

        public void addMove(final Move move) {
             this.moves.add(move);
        }

        public int size() {
             return this.moves.size();
        }

        public void clear() {
             this.moves.clear();
        }
    }

    private class TilePanel extends JPanel implements Serializable {
        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);

            final int[] q = {0};

            addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        if (isRightMouseButton(e)) {
                            if (gameType.equals("Loaded")) {
                                chessBoard = resetBoard;
                                moveLog.clear();
                                gameHistoryPanel.redo(moveLog);
                                takenPiecesPanel.redo(moveLog);
                                boardPanel.drawBoard(chessBoard);
                                int j = q[0];
                                for (int i = 0; i < (j - 1); i++) {
                                    makeMoveFromLine(lines.get(i));
                                }
                                if (q[0] != 0) {
                                    q[0]--;
                                }
                            } else if (gameType.equals("FEN") || gameType.equals("Standard")){
                                sourceTile = null;
                                destinationTile = null;
                                humanMovedPiece = null;
                            }
                        } else if (isLeftMouseButton(e)) {
                            if (gameType.equals("Loaded")) {
                                if (q[0] < lines.size()) {
                                    makeMoveFromLine(lines.get(q[0]));
                                    q[0]++;
                                }
                            } else if (gameType.equals("FEN") || gameType.equals("Standard")){
                                if (numGames == 1) {
                                    if ((server != null && color == WHITE) || (client != null && color == BLACK)) {
                                        serverWhiteGame(boardPanel, tileId);
                                        completedGames++;
                                        winner = "none";
                                    } else if ((server != null && color == BLACK) || (client != null && color == WHITE)) {
                                        serverBlackGame(boardPanel, tileId);
                                        completedGames++;
                                        winner = "none";
                                    }
                                } else if (numGames == 2) {
                                    if (completedGames == 0) {
                                        if ((server != null && color == WHITE) || (client != null && color == BLACK)) {
                                            serverWhiteGame(boardPanel, tileId);
                                            if (gameDone) {
                                                completedGames++;
                                                winner = "none";
                                                gameDone = false;
                                                chessBoard = resetBoard;
                                                if (color == WHITE) {
                                                    color = BLACK;
                                                    gameOptionsPanel.setColor("b");
                                                    boardDirection = BoardDirection.FLIPPED;
                                                } else if (color == BLACK) {
                                                    color = WHITE;
                                                    gameOptionsPanel.setColor("w");
                                                    boardDirection = BoardDirection.NORMAL;
                                                }
                                                moveLog = new MoveLog();
                                                chessBoard = resetBoard;
                                            }
                                        } else if ((server != null && color == BLACK) || (client != null && color == WHITE)) {
                                            serverBlackGame(boardPanel, tileId);
                                            if (gameDone) {
                                                completedGames++;
                                                winner = "none";
                                                gameDone = false;
                                                chessBoard = resetBoard;
                                                if (color == BLACK) {
                                                    color = WHITE;
                                                    gameOptionsPanel.setColor("w");
                                                    boardDirection = BoardDirection.NORMAL;
                                                } else if (color == WHITE) {
                                                    color = BLACK;
                                                    gameOptionsPanel.setColor("b");
                                                    boardDirection = BoardDirection.FLIPPED;
                                                }
                                                moveLog = new MoveLog();
                                                chessBoard = resetBoard;
                                            }
                                        }
                                    } else if (completedGames == 1) {
                                        if ((server != null && color == BLACK) || (client != null && color == WHITE)) {
                                            gameHistoryPanel.redo(moveLog);
                                            takenPiecesPanel.redo(moveLog);
                                            boardPanel.drawBoard(chessBoard);
                                            serverBlackGame(boardPanel, tileId);
                                        } else if ((server != null && color == WHITE) || (client != null && color == BLACK)) {
                                            gameHistoryPanel.redo(moveLog);
                                            takenPiecesPanel.redo(moveLog);
                                            boardPanel.drawBoard(chessBoard);
                                            serverWhiteGame(boardPanel, tileId);
                                        }
                                    }
                                } else if (numGames == 4) {
                                    if (completedGames == 0) {
                                        if ((server != null && color == WHITE) || (client != null && color == BLACK)) {
                                            serverWhiteGame(boardPanel, tileId);
                                            if (gameDone) {
                                                completedGames++;
                                                winner = "none";
                                                gameDone = false;
                                                chessBoard = resetBoard;
                                                if (color == WHITE) {
                                                    color = BLACK;
                                                    gameOptionsPanel.setColor("b");
                                                    boardDirection = BoardDirection.FLIPPED;
                                                } else if (color == BLACK) {
                                                    color = WHITE;
                                                    gameOptionsPanel.setColor("w");
                                                    boardDirection = BoardDirection.NORMAL;
                                                }
                                                moveLog = new MoveLog();
                                                chessBoard = resetBoard;
                                            }
                                        } else if ((server != null && color == BLACK) || (client != null && color == WHITE)) {
                                            serverBlackGame(boardPanel, tileId);
                                            if (gameDone) {
                                                completedGames++;
                                                winner = "none";
                                                gameDone = false;
                                                chessBoard = resetBoard;
                                                if (color == BLACK) {
                                                    color = WHITE;
                                                    gameOptionsPanel.setColor("w");
                                                    boardDirection = BoardDirection.NORMAL;
                                                } else if (color == WHITE) {
                                                    color = BLACK;
                                                    gameOptionsPanel.setColor("b");
                                                    boardDirection = BoardDirection.FLIPPED;
                                                }
                                                moveLog = new MoveLog();
                                                chessBoard = resetBoard;
                                            }
                                        }
                                    } else if (completedGames == 1 || completedGames == 2) {
                                        if ((server != null && color == BLACK) || (client != null && color == WHITE)) {
                                            gameHistoryPanel.redo(moveLog);
                                            takenPiecesPanel.redo(moveLog);
                                            boardPanel.drawBoard(chessBoard);
                                            serverBlackGame(boardPanel, tileId);
                                            if (gameDone) {
                                                completedGames++;
                                                winner = "none";
                                                gameDone = false;
                                                chessBoard = resetBoard;
                                                if (color == BLACK) {
                                                    color = WHITE;
                                                    gameOptionsPanel.setColor("w");
                                                    boardDirection = BoardDirection.NORMAL;
                                                } else if (color == WHITE) {
                                                    color = BLACK;
                                                    gameOptionsPanel.setColor("b");
                                                    boardDirection = BoardDirection.FLIPPED;
                                                }
                                                moveLog = new MoveLog();
                                                chessBoard = resetBoard;
                                            }
                                        } else if ((server != null && color == WHITE) || (client != null && color == BLACK)) {
                                            gameHistoryPanel.redo(moveLog);
                                            takenPiecesPanel.redo(moveLog);
                                            boardPanel.drawBoard(chessBoard);
                                            serverWhiteGame(boardPanel, tileId);
                                            if (gameDone) {
                                                completedGames++;
                                                winner = "none";
                                                gameDone = false;
                                                chessBoard = resetBoard;
                                                if (color == WHITE) {
                                                    color = BLACK;
                                                    gameOptionsPanel.setColor("b");
                                                    boardDirection = BoardDirection.FLIPPED;
                                                } else if (color == BLACK) {
                                                    color = WHITE;
                                                    gameOptionsPanel.setColor("w");
                                                    boardDirection = BoardDirection.NORMAL;
                                                }
                                                moveLog = new MoveLog();
                                                chessBoard = resetBoard;
                                            }
                                        }
                                    } else if (completedGames == 3) {
                                        if ((server != null && color == BLACK) || (client != null && color == WHITE)) {
                                            gameHistoryPanel.redo( moveLog);
                                            takenPiecesPanel.redo(moveLog);
                                            boardPanel.drawBoard(chessBoard);
                                            serverBlackGame(boardPanel, tileId);
                                        } else if ((server != null && color == WHITE) || (client != null && color == BLACK)) {
                                            gameHistoryPanel.redo(moveLog);
                                            takenPiecesPanel.redo(moveLog);
                                            boardPanel.drawBoard(chessBoard);
                                            serverWhiteGame(boardPanel, tileId);
                                        }
                                    }
                                }
                            }
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
            highlightSquare(board);
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
                    JLabel im = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth()-12, icon.getIconWidth()-12, Image.SCALE_SMOOTH)));
                    im.setPreferredSize(new Dimension(70,70));
                    add(im);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void highlightSquare(final Board board) {
            if (humanMovedPiece != null && humanMovedPiece.getPieceColor() == board.currentPlayer().getColor()) {
                if (humanMovedPiece.getPiecePosition() == this.tileId) {
                    this.removeAll();
                    try {
                        final BufferedImage image = ImageIO.read(getClass().getResource(defaultPieceImagesPath + board.getTile(this.tileId).getPiece().getPieceColor().toString().charAt(0) +
                                board.getTile(this.tileId).getPiece().toString() + "P.png"));
                        ImageIcon icon = new ImageIcon(image);
                        JLabel im = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth()-12, icon.getIconWidth()-12, Image.SCALE_SMOOTH)));
                        im.setPreferredSize(new Dimension(70,70));
                        add(im);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    private void toPlayAgain(int numGames, int completedGames) {
        JLabel playAgain = new JLabel("Ready to play again? Press OK and double click the board!");
        playAgain.setFont(font);
        if (numGames == 2 && (completedGames == 0)) {
            JOptionPane.showMessageDialog(gameFrame, playAgain);
        } else if (numGames == 4 && (completedGames == 0 || completedGames == 1 || completedGames == 2)) {
            JOptionPane.showMessageDialog(gameFrame, playAgain);
        } else {
            if (server != null) {
                try {
                    server.clearAll();
                    server = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (client != null) {
                try {
                    client.clearAll();
                    server = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.gameDone = false;
            this.numGames = 0;
            this.completedGames = 0;
            this.gameType = "none";
            this.gameType = "none";
            this.lines.clear();
        }
    }

    private boolean blackOnEighthRank(Board board) {
        for (int i = 56; i < 64; i++) {
            if (board.getTile(i).isOccupiedTile() && board.getTile(i).toString().equals("p") && winner.equals("none")) {
                return true;
            }
        }
        return false;
    }

    private boolean whiteOnEighthRank(Board board) {
        for (int i = 0; i < 8; i++) {
            if (board.getTile(i).isOccupiedTile() && board.getTile(i).toString().equals("P") && winner.equals("none")) {
                return true;
            }
        }
        return false;
    }

    private boolean whiteHasNoPawns(Board board) {
        for (Piece piece : board.getWhitePieces()) {
            if (piece.toString().equals("P")) {
                return false;
            }
        }
        return true;
    }

    private boolean blackHasNoPawns(Board board) {
        for (Piece piece : board.getBlackPieces()) {
            if (piece.toString().equals("P")) {
                return false;
            }
        }
        return true;
    }

    private void serverWhiteGame(BoardPanel boardPanel, int tileId) {
        if (chessBoard.currentPlayer().getColor() == WHITE && color == WHITE && server != null && !gameDone) {
            if (sourceTile == null) {
                sourceTile = chessBoard.getTile(tileId);
                humanMovedPiece = sourceTile.getPiece();
                if (humanMovedPiece == null) {
                    sourceTile = null;
                }
            } else {
                destinationTile = chessBoard.getTile(tileId);
                final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                if (transition.getMoveStatus().isDone()) {
                    chessBoard = transition.getTransitionBoard();
                    moveLog.addMove(move);
                }
                sourceTile = null;
                destinationTile = null;
                humanMovedPiece = null;
            }
        } else if (chessBoard.currentPlayer().getColor() == WHITE && color == BLACK && client != null && !gameDone) {
            try {
                Board temp = (Board) client.getInputStream().readObject();
                MoveLog placeHolder = (MoveLog) client.getInputStream().readObject();
                if (temp != chessBoard && placeHolder == moveLog) {
                    for (int i = 0; i < 64; i++) {
                        if (!chessBoard.getGameBoard().get(i).toString().equals(temp.getGameBoard().get(i).toString())) {
                            if (chessBoard.getGameBoard().get(i).toString().equals("-") && !temp.getGameBoard().get(i).toString().equals("-")) {
                                if (temp.getGameBoard().get(i).toString().equals("P")) {
                                    if (chessBoard.getGameBoard().get(i + 16).toString().equals("P")) {
                                        Move move = new Move.PawnJump(temp, temp.getTile(i).getPiece(), i);
                                        placeHolder.addMove(move);
                                    } else if (chessBoard.getGameBoard().get(i + 8).toString().equals("P")) {
                                        Move move = new Move.PawnMove(temp, temp.getTile(i).getPiece(), i);
                                        placeHolder.addMove(move);
                                    } else if (chessBoard.getGameBoard().get(i + 8).toString().equals("p") && temp.getGameBoard().get(i + 8).toString().equals("-")) {
                                        int q;
                                        for (q = 0; q < 64; q++) {
                                            if (chessBoard.getGameBoard().get(q).toString().equals("P") && temp.getGameBoard().get(q).toString().equals("-")) {
                                                break;
                                            }
                                        }
                                        Move move = new Move.PawnEnPassantAttackMove(temp, chessBoard.getTile(q).getPiece(), i, chessBoard.getTile(i + 8).getPiece());
                                        placeHolder.addMove(move);
                                    }
                                } else {
                                    Move move = new Move.MajorMove(temp, temp.getTile(i).getPiece(), i);
                                    placeHolder.addMove(move);
                                }
                                break;
                            } else if (!chessBoard.getGameBoard().get(i).toString().equals("-") && !temp.getGameBoard().get(i).toString().equals("-")) {
                                if (temp.getGameBoard().get(i).toString().equals("P")) {
                                    int q;
                                    for (q = 0; q < 64; q++) {
                                        if (!chessBoard.getGameBoard().get(q).toString().equals("-") && temp.getGameBoard().get(q).toString().equals("-")) {
                                            break;
                                        }
                                    }
                                    Move move = new Move.PawnAttackMove(temp, chessBoard.getTile(q).getPiece(), i, chessBoard.getTile(i).getPiece());
                                    placeHolder.addMove(move);
                                } else {
                                    int q;
                                    for (q = 0; q < 64; q++) {
                                        if (!chessBoard.getGameBoard().get(q).toString().equals("-") && temp.getGameBoard().get(q).toString().equals("-")) {
                                            break;
                                        }
                                    }
                                    Move move = new Move.MajorAttackMove(temp, chessBoard.getTile(q).getPiece(), i, chessBoard.getTile(i).getPiece());
                                    placeHolder.addMove(move);
                                }
                                break;
                            }
                        }
                    }
                    chessBoard = temp;
                    moveLog = placeHolder;
                } else {
                    chessBoard = temp;
                    moveLog = placeHolder;
                    if (chessBoard == resetBoard && (moveLog.getMoves().size() == 0)) {
                        if (!blackStarts) {
                            boardPanel.drawBoard(chessBoard);
                            Board temporaryBoard = chessBoard;
                            JLabel resetClicked = new JLabel("White hit reset. The game will restart. Wait for white to make their turn.");
                            resetClicked.setFont(font);
                            JOptionPane.showMessageDialog(gameFrame, resetClicked);
                            chessBoard = (Board) client.getInputStream().readObject();
                            client.getInputStream().readObject();
                            for (int i = 0; i < 64; i++) {
                                if (!temporaryBoard.getGameBoard().get(i).toString().equals(chessBoard.getGameBoard().get(i).toString())) {
                                    if (temporaryBoard.getGameBoard().get(i).toString().equals("-") && !chessBoard.getGameBoard().get(i).toString().equals("-")) {
                                        if (chessBoard.getGameBoard().get(i).toString().equals("P")) {
                                            if (temporaryBoard.getGameBoard().get(i + 16).toString().equals("P")) {
                                                Move move = new Move.PawnJump(chessBoard, chessBoard.getTile(i).getPiece(), i);
                                                moveLog.addMove(move);
                                            } else if (temporaryBoard.getGameBoard().get(i + 8).toString().equals("P")) {
                                                Move move = new Move.PawnMove(chessBoard, chessBoard.getTile(i).getPiece(), i);
                                                moveLog.addMove(move);
                                            } else if (temporaryBoard.getGameBoard().get(i + 8).toString().equals("p") && chessBoard.getGameBoard().get(i + 8).toString().equals("-")) {
                                                int q;
                                                for (q = 0; q < 64; q++) {
                                                    if (temporaryBoard.getGameBoard().get(q).toString().equals("P") && chessBoard.getGameBoard().get(q).toString().equals("-")) {
                                                        break;
                                                    }
                                                }
                                                Move move = new Move.PawnEnPassantAttackMove(chessBoard, temporaryBoard.getTile(q).getPiece(), i, temporaryBoard.getTile(i + 8).getPiece());
                                                moveLog.addMove(move);
                                            }
                                        } else {
                                            Move move = new Move.MajorMove(chessBoard, chessBoard.getTile(i).getPiece(), i);
                                            moveLog.addMove(move);
                                        }
                                        break;
                                    } else if (!temporaryBoard.getGameBoard().get(i).toString().equals("-") && !chessBoard.getGameBoard().get(i).toString().equals("-")) {
                                        if (chessBoard.getGameBoard().get(i).toString().equals("P")) {
                                            int q;
                                            for (q = 0; q < 64; q++) {
                                                if (!temporaryBoard.getGameBoard().get(q).toString().equals("-") && chessBoard.getGameBoard().get(q).toString().equals("-")) {
                                                    break;
                                                }
                                            }
                                            Move move = new Move.PawnAttackMove(chessBoard, temporaryBoard.getTile(q).getPiece(), i, temporaryBoard.getTile(i).getPiece());
                                            moveLog.addMove(move);
                                        } else {
                                            int q;
                                            for (q = 0; q < 64; q++) {
                                                if (!temporaryBoard.getGameBoard().get(q).toString().equals("-") && chessBoard.getGameBoard().get(q).toString().equals("-")) {
                                                    break;
                                                }
                                            }
                                            Move move = new Move.MajorAttackMove(chessBoard, temporaryBoard.getTile(q).getPiece(), i, temporaryBoard.getTile(i).getPiece());
                                            moveLog.addMove(move);
                                        }
                                        break;
                                    }
                                }
                            }
                        } else {
                            JLabel resetClicked = new JLabel("White hit reset. The game will restart. You make the first move.");
                            resetClicked.setFont(font);
                            JOptionPane.showMessageDialog(gameFrame, resetClicked);
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException ioException) {
                JLabel connectionBroken = new JLabel("Connection was broken by the other player!");
                connectionBroken.setFont(font);
                JOptionPane.showMessageDialog(gameFrame, connectionBroken);
                ioException.printStackTrace();
            }
        } else if (chessBoard.currentPlayer().getColor() == BLACK && color == BLACK && client != null && !gameDone) {
            if (sourceTile == null) {
                sourceTile = chessBoard.getTile(tileId);
                humanMovedPiece = sourceTile.getPiece();
                if (humanMovedPiece == null) {
                    sourceTile = null;
                }
            } else {
                destinationTile = chessBoard.getTile(tileId);
                final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                if (transition.getMoveStatus().isDone()) {
                    chessBoard = transition.getTransitionBoard();
                    moveLog.addMove(move);
                }
                sourceTile = null;
                destinationTile = null;
                humanMovedPiece = null;
            }
        } else if (chessBoard.currentPlayer().getColor() == BLACK && color == WHITE && server != null && !gameDone) {
            try {
                chessBoard = (Board) server.getInputStream().readObject();
                moveLog = (MoveLog) server.getInputStream().readObject();
            } catch (IOException | ClassNotFoundException ioException) {
                JLabel connectionBroken = new JLabel("Connection was broken by the other player!");
                connectionBroken.setFont(font);
                JOptionPane.showMessageDialog(gameFrame, connectionBroken);
                ioException.printStackTrace();
            }
        }
        SwingUtilities.invokeLater(() -> {
            gameHistoryPanel.redo(moveLog);
            takenPiecesPanel.redo(moveLog);
            boardPanel.drawBoard(chessBoard);
            JLabel blackEighthRank = new JLabel("<html>Black wins: Black got a pawn to the eighth rank.<html>", SwingConstants.CENTER);
            JLabel whiteNoPawns = new JLabel("<html>Black wins: Black captured all of white's pawns.<html>", SwingConstants.CENTER);
            JLabel draw = new JLabel("<html>Draw! Neither player has any legal moves.<html>", SwingConstants.CENTER);
            JLabel blackNoMoves = new JLabel("<html>White wins: Black has no legal moves.<html>", SwingConstants.CENTER);
            JLabel whiteNoMoves = new JLabel("<html>Black wins: White has no legal moves.<html>", SwingConstants.CENTER);
            JLabel whiteEighthRank = new JLabel("<html>White wins: White got a pawn to the eighth rank.<html>", SwingConstants.CENTER);
            JLabel blackNoPawns = new JLabel("<html>White wins: White captured all of black's pawns.<html>", SwingConstants.CENTER);
            blackEighthRank.setFont(font);
            whiteNoPawns.setFont(font);
            draw.setFont(font);
            blackNoMoves.setFont(font);
            whiteNoMoves.setFont(font);
            whiteEighthRank.setFont(font);
            blackNoPawns.setFont(font);

            if (server != null && color == WHITE && chessBoard.currentPlayer().getColor() == WHITE) {
                if (blackOnEighthRank(chessBoard)) {
                    JOptionPane.showMessageDialog(gameFrame, blackEighthRank);
                    gameDone = true;
                    winner = "Black";
                    toPlayAgain(numGames, completedGames);
                }
                if (whiteHasNoPawns(chessBoard) && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, whiteNoPawns);
                    gameDone = true;
                    winner = "Black";
                    toPlayAgain(numGames, completedGames);
                }
                if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, draw);
                    gameDone = true;
                    winner = "Draw";
                    toPlayAgain(numGames, completedGames);
                } else if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && !chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, whiteNoMoves);
                    gameDone = true;
                    winner = "Black";
                    toPlayAgain(numGames, completedGames);
                } else if (!chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, blackNoMoves);
                    gameDone = true;
                    winner = "White";
                    toPlayAgain(numGames, completedGames);
                }
            } else if (server != null && color == WHITE && chessBoard.currentPlayer().getColor() == BLACK) {
                try {
                    server.getOutputStream().writeObject(chessBoard);
                    server.getOutputStream().writeObject(moveLog);
                    if (whiteOnEighthRank(chessBoard)) {
                        JOptionPane.showMessageDialog(gameFrame, whiteEighthRank);
                        gameDone = true;
                        winner = "White";
                        toPlayAgain(numGames, completedGames);
                    }
                    if (blackHasNoPawns(chessBoard) && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, blackNoPawns);
                        gameDone = true;
                        winner = "White";
                        toPlayAgain(numGames, completedGames);
                    }
                    if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, draw);
                        gameDone = true;
                        winner = "Draw";
                        toPlayAgain(numGames, completedGames);
                    } else if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && !chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, whiteNoMoves);
                        gameDone = true;
                        winner = "Black";
                        toPlayAgain(numGames, completedGames);
                    } else if (!chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, blackNoMoves);
                        gameDone = true;
                        winner = "White";
                        toPlayAgain(numGames, completedGames);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else if (client != null && color == BLACK && chessBoard.currentPlayer().getColor() == WHITE) {
                try {
                    client.getOutputStream().writeObject(chessBoard);
                    client.getOutputStream().writeObject(moveLog);
                    if (blackOnEighthRank(chessBoard)) {
                        JOptionPane.showMessageDialog(gameFrame, blackEighthRank);
                        gameDone = true;
                        winner = "Black";
                        toPlayAgain(numGames, completedGames);
                    }
                    if (whiteHasNoPawns(chessBoard) && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, whiteNoPawns);
                        gameDone = true;
                        winner = "Black";
                        toPlayAgain(numGames, completedGames);
                    }
                    if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, draw);
                        gameDone = true;
                        winner = "Draw";
                        toPlayAgain(numGames, completedGames);
                    } else if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && !chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, whiteNoMoves);
                        gameDone = true;
                        winner = "Black";
                        toPlayAgain(numGames, completedGames);
                    } else if (!chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, blackNoMoves);
                        gameDone = true;
                        winner = "White";
                        toPlayAgain(numGames, completedGames);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else if (client != null && color == BLACK && chessBoard.currentPlayer().getColor() == BLACK) {
                if (whiteOnEighthRank(chessBoard)) {
                    JOptionPane.showMessageDialog(gameFrame, whiteEighthRank);
                    gameDone = true;
                    winner = "White";
                    toPlayAgain(numGames, completedGames);
                }
                if (blackHasNoPawns(chessBoard) && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, blackNoPawns);
                    gameDone = true;
                    winner = "White";
                    toPlayAgain(numGames, completedGames);
                }
                if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, draw);
                    gameDone = true;
                    winner = "Draw";
                    toPlayAgain(numGames, completedGames);
                } else if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && !chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, whiteNoMoves);
                    gameDone = true;
                    winner = "Black";
                    toPlayAgain(numGames, completedGames);
                } else if (!chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, blackNoMoves);
                    gameDone = true;
                    winner = "White";
                    toPlayAgain(numGames, completedGames);
                }
            }
        });
    }

    public void serverBlackGame(BoardPanel boardPanel, int tileId) {
        if (chessBoard.currentPlayer().getColor() == BLACK && color == BLACK && server != null && !gameDone) {
            if (sourceTile == null) {
                sourceTile = chessBoard.getTile(tileId);
                humanMovedPiece = sourceTile.getPiece();
                if (humanMovedPiece == null) {
                    sourceTile = null;
                }
            } else {
                destinationTile = chessBoard.getTile(tileId);
                final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                if (transition.getMoveStatus().isDone()) {
                    chessBoard = transition.getTransitionBoard();
                    moveLog.addMove(move);
                }
                sourceTile = null;
                destinationTile = null;
                humanMovedPiece = null;
            }
        } else if (chessBoard.currentPlayer().getColor() == BLACK && color == WHITE && client != null && !gameDone) {
            try {
                Board temp = (Board) client.getInputStream().readObject();
                MoveLog placeHolder = (MoveLog) client.getInputStream().readObject();
                if (temp != chessBoard && placeHolder == moveLog) {
                    for (int i = 0; i < 64; i++) {
                        if (!chessBoard.getGameBoard().get(i).toString().equals(temp.getGameBoard().get(i).toString())) {
                            if (chessBoard.getGameBoard().get(i).toString().equals("-") && !temp.getGameBoard().get(i).toString().equals("-")) {
                                if (temp.getGameBoard().get(i).toString().equals("p")) {
                                    if (chessBoard.getGameBoard().get(i - 16).toString().equals("p")) {
                                        Move move = new Move.PawnJump(temp, temp.getTile(i).getPiece(), i);
                                        placeHolder.addMove(move);
                                    } else if (chessBoard.getGameBoard().get(i - 8).toString().equals("p")) {
                                        Move move = new Move.PawnMove(temp, temp.getTile(i).getPiece(), i);
                                        placeHolder.addMove(move);
                                    } else if (chessBoard.getGameBoard().get(i - 8).toString().equals("P") && temp.getGameBoard().get(i - 8).toString().equals("-")) {
                                        int q;
                                        for (q = 0; q < 64; q++) {
                                            if (chessBoard.getGameBoard().get(q).toString().equals("p") && temp.getGameBoard().get(q).toString().equals("-")) {
                                                break;
                                            }
                                        }
                                        Move move = new Move.PawnEnPassantAttackMove(temp, chessBoard.getTile(q).getPiece(), i, chessBoard.getTile(i - 8).getPiece());
                                        placeHolder.addMove(move);
                                    }
                                } else {
                                    Move move = new Move.MajorMove(temp, temp.getTile(i).getPiece(), i);
                                    placeHolder.addMove(move);
                                }
                                break;
                            } else if (!chessBoard.getGameBoard().get(i).toString().equals("-") && !temp.getGameBoard().get(i).toString().equals("-")) {
                                if (temp.getGameBoard().get(i).toString().equals("p")) {
                                    int q;
                                    for (q = 0; q < 64; q++) {
                                        if (!chessBoard.getGameBoard().get(q).toString().equals("-") && temp.getGameBoard().get(q).toString().equals("-")) {
                                            break;
                                        }
                                    }
                                    Move move = new Move.PawnAttackMove(temp, chessBoard.getTile(q).getPiece(), i, chessBoard.getTile(i).getPiece());
                                    placeHolder.addMove(move);
                                } else {
                                    int q;
                                    for (q = 0; q < 64; q++) {
                                        if (!chessBoard.getGameBoard().get(q).toString().equals("-") && temp.getGameBoard().get(q).toString().equals("-")) {
                                            break;
                                        }
                                    }
                                    Move move = new Move.MajorAttackMove(temp, chessBoard.getTile(q).getPiece(), i, chessBoard.getTile(i).getPiece());
                                    placeHolder.addMove(move);
                                }
                                break;
                            }
                        }
                    }
                    chessBoard = temp;
                    moveLog = placeHolder;
                } else {
                    chessBoard = temp;
                    moveLog = placeHolder;
                    if (chessBoard == resetBoard && (moveLog.getMoves().size() == 0)) {
                        boardPanel.drawBoard(chessBoard);
                        JLabel resetClicked;
                        if (!blackStarts) {
                            resetClicked = new JLabel("Black hit reset. The game will restart. You make the first move.");
                            resetClicked.setFont(font);
                            JOptionPane.showMessageDialog(gameFrame, resetClicked);
                        } else {
                            resetClicked = new JLabel("Black hit reset. The game will restart. Black makes the first move.");
                            resetClicked.setFont(font);
                            JOptionPane.showMessageDialog(gameFrame, resetClicked);
                            Board temporaryBoard = chessBoard;
                            chessBoard = (Board) client.getInputStream().readObject();
                            moveLog = (MoveLog) client.getInputStream().readObject();
                            for (int i = 0; i < 64; i++) {
                                if (!temporaryBoard.getGameBoard().get(i).toString().equals(chessBoard.getGameBoard().get(i).toString())) {
                                    if (temporaryBoard.getGameBoard().get(i).toString().equals("-") && !chessBoard.getGameBoard().get(i).toString().equals("-")) {
                                        if (chessBoard.getGameBoard().get(i).toString().equals("p")) {
                                            if (temporaryBoard.getGameBoard().get(i - 16).toString().equals("p")) {
                                                Move move = new Move.PawnJump(chessBoard, chessBoard.getTile(i).getPiece(), i);
                                                moveLog.addMove(move);
                                            } else if (temporaryBoard.getGameBoard().get(i - 8).toString().equals("p")) {
                                                Move move = new Move.PawnMove(chessBoard, chessBoard.getTile(i).getPiece(), i);
                                                moveLog.addMove(move);
                                            } else if (temporaryBoard.getGameBoard().get(i - 8).toString().equals("P") && chessBoard.getGameBoard().get(i - 8).toString().equals("-")) {
                                                int q;
                                                for (q = 0; q < 64; q++) {
                                                    if (temporaryBoard.getGameBoard().get(q).toString().equals("p") && chessBoard.getGameBoard().get(q).toString().equals("-")) {
                                                        break;
                                                    }
                                                }
                                                Move move = new Move.PawnEnPassantAttackMove(chessBoard, temporaryBoard.getTile(q).getPiece(), i, temporaryBoard.getTile(i - 8).getPiece());
                                                moveLog.addMove(move);
                                            }
                                        } else {
                                            Move move = new Move.MajorMove(chessBoard, chessBoard.getTile(i).getPiece(), i);
                                            moveLog.addMove(move);
                                        }
                                        break;
                                    } else if (!temporaryBoard.getGameBoard().get(i).toString().equals("-") && !chessBoard.getGameBoard().get(i).toString().equals("-")) {
                                        if (chessBoard.getGameBoard().get(i).toString().equals("p")) {
                                            int q;
                                            for (q = 0; q < 64; q++) {
                                                if (!temporaryBoard.getGameBoard().get(q).toString().equals("-") && chessBoard.getGameBoard().get(q).toString().equals("-")) {
                                                    break;
                                                }
                                            }
                                            Move move = new Move.PawnAttackMove(chessBoard, temporaryBoard.getTile(q).getPiece(), i, temporaryBoard.getTile(i).getPiece());
                                            moveLog.addMove(move);
                                        } else {
                                            int q;
                                            for (q = 0; q < 64; q++) {
                                                if (!temporaryBoard.getGameBoard().get(q).toString().equals("-") && chessBoard.getGameBoard().get(q).toString().equals("-")) {
                                                    break;
                                                }
                                            }
                                            Move move = new Move.MajorAttackMove(chessBoard, temporaryBoard.getTile(q).getPiece(), i, temporaryBoard.getTile(i).getPiece());
                                            moveLog.addMove(move);
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException ioException) {
                JLabel connectionBroken = new JLabel("Connection was broken by the other player!");
                connectionBroken.setFont(font);
                JOptionPane.showMessageDialog(gameFrame, connectionBroken);
                ioException.printStackTrace();
            }
        } else if (chessBoard.currentPlayer().getColor() == WHITE && color == WHITE && client != null && !gameDone) {
            if (sourceTile == null) {
                sourceTile = chessBoard.getTile(tileId);
                humanMovedPiece = sourceTile.getPiece();
                if (humanMovedPiece == null) {
                    sourceTile = null;
                }
            } else {
                destinationTile = chessBoard.getTile(tileId);
                final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                if (transition.getMoveStatus().isDone()) {
                    chessBoard = transition.getTransitionBoard();
                    moveLog.addMove(move);
                }
                sourceTile = null;
                destinationTile = null;
                humanMovedPiece = null;
            }
        } else if (chessBoard.currentPlayer().getColor() == WHITE && color == BLACK && server != null && !gameDone) {
            try {
                chessBoard = (Board) server.getInputStream().readObject();
                moveLog = (MoveLog) server.getInputStream().readObject();
            } catch (IOException | ClassNotFoundException ioException) {
                JLabel connectionBroken = new JLabel("Connection was broken by the other player!");
                connectionBroken.setFont(font);
                JOptionPane.showMessageDialog(gameFrame, connectionBroken);
                ioException.printStackTrace();
            }
        }

        SwingUtilities.invokeLater(() -> {
            gameHistoryPanel.redo(moveLog);
            takenPiecesPanel.redo(moveLog);
            boardPanel.drawBoard(chessBoard);
            JLabel blackEighthRank= new JLabel("<html>Black wins: Black got a pawn to the eighth rank.<html>", SwingConstants.CENTER);
            JLabel whiteNoPawns = new JLabel("<html>Black wins: Black captured all of white's pawns.<html>", SwingConstants.CENTER);
            JLabel draw = new JLabel("<html>Draw! Neither player has any legal moves.<html>", SwingConstants.CENTER);
            JLabel blackNoMoves = new JLabel("<html>White wins: Black has no legal moves.<html>", SwingConstants.CENTER);
            JLabel whiteNoMoves = new JLabel("<html>Black wins: White has no legal moves.<html>", SwingConstants.CENTER);
            JLabel whiteEighthRank = new JLabel("<html>White wins: White got a pawn to the eighth rank.<html>", SwingConstants.CENTER);
            JLabel blackNoPawns = new JLabel("<html>White wins: White captured all of black's pawns.<html>", SwingConstants.CENTER);
            JLabel playAgain = new JLabel("Ready to play again? Press OK and double click the board!");
            blackEighthRank.setFont(font);
            whiteNoPawns.setFont(font);
            draw.setFont(font);
            blackNoMoves.setFont(font);
            whiteNoMoves.setFont(font);
            whiteEighthRank.setFont(font);
            blackNoPawns.setFont(font);
            playAgain.setFont(font);

            if (server != null && color == BLACK && chessBoard.currentPlayer().getColor() == WHITE) {
                try {
                    server.getOutputStream().writeObject(chessBoard);
                    server.getOutputStream().writeObject(moveLog);
                    if (blackOnEighthRank(chessBoard)) {
                        JOptionPane.showMessageDialog(gameFrame, blackEighthRank);
                        gameDone = true;
                        winner = "Black";
                        toPlayAgain(numGames, completedGames);
                    }
                    if (whiteHasNoPawns(chessBoard) && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, whiteNoPawns);
                        gameDone = true;
                        winner = "Black";
                        toPlayAgain(numGames, completedGames);
                    }
                    if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, draw);
                        gameDone = true;
                        winner = "Draw";
                        toPlayAgain(numGames, completedGames);
                    } else if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && !chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, whiteNoMoves);
                        gameDone = true;
                        winner = "Black";
                        toPlayAgain(numGames, completedGames);
                    } else if (!chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, blackNoMoves);
                        gameDone = true;
                        winner = "White";
                        toPlayAgain(numGames, completedGames);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (server != null && color == BLACK && chessBoard.currentPlayer().getColor() == BLACK) {
                if (whiteOnEighthRank(chessBoard)) {
                    JOptionPane.showMessageDialog(gameFrame, whiteEighthRank);
                    gameDone = true;
                    winner = "White";
                    toPlayAgain(numGames, completedGames);
                }
                if (blackHasNoPawns(chessBoard) && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, blackNoPawns);
                    gameDone = true;
                    winner = "White";
                    toPlayAgain(numGames, completedGames);
                }
                if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, draw);
                    gameDone = true;
                    winner = "Draw";
                    toPlayAgain(numGames, completedGames);
                } else if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && !chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, whiteNoMoves);
                    gameDone = true;
                    winner = "Black";
                    toPlayAgain(numGames, completedGames);
                } else if (!chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, blackNoMoves);
                    gameDone = true;
                    winner = "White";
                    toPlayAgain(numGames, completedGames);
                }
            } else if (client != null && color == WHITE && chessBoard.currentPlayer().getColor() == WHITE) {
                if (blackOnEighthRank(chessBoard)) {
                    JOptionPane.showMessageDialog(gameFrame, blackEighthRank);
                    gameDone = true;
                    winner = "Black";
                    toPlayAgain(numGames, completedGames);
                }
                if (whiteHasNoPawns(chessBoard) && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, whiteNoPawns);
                    gameDone = true;
                    winner = "Black";
                    toPlayAgain(numGames, completedGames);
                }
                if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, draw);
                    gameDone = true;
                    winner = "Draw";
                    toPlayAgain(numGames, completedGames);
                } else if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && !chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, whiteNoMoves);
                    gameDone = true;
                    winner = "Black";
                    toPlayAgain(numGames, completedGames);
                } else if (!chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                    JOptionPane.showMessageDialog(gameFrame, blackNoMoves);
                    gameDone = true;
                    winner = "White";
                    toPlayAgain(numGames, completedGames);
                }
            } else if (client != null && color == WHITE && chessBoard.currentPlayer().getColor() == BLACK) {
                try {
                    client.getOutputStream().writeObject(chessBoard);
                    client.getOutputStream().writeObject(moveLog);
                    if (whiteOnEighthRank(chessBoard)) {
                        JOptionPane.showMessageDialog(gameFrame, whiteEighthRank);
                        gameDone = true;
                        winner = "White";
                        toPlayAgain(numGames, completedGames);
                    }
                    if (blackHasNoPawns(chessBoard) && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, blackNoPawns);
                        gameDone = true;
                        winner = "White";
                        toPlayAgain(numGames, completedGames);
                    }
                    if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, draw);
                        gameDone = true;
                        winner = "Draw";
                        toPlayAgain(numGames, completedGames);
                    } else if (chessBoard.whitePlayer().getLegalMoves().isEmpty() && !chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, whiteNoMoves);
                        gameDone = true;
                        winner = "Black";
                        toPlayAgain(numGames, completedGames);
                    } else if (!chessBoard.whitePlayer().getLegalMoves().isEmpty() && chessBoard.blackPlayer().getLegalMoves().isEmpty() && winner.equals("none")) {
                        JOptionPane.showMessageDialog(gameFrame, blackNoMoves);
                        gameDone = true;
                        winner = "White";
                        toPlayAgain(numGames, completedGames);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}