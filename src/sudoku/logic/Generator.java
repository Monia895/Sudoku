package sudoku.logic;

import sudoku.model.Board;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Generator {

    private Random random = new Random();

    public Board generate(Difficulty difficulty) {
        Board board = new Board();
        fillBoard(board);
        removeNumbers(board, difficulty.getCellsToRemove());
        return board;
    }

    private boolean fillBoard(Board board) {
        int[] emptyCell = findEmptyCell(board);
        if (emptyCell == null) return true;

        int row = emptyCell[0];
        int col = emptyCell[1];

        List<Integer> numbers = shuffledNumbers();

        for (int value : numbers) {
            if (board.validate(row, col, value)) {
                board.setValue(row, col, value);

                if (fillBoard(board)) return true;

                board.setValue(row, col, 0);
            }
        }

        return false;
    }

    private void removeNumbers(Board board, int count) {
        List<int[]> positions = getAllPositions();
        Collections.shuffle(positions, random);

        int removed = 0;
        for (int[] pos : positions) {
            if (removed >= count) break;

            int row = pos[0];
            int col = pos[1];
            int backup = board.getValue(row, col);

            board.setValue(row, col, 0);

            if (hasUniqueSolution(board)) {
                removed++;
            } else {
                board.setValue(row, col, backup);
            }
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board.getCell(row, col).setFixed(board.getValue(row, col) > 0);
            }
        }
    }

    private boolean hasUniqueSolution(Board board) {
        Board copy = board.copy();
        return countSolutions(copy, 0) == 1;
    }

    private int countSolutions(Board board, int count) {
        if (count > 1) return count;

        int[] emptyCell = findEmptyCell(board);
        if (emptyCell == null) return count + 1;

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int value = 1; value <= 9; value++) {
            if (board.validate(row, col, value)) {
                board.setValue(row, col, value);
                count = countSolutions(board, count);
                board.setValue(row, col, 0);
            }
        }

        return count;
    }

    private int[] findEmptyCell(Board board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.getValue(row, col) == 0) return new int[]{row, col};
            }
        }
        return null;
    }

    private List<Integer> shuffledNumbers() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) numbers.add(i);
        Collections.shuffle(numbers, random);
        return numbers;
    }

    private List<int[]> getAllPositions() {
        List<int[]> positions = new ArrayList<>();
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                positions.add(new int[]{row, col});
        return positions;
    }
}