package sudoku.ui;

import sudoku.game.BestTimes;
import sudoku.game.GameState;
import sudoku.model.Board;
import sudoku.logic.Difficulty;
import sudoku.logic.Generator;
import sudoku.game.GameSaver;
import sudoku.logic.Solver;

import javax.swing.*;
import java.awt.*;

public class MainWindowV2 extends JFrame {

    private Board originalBoard;

    private Difficulty currentDifficulty = Difficulty.MEDIUM;
    private Generator generator = new Generator();
    private JLabel difficultyLabel;

    private BoardPanel boardPanel;
    private Board board;
    private GameState gameState;

    private JLabel hintLabel;
    private JButton hintButton;

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
        originalBoard = board.copy();

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
                    boardPanel.playWinAnimation();


                    javax.swing.Timer delay = new javax.swing.Timer(1200, e -> {
                        new WinDialog(
                            MainWindowV2.this,
                            gameState.getElapsedSeconds(),
                            gameState.getErrorCount(),
                            currentDifficulty
                        );
                    });
                    delay.setRepeats(false);
                    delay.start();
                }
            }
        });

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setIconImage(loadIcon());
        setVisible(true);

        startTimer();
    }

    private JPanel createSidePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(140, 450));
        panel.setBackground(new Color(255, 235, 242));

        // timer
        timerLabel = new JLabel("00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        timerLabel.setForeground(new Color(180, 60, 100));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // błędy
        errorLabel = new JLabel("Błędy: 0/3");
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // poziom
        difficultyLabel = new JLabel("Poziom: " + currentDifficulty);
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        difficultyLabel.setForeground(Color.GRAY);
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<Difficulty> difficultyBox = new JComboBox<>(Difficulty.values());
        difficultyBox.setSelectedItem(currentDifficulty);
        difficultyBox.setMaximumSize(new Dimension(120, 28));
        difficultyBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyBox.addActionListener(e ->
                currentDifficulty = (Difficulty) difficultyBox.getSelectedItem()
        );

        // przyciski gry
        JButton newGameButton = new JButton("Nowa gra");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setMaximumSize(new Dimension(120, 30));
        newGameButton.addActionListener(e -> newGame());
        newGameButton.setBackground(new Color(220, 80, 120));
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setOpaque(true);
        newGameButton.setBorderPainted(false);

        JButton resetButton = new JButton("Resetuj");
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.setMaximumSize(new Dimension(120, 30));
        resetButton.addActionListener(e -> resetGame());
        resetButton.setBackground(new Color(255, 210, 225));
        resetButton.setForeground(new Color(180, 60, 100));
        resetButton.setOpaque(true);
        resetButton.setBorderPainted(false);

        // podpowiedzi
        hintLabel = new JLabel("Podpowiedzi: " + gameState.getHintsLeft());
        hintLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        hintLabel.setForeground(Color.GRAY);
        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        hintButton = new JButton("Podpowiedź");
        hintButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hintButton.setMaximumSize(new Dimension(120, 30));
        hintButton.addActionListener(e -> useHint(hintLabel, hintButton));

        hintButton.setBackground(new Color(255, 210, 225));
        hintButton.setForeground(new Color(180, 60, 100));
        hintButton.setOpaque(true);
        hintButton.setBorderPainted(false);

        // zapis
        JButton saveButton = new JButton("Zapisz grę");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setMaximumSize(new Dimension(120, 30));
        saveButton.addActionListener(e -> saveGame());
        saveButton.setBackground(new Color(255, 210, 225));
        saveButton.setForeground(new Color(180, 60, 100));
        saveButton.setOpaque(true);
        saveButton.setBorderPainted(false);

        JButton loadButton = new JButton("Wczytaj grę");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setMaximumSize(new Dimension(120, 30));
        loadButton.setEnabled(GameSaver.saveExists());
        loadButton.addActionListener(e -> loadGame(loadButton));
        loadButton.setBackground(new Color(255, 210, 225));
        loadButton.setForeground(new Color(180, 60, 100));
        loadButton.setOpaque(true);
        loadButton.setBorderPainted(false);

        // menu
        JButton menuButton = new JButton("Menu");
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setMaximumSize(new Dimension(120, 30));
        menuButton.addActionListener(e -> { new MenuWindow(); dispose(); });
        menuButton.setBackground(new Color(255, 210, 225));
        menuButton.setForeground(new Color(180, 60, 100));
        menuButton.setOpaque(true);
        menuButton.setBorderPainted(false);

        JButton bestTimesButton = new JButton("Rekordy");
        bestTimesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bestTimesButton.setMaximumSize(new Dimension(120, 30));
        bestTimesButton.setBackground(new Color(255, 210, 225));
        bestTimesButton.setForeground(new Color(180, 60, 100));
        bestTimesButton.setOpaque(true);
        bestTimesButton.setBorderPainted(false);
        bestTimesButton.addActionListener(e -> showBestTimes());

        // składanie panelu
        panel.add(Box.createVerticalGlue());
        panel.add(timerLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 4)));
        panel.add(errorLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(difficultyLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 4)));
        panel.add(difficultyBox);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(newGameButton);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(resetButton);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(hintLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 4)));
        panel.add(hintButton);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(saveButton);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(loadButton);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(menuButton);
        panel.add(Box.createVerticalGlue());

        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(bestTimesButton);

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

        board = originalBoard.copy();
        boardPanel.setEnabled(true);
        boardPanel.loadPuzzle(board);
        difficultyLabel.setText("Poziom: " + currentDifficulty);
        hintLabel.setText("Podpowiedzi: 3");
        hintButton.setEnabled(true);

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
        originalBoard = board.copy();
        boardPanel.setEnabled(true);
        boardPanel.loadPuzzle(board);
        errorLabel.setForeground(Color.BLACK);
        difficultyLabel.setText("Poziom: " + currentDifficulty);
        hintLabel.setText("Podpowiedzi: 3");
        hintButton.setEnabled(true);

        startTimer();
    }

    private void saveGame() {
        GameSaver.save(board, gameState);
        JOptionPane.showMessageDialog(
                this,
                "Gra została zapisana.",
                "Zapis",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void loadGame(JButton loadButton) {
        swingTimer.stop();

        board = new Board();
        gameState = new GameState();

        boolean success = GameSaver.load(board, gameState);

        if (success) {
            boardPanel.setEnabled(true);
            boardPanel.loadPuzzle(board);
            timerLabel.setText(gameState.getFormattedTime());
            errorLabel.setText("Błędy: " + gameState.getErrorCount() + "/3");
            difficultyLabel.setText("Poziom: wczytana gra");
            startTimer();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Nie znaleziono zapisanej gry.",
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE
            );
            startTimer();
        }
    }

    private void useHint(JLabel hintLabel, JButton hintButton) {
        if (!gameState.useHint()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Wykorzystałeś wszystkie podpowiedzi.",
                    "Brak podpowiedzi",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        Solver solver = new Solver(board);
        int[] hint = solver.getHint(board);

        if (hint == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nie można udzielić podpowiedzi.",
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE
            );
            gameState.useHint(); // cofnij zużycie
            return;
        }

        int row = hint[0];
        int col = hint[1];
        int value = hint[2];

        board.setValue(row, col, value);
        board.getCell(row, col).setFixed(true);

        JTextField field = boardPanel.getField(row, col);
        field.setText(String.valueOf(value));
        field.setEditable(false);
        field.setBackground(new Color(200, 230, 200)); // zielone tło dla podpowiedzi

        hintLabel.setText("Podpowiedzi: " + gameState.getHintsLeft());
        if (gameState.getHintsLeft() == 0) hintButton.setEnabled(false);
    }

    private void showBestTimes() {
        StringBuilder sb = new StringBuilder();
        sb.append("Najlepsze czasy:\n\n");

        for (Difficulty diff : Difficulty.values()) {
            Integer best = BestTimes.getBest(diff);
            sb.append(diff).append(": ");
            sb.append(best != null ? BestTimes.format(best) : "brak rekordu");
            sb.append("\n");
        }

        JOptionPane.showMessageDialog(
                this,
                sb.toString(),
                "Rekordy",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private Image loadIcon() {
        try {
            return new ImageIcon(
                    getClass().getClassLoader().getResource("icon.png")
            ).getImage();
        } catch (Exception e) {
            return null;
        }
    }
}
