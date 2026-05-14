package sudoku.ui;

import sudoku.game.GameState;
import sudoku.model.Board;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private BoardPanel boardPanel;
    private Board board;
    private GameState gameState;

    private JLabel timerLabel;
    private JLabel errorLabel;
    private javax.swing.Timer swingTimer;

    public MainWindow() {
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        board = new Board();
        gameState = new GameState();

        board.loadPuzzle(getNextPuzzle());

        boardPanel = new BoardPanel();
        boardPanel.loadPuzzle(board);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(createSidePanel(), BorderLayout.EAST);

        boardPanel.setGameEventListener(new GameEventListener() {
            @Override
            public void onError() {
                gameState.incrementErrors();
                errorLabel.setText("Błędy: " + gameState.getErrorCount() + "/3");

                if (gameState.getErrorCount() >= 3) {
                    swingTimer.stop();
                    boardPanel.setEnabled(false);
                    errorLabel.setForeground(Color.RED);
                    setTitle("Sudoku — Koniec gry");
                    JOptionPane.showMessageDialog(
                            MainWindow.this,
                            "Wykorzystałeś wszystkie próby.",
                            "Koniec gry",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

            @Override
            public void onCorrectInput() {
                if (board.isSolved()) {
                    swingTimer.stop();
                    setTitle("Sudoku — Wygrana!");
                    JOptionPane.showMessageDialog(
                            MainWindow.this,
                            "Gratulacje! Czas: " + gameState.getFormattedTime(),
                            "Wygrana!",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        startTimer();
    }

    private JPanel createSidePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        panel.setPreferredSize(new Dimension(130, 450));

        timerLabel = new JLabel("00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        errorLabel = new JLabel("Błędy: 0/3");
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton newGameButton = new JButton("Nowa gra");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setMaximumSize(new Dimension(120, 35));
        newGameButton.addActionListener(e -> newGame());

        JButton resetButton = new JButton("Resetuj");
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.setMaximumSize(new Dimension(120, 35));
        resetButton.addActionListener(e -> resetGame());

        panel.add(Box.createVerticalGlue());
        panel.add(timerLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(errorLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(newGameButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(resetButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private void startTimer() {
        gameState.setRunning(true);
        swingTimer = new javax.swing.Timer(1000, e -> {
            gameState.incrementTime();
            timerLabel.setText(gameState.getFormattedTime());
        });
        swingTimer.start();
    }

    private void resetGame() {
        swingTimer.stop();
        gameState.reset();
        timerLabel.setText("00:00");
        errorLabel.setText("Błędy: 0/3");
        errorLabel.setForeground(Color.BLACK);
        setTitle("Sudoku");

        board = new Board();
        board.loadPuzzle(getNextPuzzle());
        boardPanel.setEnabled(true);
        boardPanel.loadPuzzle(board);

        startTimer();
    }

    private static final int[][][] PUZZLES = {
            {
                    {5,3,0,0,7,0,0,0,0},
                    {6,0,0,1,9,5,0,0,0},
                    {0,9,8,0,0,0,0,6,0},
                    {8,0,0,0,6,0,0,0,3},
                    {4,0,0,8,0,3,0,0,1},
                    {7,0,0,0,2,0,0,0,6},
                    {0,6,0,0,0,0,2,8,0},
                    {0,0,0,4,1,9,0,0,5},
                    {0,0,0,0,8,0,0,7,9}
            },
            {
                    {0,0,0,2,6,0,7,0,1},
                    {6,8,0,0,7,0,0,9,0},
                    {1,9,0,0,0,4,5,0,0},
                    {8,2,0,1,0,0,0,4,0},
                    {0,0,4,6,0,2,9,0,0},
                    {0,5,0,0,0,3,0,2,8},
                    {0,0,9,3,0,0,0,7,4},
                    {0,4,0,0,5,0,0,3,6},
                    {7,0,3,0,1,8,0,0,0}
            },
            {
                    {0,0,0,0,0,0,0,1,2},
                    {0,0,0,0,3,5,0,0,0},
                    {0,0,0,6,0,0,0,7,0},
                    {7,0,0,0,0,0,3,0,0},
                    {0,0,0,4,0,0,8,0,0},
                    {1,0,0,0,0,0,0,0,0},
                    {0,0,0,1,2,0,0,0,0},
                    {0,8,0,0,0,0,0,4,0},
                    {0,5,0,0,0,0,6,0,0}
            }
    };

    private int currentPuzzleIndex = 0;

    private int[][] getNextPuzzle() {
        currentPuzzleIndex = (currentPuzzleIndex + 1) % PUZZLES.length;
        return PUZZLES[currentPuzzleIndex];
    }

    private void newGame() {
        swingTimer.stop();
        gameState.reset();
        timerLabel.setText("00:00");
        errorLabel.setText("Błędy: 0/3");
        errorLabel.setForeground(Color.BLACK);
        setTitle("Sudoku");

        board = new Board();
        board.loadPuzzle(getNextPuzzle());
        boardPanel.setEnabled(true);
        boardPanel.loadPuzzle(board);
        errorLabel.setForeground(Color.BLACK);

        startTimer();
    }
}
