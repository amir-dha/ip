package mochi.task;

import mochi.MochiException;
import mochi.storage.Storage;
import mochi.ui.Ui;
import mochi.MochiException;
import mochi.task.Task;
import mochi.task.Todo;
import mochi.task.Deadline;
import mochi.task.Event;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the list of tasks and provides operations to modify them.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with existing tasks.
     * @param tasks The list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the list of tasks.
     * @return A list of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the list and saves it.
     * @param task The task to add.
     * @param ui The UI instance for displaying messages.
     * @param storage The storage instance for saving tasks.
     * @throws IOException If an error occurs while saving.
     */
    public void addTask(Task task, Ui ui, Storage storage) throws IOException {
        tasks.add(task);
        storage.saveTasks(tasks);
        ui.showMessage(" Gotcha, I got add this task:\n" + task + "\n Now you got " + tasks.size() + " thingies to do.");
    }

    /**
     * Deletes a task from the list and updates storage.
     * @param ind The task index.
     * @param ui The UI instance for displaying messages.
     * @param storage The storage instance for saving tasks.
     * @throws MochiException If the index is invalid.
     * @throws IOException If an error occurs while saving.
     */
    public void deleteTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        int taskInd = ind - 1;
        if (taskInd < 0 || taskInd >= tasks.size()) {
            throw new MochiException(" Babes this task doesn't exist. Give me a task that exists.");
        }

        Task task = tasks.remove(taskInd);
        storage.saveTasks(tasks);

        ui.showMessage(" Can. Take out task already.\n" + task + "\n Now you got "
                + tasks.size() + " tasks in the list.");
    }

    /**
     * Marks a task as done.
     * @param ind The task index.
     * @param ui The UI instance for displaying messages.
     * @param storage The storage instance for saving tasks.
     * @throws MochiException If the index is invalid.
     * @throws IOException If an error occurs while saving.
     */
    public void markTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        int taskInd = ind - 1;
        if (taskInd < 0 || taskInd >= tasks.size()) {
            throw new MochiException(" Babes, this task don't even exist?? How to mark?");
        }
        tasks.get(taskInd).markAsDone();
        storage.saveTasks(tasks);
        ui.showMessage(" Wow. You actually did something, that's one down I guess.\n" + tasks.get(taskInd));
    }

    /**
     * Marks a task as undone.
     * @param ind The task index.
     * @param ui The UI instance for displaying messages.
     * @param storage The storage instance for saving tasks.
     * @throws MochiException If the index is invalid.
     * @throws IOException If an error occurs while saving.
     */
    public void unmarkTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        int taskInd = ind - 1;
        if (taskInd < 0 || taskInd >= tasks.size()) {
            throw new MochiException(" Babes, this task don't even exist?? How to unmark?");
        }
        tasks.get(taskInd).markAsNotDone();
        storage.saveTasks(tasks);
        ui.showMessage(" Sigh, it was my fault to think you actually finished a task..\n" + tasks.get(taskInd));
    }

    /**
     * Lists all tasks in the TaskList.
     * @param ui The UI instance for displaying messages.
     */
    public void listTasks(Ui ui) {
        if (tasks.isEmpty()) {
            ui.showMessage(" No task leh. You so free ah??");
        } else {
            ui.printLine();
            System.out.println(" Look at the consequences of your procrastination:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + tasks.get(i));
            }
            ui.printLine();
        }
    }

    /**
     * Finds and lists tasks that contain the given keyword in their description.
     * @param keyword The keyword to search for.
     * @param ui The UI instance for displaying messages.
     */
    public void findTasks(String keyword, Ui ui) {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            ui.showMessage("Bro no such task eh.");
        } else {
            ui.printLine();
            System.out.println("Here, the task that match in your never-ending list.");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + ". " + matchingTasks.get(i));
            }

            ui.printLine();;
        }
    }
}
