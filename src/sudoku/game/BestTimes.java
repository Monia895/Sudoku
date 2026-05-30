package sudoku.game;

import sudoku.logic.Difficulty;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BestTimes {

    private static final String FILE = "best_times.txt";

    public static void save(Difficulty difficulty, int seconds) {
        Map<String, Integer> times = loadAll();
        String key = difficulty.name();

        if (!times.containsKey(key) || seconds < times.get(key)) {
            times.put(key, seconds);
            saveAll(times);
        }
    }

    public static Integer getBest(Difficulty difficulty) {
        Map<String, Integer> times = loadAll();
        return times.get(difficulty.name());
    }

    private static Map<String, Integer> loadAll() {
        Map<String, Integer> times = new HashMap<>();
        File file = new File(FILE);
        if (!file.exists()) return times;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    times.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Błąd odczytu najlepszych czasów: " + e.getMessage());
        }
        return times;
    }

    private static void saveAll(Map<String, Integer> times) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE))) {
            for (Map.Entry<String, Integer> entry : times.entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Błąd zapisu najlepszych czasów: " + e.getMessage());
        }
    }

    public static String format(int seconds) {
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }
}
