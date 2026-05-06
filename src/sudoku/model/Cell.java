package sudoku.model;

public class Cell {
    private int value;
    private boolean isFixed;
    private boolean hasError;

    public Cell() {
        this.value = 0;
        this.isFixed = false;
        this.hasError = false;
    }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public boolean isFixed() { return isFixed; }
    public void setFixed(boolean fixed) { this.isFixed = fixed; }

    public boolean hasError() { return hasError; }
    public void setHasError(boolean hasError) { this.hasError = hasError; }
}
