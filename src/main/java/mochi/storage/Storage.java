package mochi.storage;

import mochi.task.TaskList;
import mochi.task.Task;
import mochi.task.Todo;
import mochi.task.Deadline;
import mochi.task.Event;
import mochi.MochiException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
