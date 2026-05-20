package sudoku.logic;

import sudoku.model.Board;

public class Solver {

    private Board board;

    public Solver(Board board) {
        this.board = board;
    }

    public boolean solve() {
        int[] emptyCell = findEmptyCell();

        if (emptyCell == null) return true;

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int value = 1; value <= 9; value++) {
            if (board.validate(row, col, value)) {
                board.setValue(row, col, value);

                if (solve()) return true;

                board.setValue(row, col, 0);
            }
        }

        return false;
    }

    private int[] findEmptyCell() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.getValue(row, col) == 0) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

}