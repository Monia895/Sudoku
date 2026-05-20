package sudoku.logic;

public enum Difficulty {
    EASY(35),
    MEDIUM(45),
    HARD(52);

    private final int cellsToRemove;

    Difficulty(int cellsToRemove) {
        this.cellsToRemove = cellsToRemove;
    }

    public int getCellsToRemove() {
        return cellsToRemove;
    }

    @Override
    public String toString() {
        switch (this) {
            case EASY: return "Łatwy";
            case MEDIUM: return "Średni";
            case HARD: return "Trudny";
            default: return "";
        }
    }
}
