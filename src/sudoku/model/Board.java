package sudoku.model;

public class Board {
    private Cell[][] cells;

    public Board() {
        cells = new Cell[9][9];
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                cells[row][col] = new Cell();
    }

    public int getValue(int row, int col) {
        return cells[row][col].getValue();
    }

    public void setValue(int row, int col, int value) {
        cells[row][col].setValue(value);
    }

    public boolean isFixed(int row, int col) {
        return cells[row][col].isFixed();
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public void loadPuzzle(int[][] puzzle) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = puzzle[row][col];
                cells[row][col].setValue(value);
                cells[row][col].setFixed(value > 0);
            }
        }
    }

    public boolean validate(int row, int col, int value) {
        for (int c = 0; c < 9; c++) {
            if (c != col && cells[row][c].getValue() == value) return false;
        }

        for (int r = 0; r < 9; r++) {
            if (r != row && cells[r][col].getValue() == value) return false;
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (r != row && c != col && cells[r][c].getValue() == value) return false;
            }
        }

        return true;
    }

    public boolean isSolved() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Cell cell = cells[row][col];
                if (cell.getValue() == 0) return false;
                if (cell.hasError()) return false;
            }
        }
        return true;
    }

    public Board copy() {
        Board copy = new Board();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                copy.getCell(row, col).setValue(cells[row][col].getValue());
                copy.getCell(row, col).setFixed(cells[row][col].isFixed());
            }
        }
        return copy;
    }


}
