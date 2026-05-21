package sudoku.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

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

    @BeforeEach
    void setUp() {
        board = new Board();
        board.loadPuzzle(PUZZLE);
    }

    @Test
    void getValue_zwracaPoprawnaCyfre() {
        assertEquals(5, board.getValue(0, 0));
        assertEquals(3, board.getValue(0, 1));
        assertEquals(0, board.getValue(0, 2));
    }

    @Test
    void isFixed_blokojeKomorkeZCyfra() {
        assertTrue(board.isFixed(0, 0));
        assertFalse(board.isFixed(0, 2));
    }

    @Test
    void validate_wykrywaKonfliktWWierszu() {
        assertFalse(board.validate(0, 2, 5));
    }

    @Test
    void validate_wykrywaKonfliktWKolumnie() {
        assertFalse(board.validate(2, 0, 5));
    }

    @Test
    void validate_wykrywaKonfliktWKwadracie() {
        assertFalse(board.validate(1, 1, 9));
    }

    @Test
    void validate_akceptujePoprawnaCyfre() {
        assertTrue(board.validate(0, 2, 4));
    }

    @Test
    void isSolved_zwracaFalseDlaNiepelnejPlanszy() {
        assertFalse(board.isSolved());
    }

    @Test
    void isSolved_zwracaTrueDlaPelnejPoprawnejPlanszy() {
        Board solved = new Board();
        solved.loadPuzzle(new int[][] {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        });
        assertTrue(solved.isSolved());
    }

    @Test
    void copy_tworzeNiezaleznaKopie() {
        Board copy = board.copy();
        copy.setValue(0, 0, 9);

        assertEquals(5, board.getValue(0, 0));
        assertEquals(9, copy.getValue(0, 0));
    }

    @Test
    void copy_kopiujeFlageFixed() {
        Board copy = board.copy();
        assertTrue(copy.isFixed(0, 0));
        assertFalse(copy.isFixed(0, 2));
    }
}
