package sudoku.ui;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    public BoardPanel() {
        setLayout(new GridLayout(9, 9));
        setPreferredSize(new Dimension(450, 450));

    }
}
