package sudoku.game;

public class GameState {
    private int errorCount;
    private int elapsedSeconds;
    private boolean isRunning;

    public GameState() {
        this.errorCount = 0;
        this.elapsedSeconds = 0;
        this.isRunning = false;
    }

    public int getErrorCount() { return errorCount; }
    public void incrementErrors() { this.errorCount++; }

    public int getElapsedSeconds() { return elapsedSeconds; }
    public void incrementTime() { this.elapsedSeconds++; }
    public void resetTime() { this.elapsedSeconds = 0; }

    public boolean isRunning() { return isRunning; }
    public void setRunning(boolean running) { this.isRunning = running; }

    public void reset() {
        errorCount = 0;
        elapsedSeconds = 0;
        isRunning = false;
        hintsLeft = 3;
    }

    public String getFormattedTime() {
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void setElapsedSeconds(int seconds) {
        this.elapsedSeconds = seconds;
    }

    public void setErrorCount(int count) {
        this.errorCount = count;
    }

    private int hintsLeft = 3;

    public int getHintsLeft() { return hintsLeft; }

    public boolean useHint() {
        if (hintsLeft <= 0) return false;
        hintsLeft--;
        return true;
    }

    public void resetHints() { hintsLeft = 3; }
}
