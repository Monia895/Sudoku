import sudoku.ui.MainWindow;
import javax.swing.SwingUtilities;
import sudoku.ui.MenuWindow;
import sudoku.model.Board;
import sudoku.logic.Solver;
import sudoku.logic.Generator;
import sudoku.logic.Difficulty;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(MenuWindow::new);
    }

}