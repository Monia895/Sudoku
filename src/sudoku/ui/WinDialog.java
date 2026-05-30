package sudoku.ui;

import sudoku.game.BestTimes;
import sudoku.logic.Difficulty;

import javax.swing.*;
import java.awt.*;

public class WinDialog extends JDialog {

    public WinDialog(JFrame parent, int seconds, int errors, Difficulty difficulty) {
        super(parent, "Wygrana!", true);
        setResizable(false);

        BestTimes.save(difficulty, seconds);
        Integer best = BestTimes.getBest(difficulty);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(new Color(255, 235, 242));

        JLabel trophy = new JLabel("🏆");
        trophy.setFont(new Font("Arial", Font.PLAIN, 40));
        trophy.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Gratulacje!");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(180, 60, 100));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel timeLabel = new JLabel("Czas: " + BestTimes.format(seconds));
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel errorsLabel = new JLabel("Błędy: " + errors + "/3");
        errorsLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        errorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel diffLabel = new JLabel("Poziom: " + difficulty);
        diffLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        diffLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bestLabel;
        if (best != null && best == seconds) {
            bestLabel = new JLabel("Nowy rekord! 🎉");
            bestLabel.setForeground(new Color(180, 60, 100));
        } else if (best != null) {
            bestLabel = new JLabel("Najlepszy czas: " + BestTimes.format(best));
            bestLabel.setForeground(Color.GRAY);
        } else {
            bestLabel = new JLabel("");
        }
        bestLabel.setFont(new Font("Arial", Font.BOLD, 13));
        bestLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton closeButton = new JButton("OK");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.setMaximumSize(new Dimension(100, 30));
        closeButton.setBackground(new Color(220, 80, 120));
        closeButton.setForeground(Color.WHITE);
        closeButton.setOpaque(true);
        closeButton.setBorderPainted(false);
        closeButton.addActionListener(e -> dispose());

        panel.add(trophy);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 16)));
        panel.add(timeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(errorsLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(diffLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(bestLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 16)));
        panel.add(closeButton);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
