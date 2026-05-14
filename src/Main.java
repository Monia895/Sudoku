import sudoku.ui.MainWindow;
import javax.swing.SwingUtilities;
import sudoku.ui.MenuWindow;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuWindow::new);
    }

}