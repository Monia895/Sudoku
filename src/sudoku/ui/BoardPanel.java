package sudoku.ui;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

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
}