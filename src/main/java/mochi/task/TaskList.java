package mochi.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mochi.MochiException;
import mochi.storage.Storage;
import mochi.ui.Ui;

/**
 * Manages the list of tasks and provides operations to modify them.
 */
public class TaskList {
    private static final String NO_TASKS_MESSAGE = "No task leh. You so free ah??";
    private static final String TASK_NOT_FOUND = " Babes, this task don't even exist?? How to ";
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
        return String.format("Gotcha, I got add this task:\n%s\n Now you got %d thingies to do.", task, tasks.size());
    }

    /**
     * Deletes a task from the list and updates storage.
     */
    public String deleteTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        Task task = getTaskByIndex(ind, "delete");
        tasks.remove(task);
        storage.saveTasks(tasks);
        return String.format("Can. Take out task already.\n%s\n Now you got %d tasks in the list.", task, tasks.size());
    }

    /**
     * Marks a task as done.
     */
    public String markTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        Task task = getTaskByIndex(ind, "delete");
        task.markAsDone();
        storage.saveTasks(tasks);
        return String.format("Wow. You actually did something, that's one down I guess.\n%s", task);
    }

    /**
     * Marks a task as undone.
     */
    public String unmarkTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        Task task = getTaskByIndex(ind, "unmark");
        task.markAsNotDone();
        storage.saveTasks(tasks);
        return String.format("Wow. You actually did something, that's one down I guess.\n%s", task);
    }

    /**
     * Sorts the tasks based on type:
     * - Deadlines first (by date)
     * - Events second (by date)
     * - ToDos last (alphabetically)
     */
    public void sortTasks() {
        Collections.sort(tasks, Comparator
                .comparing(this::getTaskType)
                .thenComparing(this::compareByDateOrAlphabet));
    }

    /**
     * Determines the task type for sorting order.
     * @param task The task to classify.
     * @return An integer representing the sorting priority.
     */
    private int getTaskType(Task task) {
        if (task instanceof Deadline) {
            return 1;
        } else if (task instanceof Event) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * Compares tasks either by date (for Deadlines/Events) or alphabetically (for ToDos).
     * @param t1 First task.
     * @param t2 Second task.
     * @return Comparison result: - 1 (t1 before t2), 1 (t2 before t1), 0 (equal).
     */
    private int compareByDateOrAlphabet(Task t1, Task t2) {
        if (t1 instanceof Deadline && t2 instanceof Deadline) {
            return ((Deadline) t1).getBy().compareTo(((Deadline) t2).getBy());
        }
        if (t1 instanceof Event && t2 instanceof Event) {
            return ((Event) t1).getFrom().compareTo(((Event) t2).getFrom());
        }
        if (t1 instanceof Todo && t2 instanceof Todo) {
            return t1.getDescription().compareToIgnoreCase(t2.getDescription());
        }
        return 0;
    }

    /**
     * Lists all tasks in the TaskList.
     */
    public String listTasks(Ui ui) {
        if (tasks.isEmpty()) {
            return TaskList.NO_TASKS_MESSAGE;
        }

        sortTasks();

        StringBuilder result = new StringBuilder("Look at the consequences of your procrastination:\n");
        for (int i = 0; i < tasks.size(); i++) {
            result.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return result.toString();
    }

    /**
     * Finds and lists tasks that contain the given keyword in their description.
     */
    public String findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            return "Bro no such task eh.";

        }
        StringBuilder result = new StringBuilder("Here, the task that match in your never-ending list:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            result.append((i + 1)).append(". ").append(matchingTasks.get(i)).append("\n");
        }
        return result.toString();
    }
    private Task getTaskByIndex(int ind, String action) throws MochiException {
        int taskInd = ind - 1;
        if (taskInd < 0 || taskInd >= tasks.size()) {
            throw new MochiException(TASK_NOT_FOUND + action + "?");
        }
        return tasks.get(taskInd);
    }
}
