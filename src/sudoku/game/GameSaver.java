package sudoku.game;

import sudoku.model.Board;
import java.io.*;

public class GameSaver {

    private static final String SAVE_FILE = "sudoku_save.txt";

    public static void save(Board board, GameState gameState) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    writer.print(board.getValue(row, col));
                    if (col < 8) writer.print(" ");
                }
                writer.println();
            }

            writer.println("FIXED");
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    writer.print(board.isFixed(row, col) ? 1 : 0);
                    if (col < 8) writer.print(" ");
                }
                writer.println();
            }

            writer.println("TIME " + gameState.getElapsedSeconds());
            writer.println("ERRORS " + gameState.getErrorCount());

        } catch (IOException e) {
            System.err.println("Błąd zapisu: " + e.getMessage());
        }
    }

    public static boolean load(Board board, GameState gameState) {
        File file = new File(SAVE_FILE);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            for (int row = 0; row < 9; row++) {
                String[] parts = reader.readLine().split(" ");
                for (int col = 0; col < 9; col++) {
                    board.setValue(row, col, Integer.parseInt(parts[col]));
                }
            }

            reader.readLine();

            for (int row = 0; row < 9; row++) {
                String[] parts = reader.readLine().split(" ");
                for (int col = 0; col < 9; col++) {
                    board.getCell(row, col).setFixed(parts[col].equals("1"));
                }
            }

            gameState.setElapsedSeconds(
                    Integer.parseInt(reader.readLine().split(" ")[1])
            );
            gameState.setErrorCount(
                    Integer.parseInt(reader.readLine().split(" ")[1])
            );

            return true;

        } catch (IOException | NumberFormatException e) {
            System.err.println("Błąd odczytu: " + e.getMessage());
            return false;
        }
    }

    public static boolean saveExists() {
        return new File(SAVE_FILE).exists();
    }

    public static void deleteSave() {
        new File(SAVE_FILE).delete();
    }

}
