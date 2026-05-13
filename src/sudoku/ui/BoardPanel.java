package sudoku.ui;

import sudoku.model.Board;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class BoardPanel extends JPanel {

    private JTextField[][] fields = new JTextField[9][9];

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

    public JTextField getField(int row, int col) {
        return fields[row][col];
    }

    public void loadPuzzle(Board board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField field = fields[row][col];
                int value = board.getValue(row, col);

                if (value > 0) {
                    field.setText(String.valueOf(value));
                    field.setEditable(false);
                    field.setBackground(new Color(220, 220, 220));
                } else {
                    field.setText("");
                    field.setEditable(true);
                    field.setBackground(Color.WHITE);
                    addValidationListener(field, row, col, board);
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
            }
        });
    }

    private GameEventListener gameEventListener;

    public void setGameEventListener(GameEventListener listener) {
        this.gameEventListener = listener;
    }
}