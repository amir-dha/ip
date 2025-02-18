package mochi.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mochi.MochiException;
import mochi.storage.Storage;
import mochi.ui.Ui;

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
     */
    public String addTask(Task task, Ui ui, Storage storage) throws IOException {
        tasks.add(task);
        storage.saveTasks(tasks);
        return "Gotcha, I got add this task:\n"
                + task + "\n Now you got " + tasks.size() + " thingies to do.";
    }

    /**
     * Deletes a task from the list and updates storage.
     */
    public String deleteTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        int taskInd = ind - 1;
        if (taskInd < 0 || taskInd >= tasks.size()) {
            throw new MochiException(" Babes this task doesn't exist. Give me a task that exists.");
        }

        Task task = tasks.remove(taskInd);
        storage.saveTasks(tasks);
        return "Can. Take out task already.\n" + task + "\n Now you got "
                + tasks.size() + " tasks in the list.";
    }

    /**
     * Marks a task as done.
     */
    public String markTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        int taskInd = ind - 1;
        if (taskInd < 0 || taskInd >= tasks.size()) {
            throw new MochiException(" Babes, this task don't even exist?? How to mark?");
        }
        tasks.get(taskInd).markAsDone();
        storage.saveTasks(tasks);
        return "Wow. You actually did something, that's one down I guess.\n" + tasks.get(taskInd);
    }

    /**
     * Marks a task as undone.
     */
    public String unmarkTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        int taskInd = ind - 1;
        if (taskInd < 0 || taskInd >= tasks.size()) {
            throw new MochiException(" Babes, this task don't even exist?? How to unmark?");
        }
        tasks.get(taskInd).markAsNotDone();
        storage.saveTasks(tasks);
        return "Sigh, it was my fault to think you actually finished a task..\n" + tasks.get(taskInd);
    }

    /**
     * Lists all tasks in the TaskList.
     */
    public String listTasks(Ui ui) {
        if (tasks.isEmpty()) {
            return "No task leh. You so free ah??";
        } else {
            StringBuilder result = new StringBuilder("Look at the consequences of your procrastination:\n");
            for (int i = 0; i < tasks.size(); i++) {
                result.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
            }
            return result.toString();
        }
    }

    /**
     * Finds and lists tasks that contain the given keyword in their description.
     */
    public String findTasks(String keyword, Ui ui) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            return "Bro no such task eh.";
        } else {
            StringBuilder result = new StringBuilder("Here, the task that match in your never-ending list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                result.append((i + 1)).append(". ").append(matchingTasks.get(i)).append("\n");
            }
            return result.toString();
        }
    }
}
