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
}
