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
        panel.setBackground(new Color(255, 240, 245));

        JLabel title = new JLabel("SUDOKU");
        title.setFont(new Font("Arial", Font.BOLD, 42));
        title.setForeground(new Color(180, 60, 100));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Wybierz wersję gry");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(200, 10));
        separator.setForeground(new Color(200, 200, 200));

        JButton classicButton = new JButton("Wersja klasyczna");
        classicButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        classicButton.setMaximumSize(new Dimension(200, 40));
        classicButton.setFont(new Font("Arial", Font.PLAIN, 14));
        classicButton.setBackground(new Color(220, 80, 120));
        classicButton.setForeground(Color.WHITE);
        classicButton.setOpaque(true);
        classicButton.setBorderPainted(false);
        classicButton.addActionListener(e -> { new MainWindow(); dispose(); });

        JButton extendedButton = new JButton("Wersja rozszerzona");
        extendedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        extendedButton.setMaximumSize(new Dimension(200, 40));
        extendedButton.setFont(new Font("Arial", Font.PLAIN, 14));
        extendedButton.setBackground(new Color(255, 210, 225));
        extendedButton.setForeground(new Color(180, 60, 100));
        extendedButton.setOpaque(true);
        extendedButton.setBorderPainted(false);
        extendedButton.addActionListener(e -> { new MainWindowV2(); dispose(); });

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(subtitle);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(separator);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(classicButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(extendedButton);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setIconImage(loadIcon());
        setVisible(true);
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