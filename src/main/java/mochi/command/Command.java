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

public abstract class Command {
    public abstract void exec(TaskList tasks, Ui ui, Storage storage) throws IOException, MochiException;
    public boolean isBye() {
        return false;
    }
}

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

class ListCommand extends Command {

    @Override
    public void exec(TaskList tasks, Ui ui, Storage storage) {
        tasks.listTasks(ui);
    }
}

class AddCommand extends Command {
    private Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void exec(TaskList tasks, Ui ui, Storage storage) throws IOException {
        tasks.addTask(task, ui, storage);
    }
}

class DeleteCommand extends Command {
    private int taskInd;

    public DeleteCommand(int taskInd) {
        this.taskInd = taskInd;
    }

    @Override
    public void exec(TaskList tasks, Ui ui, Storage storage) throws IOException, MochiException {
        tasks.deleteTask(taskInd, ui, storage);
    }
}

class MarkCommand extends Command {
    private int taskInd;

    public MarkCommand(int taskInd) {
        this.taskInd = taskInd;
    }

    @Override
    public void exec(TaskList tasks, Ui ui, Storage storage) throws IOException, MochiException {
        tasks.markTask(taskInd, ui, storage);
    }
}

class UnmarkCommand extends Command {
    private int taskInd;

    public UnmarkCommand(int taskInd) {
        this.taskInd = taskInd;
    }

    @Override
    public void exec(TaskList tasks, Ui ui, Storage storage) throws IOException, MochiException {
        tasks.unmarkTask(taskInd, ui, storage);
    }

}

/**
 * Handles the "find" command to search for tasks containing a given keyword.
 */
class FindCommand extends Command {
    private String keyword;

    /**
     * Constructs a FindComment with the specified keyword.
     * @param keyword The keyword to search for in tasks.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void exec(TaskList tasks, Ui ui, Storage storage) {
        tasks.findTasks(keyword, ui);
    }
}

