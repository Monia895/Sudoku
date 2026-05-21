package sudoku.logic;

import sudoku.model.Board;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {

    private static final int[][] PUZZLE = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    @Test
    void solve_rozwiazujePoprawnaPlansze() {
        Board board = new Board();
        board.loadPuzzle(PUZZLE);

        Solver solver = new Solver(board);
        assertTrue(solver.solve());
    }

    @Test
    void solve_wypelniawszystkiePola() {
        Board board = new Board();
        board.loadPuzzle(PUZZLE);

        new Solver(board).solve();

        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                assertNotEquals(0, board.getValue(row, col));
    }

    @Test
    void solve_nieNiszczyOryginaluPrzyCopy() {
        Board board = new Board();
        board.loadPuzzle(PUZZLE);

        Board copy = board.copy();
        new Solver(copy).solve();
        
        assertEquals(0, board.getValue(0, 2));
    }

    @Test
    void getHint_zwracaPoprawnaPozycjeIWartosc() {
        Board board = new Board();
        board.loadPuzzle(PUZZLE);

        Solver solver = new Solver(board);
        int[] hint = solver.getHint(board);

        assertNotNull(hint);
        assertEquals(3, hint.length);

        int row = hint[0];
        int col = hint[1];
        int value = hint[2];

        assertTrue(row >= 0 && row < 9);
        assertTrue(col >= 0 && col < 9);

        assertTrue(value >= 1 && value <= 9);

        assertEquals(0, board.getValue(row, col));

        assertTrue(board.validate(row, col, value));
    }
}
