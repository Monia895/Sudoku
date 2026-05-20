import sudoku.ui.MainWindow;
import javax.swing.SwingUtilities;
import sudoku.ui.MenuWindow;
import sudoku.model.Board;
import sudoku.logic.Solver;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuWindow::new);
    }

}