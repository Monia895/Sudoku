package sudoku.ui;

import sudoku.game.GameState;
import sudoku.model.Board;

import sudoku.logic.Difficulty;
import sudoku.logic.Generator;

import javax.swing.*;
import java.awt.*;

public class MainWindowV2 extends JFrame {

    private Difficulty currentDifficulty = Difficulty.MEDIUM;
    private Generator generator = new Generator();
    private JLabel difficultyLabel;

    private BoardPanel boardPanel;
    private Board board;
    private GameState gameState;

    private JLabel timerLabel;
    private JLabel errorLabel;
    private javax.swing.Timer swingTimer;

    public MainWindowV2() {
        setTitle("Sudoku - Wersja rozszerzona");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        board = new Board();
        gameState = new GameState();

        board = generateNewBoard();

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
                            MainWindowV2.this,
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
                            MainWindowV2.this,
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

        difficultyLabel = new JLabel("Poziom: " + currentDifficulty);
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        difficultyLabel.setForeground(Color.GRAY);
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        errorLabel = new JLabel("Błędy: 0/3");
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<Difficulty> difficultyBox = new JComboBox<>(Difficulty.values());
        difficultyBox.setSelectedItem(currentDifficulty);
        difficultyBox.setMaximumSize(new Dimension(120, 30));
        difficultyBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyBox.addActionListener(e -> {
            currentDifficulty = (Difficulty) difficultyBox.getSelectedItem();
        });



        JButton newGameButton = new JButton("Nowa gra");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setMaximumSize(new Dimension(120, 35));
        newGameButton.addActionListener(e -> newGame());

        JButton resetButton = new JButton("Resetuj");
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.setMaximumSize(new Dimension(120, 35));
        resetButton.addActionListener(e -> resetGame());

        JButton menuButton = new JButton("Menu");
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setMaximumSize(new Dimension(120, 35));
        menuButton.addActionListener(e -> {
            new MenuWindow();
            dispose();
        });

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(menuButton);

        panel.add(Box.createVerticalGlue());
        panel.add(timerLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(errorLabel);

        panel.add(timerLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(difficultyLabel);

        panel.add(difficultyBox);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(newGameButton);

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

        board = generateNewBoard();
        boardPanel.setEnabled(true);
        boardPanel.loadPuzzle(board);
        difficultyLabel.setText("Poziom: " + currentDifficulty);

        startTimer();
    }

    private Board generateNewBoard() {
        return generator.generate(currentDifficulty);
    }

    private void newGame() {
        swingTimer.stop();
        gameState.reset();
        timerLabel.setText("00:00");
        errorLabel.setText("Błędy: 0/3");
        errorLabel.setForeground(Color.BLACK);
        setTitle("Sudoku");

        board = generateNewBoard();
        boardPanel.setEnabled(true);
        boardPanel.loadPuzzle(board);
        errorLabel.setForeground(Color.BLACK);
        difficultyLabel.setText("Poziom: " + currentDifficulty);

        startTimer();
    }
}
