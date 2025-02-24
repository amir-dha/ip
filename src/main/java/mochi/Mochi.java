package mochi;

import java.io.IOException;

import mochi.command.Command;
import mochi.storage.Storage;
import mochi.task.TaskList;
import mochi.ui.Ui;


/**
 * mochi.Main class for Mochi chatbot application.
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
        TaskList tempTasks;
        try {
            tempTasks = new TaskList(storage.loadTasks());
        } catch (IOException e) {
            ui.showLoadingError();
            tempTasks = new TaskList();
        }
        this.tasks = tempTasks;
    }

    /**
     * Handles user input for GUI mode.
     * @param input The user command.
     * @return The chatbot's response.
     */
    public String getResponse(String input) {
        return Command.processCommand(input, tasks, ui, storage);
    }
}









