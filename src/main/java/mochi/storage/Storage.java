package mochi.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import mochi.task.Deadline;
import mochi.task.Event;
import mochi.task.Task;
import mochi.task.Todo;




/**
 * Handles file storage for tasks, including loading and saving tasks.
 */
public class Storage {
    private String filePath;

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

        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
            return tasks;
        }

        Scanner sc = new Scanner(new FileReader(file));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Task task = parseTask(line);
            if (task != null) {
                tasks.add(task);
            }
        }
        sc.close();
        return tasks;
    }

    /**
     * Saves tasks to storage file
     * @param tasks The list of tasks to be saved.
     * @throws IOException If an error occurs during the file writing.
     */
    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);

        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (Task task : tasks) {
            writer.write(task.toFileString());
            writer.newLine();
        }
        writer.close();
    }

    /**
     * Parses a task from a formatted string.
     * @param line The formatted task string.
     * @return The parsed Task object, or null if parsing fails.
     */
    private Task parseTask(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String desc = parts[2];

            Task task;
            switch (type) {
            case "T":
                task = new Todo(desc);
                break;
            case "D":
                task = new Deadline(desc, parts[3]);
                break;
            case "E":
                task = new Event(desc, parts[3], parts[4]);
                break;
            default:
                return null;
            }

            if (isDone) {
                task.markAsDone();
            }
            return task;
        } catch (Exception e) {
            return null;
        }
    }
}
