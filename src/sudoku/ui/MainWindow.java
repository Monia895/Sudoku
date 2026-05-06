package sudoku.ui;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Sudoku");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);


        setVisible(true);
    }
}
