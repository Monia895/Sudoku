package sudoku.ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private BoardPanel boardPanel;

    public MainWindow() {
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        boardPanel = new BoardPanel();

        // panel główny z marginesem wokół planszy
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        add(mainPanel);

        pack();                        // dopasuj rozmiar okna do zawartości
        setLocationRelativeTo(null);   // wyśrodkuj na ekranie
        setVisible(true);
    }
}
