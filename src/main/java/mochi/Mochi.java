package mochi;

import java.io.IOException;
import java.util.Scanner;
import mochi.command.Parser;
import mochi.command.Command;
import mochi.storage.Storage;
import mochi.ui.Ui;
import mochi.task.TaskList;

/**
 * Main class for Mochi chatbot application.
 * Handles user interactions, task management and file storage.
 */


public class Mochi {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    /**
     * Initializes the Mochi application with a specified file path for storing tasks.
     * @param filePath Path to the storage file.
     */
    public Mochi(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Main entry point for the application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new Mochi("./data/mochi.txt").run();
    }

    /**
     * Run the chatbot loop, processing user commands until exit command is given.
     */
    public void run() {
        ui.showWelcome();
        boolean isBye = false;
        Scanner scanner = new Scanner(System.in);

        while (!isBye) {
            try {
                String instruction = scanner.nextLine().trim();
                Command command = Parser.parse(instruction);
                command.exec(tasks, ui, storage);
                isBye = command.isBye();
            } catch (MochiException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Got some other error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}









