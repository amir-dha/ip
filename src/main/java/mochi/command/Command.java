package mochi.command;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import mochi.MochiException;
import mochi.storage.Storage;
import mochi.task.Task;
import mochi.task.TaskList;
import mochi.ui.Ui;

/**
 * Represents an abstract command that can be executed on the task list.
 */
public abstract class Command {
    /**
     * Executes the command on the given task list, user interface, and storage.
     * @param tasks The task list to modify.
     * @param ui The UI instance for displaying messages.
     * @param storage The storage instance for saving tasks.
     * @throws IOException If an error occurs while saving.
     * @throws MochiException If the command execution fails.
     */
    public abstract String exec(TaskList tasks, Ui ui, Storage storage) throws IOException, MochiException;

    public boolean isBye() {
        return false;
    }

    /**
     * Processes a given user command and returns the response.
     * Moves execution logic away from Mochi.java for cleaner design.
     */
    public static String processCommand(String input, TaskList tasks, Ui ui, Storage storage) {
        try {
            Command command = Parser.parse(input);
            return command.exec(tasks, ui, storage);
        } catch (MochiException e) {
            return ui.showError(e.getMessage());
        } catch (IOException e) {
            return ui.showUnexpectedError("File error: " + e.getMessage());
        } catch (Exception e) {
            return ui.showUnexpectedError(e.getMessage());
        }
    }
}

/**
 * Handles the "bye" command to exit the application.
 */
class ByeCommand extends Command {
    @Override
    public String exec(TaskList tasks, Ui ui, Storage storage) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.exit();
                System.exit(0);
            }
        }, 1500);

        return ui.showByeMessage();
    }

    @Override
    public boolean isBye() {
        return true;
    }
}

/**
 * Returns the sorted task list for a specific category.
 */
class SortCommand extends Command {
    private final String category;
    public SortCommand(String category) {
        this.category = category;
    }
    @Override
    public String exec(TaskList tasks, Ui ui, Storage storage) throws MochiException {
        if (category.equals("all")) {
            return tasks.listSortedTasks(ui);
        }
        return tasks.listSortedTasksByCategory(category);
    }
}

/**
 * Handles the "list" command to display all tasks.
 */
class ListCommand extends Command {
    @Override
    public String exec(TaskList tasks, Ui ui, Storage storage) {
        return tasks.listSortedTasks(ui);
    }
}

/**
 * Handles the "add" command to add a task to the task list.
 */
class AddCommand extends Command {
    private final Task task;

    /**
     * Constructs an AddCommand with the specified task.
     * @param task The task to add.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public String exec(TaskList tasks, Ui ui, Storage storage) throws IOException {
        return tasks.addTask(task, ui, storage);
    }
}

/**
 * Handles the "delete" command to remove a task from the task list.
 */
class DeleteCommand extends Command {
    private final int taskInd;

    /**
     * Constructs a DeleteCommand with the specified task index.
     * @param taskInd The index of the task to delete.
     */
    public DeleteCommand(int taskInd) {
        this.taskInd = taskInd;
    }

    @Override
    public String exec(TaskList tasks, Ui ui, Storage storage) throws IOException, MochiException {
        return tasks.deleteTask(taskInd, ui, storage);
    }
}

/**
 * Handles the "mark" command to mark a task as done.
 */
class MarkCommand extends Command {
    private final int taskInd;

    /**
     * Constructs a MarkCommand with the specified task index.
     * @param taskInd The index of the task to mark as done.
     */
    public MarkCommand(int taskInd) {
        this.taskInd = taskInd;
    }

    @Override
    public String exec(TaskList tasks, Ui ui, Storage storage) throws IOException, MochiException {
        return tasks.markTask(taskInd, ui, storage);
    }
}

/**
 * Handles the "unmark" command to mark a task as not done.
 */
class UnmarkCommand extends Command {
    private final int taskInd;

    /**
     * Constructs an UnmarkCommand with the specified task index.
     * @param taskInd The index of the task to mark as not done.
     */
    public UnmarkCommand(int taskInd) {
        this.taskInd = taskInd;
    }

    @Override
    public String exec(TaskList tasks, Ui ui, Storage storage) throws IOException, MochiException {
        return tasks.unmarkTask(taskInd, ui, storage);
    }
}

/**
 * Handles the "find" command to search for tasks containing a given keyword.
 */
class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     * @param keyword The keyword to search for in tasks.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String exec(TaskList tasks, Ui ui, Storage storage) {
        return tasks.findTasks(keyword, ui);
    }
}
