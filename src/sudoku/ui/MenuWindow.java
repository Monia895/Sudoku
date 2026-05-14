package sudoku.ui;

import javax.swing.*;
import java.awt.*;

public class MenuWindow extends JFrame {

    public MenuWindow() {
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JLabel title = new JLabel("SUDOKU");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Wybierz wersję gry");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton classicButton = new JButton("Wersja klasyczna");
        classicButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        classicButton.setMaximumSize(new Dimension(200, 40));
        classicButton.setFont(new Font("Arial", Font.PLAIN, 14));
        classicButton.addActionListener(e -> {
            new MainWindow();
            dispose();
        });

        JButton extendedButton = new JButton("Wersja rozszerzona");
        extendedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        extendedButton.setMaximumSize(new Dimension(200, 40));
        extendedButton.setFont(new Font("Arial", Font.PLAIN, 14));
        extendedButton.addActionListener(e -> {
            new MainWindowV2();
            dispose();
        });

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(subtitle);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(classicButton);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(extendedButton);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}