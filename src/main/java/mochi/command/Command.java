package mochi.command;


import mochi.task.Task;
import mochi.task.Todo;
import mochi.task.Deadline;
import mochi.task.Event;
import mochi.task.TaskList;
import mochi.storage.Storage;
import mochi.ui.Ui;
import mochi.MochiException;
import java.io.IOException;

/**
 * Represents an abstract command that can be executed on the task list.
 */
public abstract class Command {

    /**
     * Executes the command on the given task list, user interface and storage.
     * @param tasks The task list to modify.
     * @param ui The UI instance for displaying messages.
     * @param storage The storage instance for saving tasks.
     * @throws IOException If an error occurs while saving.
     * @throws MochiException If the command execution fails.
     */
    public abstract void exec(TaskList tasks, Ui ui, Storage storage) throws IOException, MochiException;
    public boolean isBye() {
        return false;
    }
}

/**
 * Handles the "bye" command to exit the application.
 */
class ByeCommand extends Command {
    @Override
    public void exec(TaskList tasks, Ui ui, Storage storage) {
        ui.showMessage(" Bye. Sayonara. Begone.");
    }

    @Override
    public boolean isBye() {
        return true;
    }
}

/**
 * Handles the "list" command to display all tasks.
 */
class ListCommand extends Command {

    @Override
    public void exec(TaskList tasks, Ui ui, Storage storage) {
        tasks.listTasks(ui);
    }
}

/**
 * Handles the "add" command to add a task to the task list.
 */
class AddCommand extends Command {
    private Task task;

    /**
     * Constructs an AddCommand with the specified task.
     * @param task The task to add.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void exec(TaskList tasks, Ui ui, Storage storage) throws IOException {
        tasks.addTask(task, ui, storage);
    }
}

/**
 * Handles the "delete" command to remove a task from the task list.
 */
class DeleteCommand extends Command {
    private int taskInd;

    /**
     * Constructs a DeleteCommand with the specified task index.
     * @param taskInd The index of the task to delete.
     */
    public DeleteCommand(int taskInd) {
        this.taskInd = taskInd;
    }

    @Override
    public void exec(TaskList tasks, Ui ui, Storage storage) throws IOException, MochiException {
        tasks.deleteTask(taskInd, ui, storage);
    }
}

/**
 * Handles the "Mark" command to mark a task as done.
 */
class MarkCommand extends Command {
    private int taskInd;

    /**
     * Constructs a MarkCommand with the specified task index.
     * @param taskInd The index of the task to mark as done.
     */
    public MarkCommand(int taskInd) {
        this.taskInd = taskInd;
    }

    @Override
    public void exec(TaskList tasks, Ui ui, Storage storage) throws IOException, MochiException {
        tasks.markTask(taskInd, ui, storage);
    }
}

/**
 * Handles the "unmark" command to mark a test as not done.
 */
class UnmarkCommand extends Command {
    private int taskInd;

    /**
     * Constructs an UnmarkCommand with the specified task index.
     * @param taskInd The index of the task to mark as not done.
     */
    public UnmarkCommand(int taskInd) {
        this.taskInd = taskInd;
    }

    @Override
    public void exec(TaskList tasks, Ui ui, Storage storage) throws IOException, MochiException {
        tasks.unmarkTask(taskInd, ui, storage);
    }
}

