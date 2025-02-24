package mochi.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import mochi.MochiException;
import mochi.task.Deadline;
import mochi.task.Event;
import mochi.task.Task;
import mochi.task.Todo;

/**
 * Handles file storage for tasks, including loading and saving tasks.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     * @param filePath The file path for storing tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * @return A list of loaded tasks.
     * @throws IOException If an error occurs during file reading.
     */
    public ArrayList<Task> loadTasks() throws IOException {
        File file = new File(filePath);
        ArrayList<Task> tasks = new ArrayList<>();
        createFileIfNotExists(file);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    /**
     * Saves tasks to storage file
     * @param tasks The list of tasks to be saved.
     * @throws IOException If an error occurs during the file writing.
     */
    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);
        createFileIfNotExists(file);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        }
    }

    /**
     * Parses a task from a formatted string.
     * @param line The formatted task string.
     * @return The parsed Task object, or null if parsing fails.
     */
    private Task parseTask(String line) {
        try {
            String[] parts = line.split(" \\| ");
            if (parts.length < 3) {
                return null;
            }
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String desc = parts[2];

            Task task = createTask(type, desc, parts);
            if (task != null && isDone) {
                task.markAsDone();
            }
            return task;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a Task object based on type.
     */
    private Task createTask(String type, String description, String[] parts) throws MochiException {
        return switch (type) {
        case "T" -> new Todo(description);
        case "D" -> parts.length > 3 ? new Deadline(description, parts[3]) : null;
        case "E" -> parts.length > 4 ? new Event(description, parts[3], parts[4]) : null;
        default -> null;
        };
    }

    /**
     * Ensures that the file exists, creating it if necessary.
     */
    private void createFileIfNotExists(File file) throws IOException {
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
    }
}
