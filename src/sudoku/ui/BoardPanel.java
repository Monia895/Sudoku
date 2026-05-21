package sudoku.ui;

import sudoku.model.Board;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class BoardPanel extends JPanel {

    private Board board;

    private JTextField[][] fields = new JTextField[9][9];

    private static final Color[] DIGIT_COLORS = {
            null,
            new Color(255, 170, 170),    // 1 — czerwony
            new Color(255, 210, 130),    // 2 — pomarańczowy
            new Color(255, 255, 140),    // 3 — żółty
            new Color(160, 230, 160),    // 4 — zielony
            new Color(140, 210, 255),    // 5 — niebieski
            new Color(190, 170, 255),    // 6 — fioletowy
            new Color(255, 170, 220),    // 7 — różowy
            new Color(150, 230, 210),    // 8 — turkusowy
            new Color(255, 195, 150),    // 9 — brzoskwiniowy
    };

    private int highlightedDigit = 0;

    public BoardPanel() {
        setLayout(new GridLayout(9, 9));
        setPreferredSize(new Dimension(450, 450));

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField field = new JTextField();

                field.setHorizontalAlignment(JTextField.CENTER);
                field.setFont(new Font("Arial", Font.PLAIN, 20));
                field.setDocument(new LimitDocument());
                field.setBorder(createCellBorder(row, col));

                fields[row][col] = field;
                add(field);
            }
        }
    }

    private MatteBorder createCellBorder(int row, int col) {
        int top    = (row % 3 == 0) ? 3 : 1;
        int left   = (col % 3 == 0) ? 3 : 1;
        int bottom = (row == 8)     ? 3 : 1;
        int right  = (col == 8)     ? 3 : 1;

        return new MatteBorder(top, left, bottom, right, Color.BLACK);
    }

    public void loadPuzzle(Board board) {
        this.board = board;
        highlightedDigit = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField field = fields[row][col];
                field.setDocument(new LimitDocument());

                int value = board.getValue(row, col);

                if (value > 0) {
                    field.setText(String.valueOf(value));
                    field.setEditable(false);
                    field.setBackground(new Color(235, 225, 210));
                    field.setCursor(Cursor.getDefaultCursor());

                    int cellValue = value;
                    field.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                            highlightDigit(cellValue);
                        }
                    });

                } else {
                    field.setText("");
                    field.setEditable(true);
                    field.setBackground(Color.WHITE);
                    addValidationListener(field, row, col, board);

                    final int finalRow = row;
                    final int finalCol = col;
                    field.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                            int val = board.getValue(finalRow, finalCol);
                            if (val != 0) highlightDigit(val);
                        }
                    });

                }
            }
        }
    }

    private void addValidationListener(JTextField field, int row, int col, Board board) {
        field.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                handleChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                field.setBackground(Color.WHITE);
                board.setValue(row, col, 0);
                board.getCell(row, col).setHasError(false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}

            private void handleChange() {
                String text = field.getText();
                if (text.isEmpty()) return;

                int value = Integer.parseInt(text);
                board.setValue(row, col, value);

                boolean valid = board.validate(row, col, value);
                board.getCell(row, col).setHasError(!valid);

                field.setBackground(valid ? Color.WHITE : new Color(255, 200, 200));

                if (!valid && gameEventListener != null) {
                    gameEventListener.onError();
                }

                if (valid && gameEventListener != null) {
                    gameEventListener.onCorrectInput();
                }

                if (highlightedDigit != 0) {
                    refreshHighlight();
                }
            }
        });
    }

    public JTextField getField(int row, int col) {
        return fields[row][col];
    }

    @Override
    public void setEnabled(boolean enabled) {  // <-- tutaj
        super.setEnabled(enabled);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!board.isFixed(row, col)) {
                    fields[row][col].setEditable(enabled);
                }
            }
        }
    }

    private GameEventListener gameEventListener;

    public void setGameEventListener(GameEventListener listener) {
        this.gameEventListener = listener;
    }

    private void highlightDigit(int digit) {
        if (highlightedDigit == digit) {
            highlightedDigit = 0;
        } else {
            highlightedDigit = digit;
        }
        refreshHighlight();
    }

    private void refreshHighlight() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField field = fields[row][col];
                int value = board.getValue(row, col);

                if (board.isFixed(row, col)) {
                    if (highlightedDigit != 0 && value == highlightedDigit) {
                        field.setBackground(DIGIT_COLORS[highlightedDigit]);
                    } else {
                        field.setBackground(new Color(235, 225, 210));
                    }
                } else {
                    if (highlightedDigit != 0 && value == highlightedDigit) {
                        field.setBackground(DIGIT_COLORS[highlightedDigit]);
                    } else if (board.getCell(row, col).hasError()) {
                        field.setBackground(new Color(255, 200, 200));
                    } else {
                        field.setBackground(Color.WHITE);
                    }
                }
            }
        }
    }
}