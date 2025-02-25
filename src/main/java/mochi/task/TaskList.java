package mochi.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import mochi.MochiException;
import mochi.storage.Storage;
import mochi.ui.Ui;

/**
 * Manages the list of tasks and provides operations to modify them.
 */
public class TaskList {
    private static final String NO_TASKS_MESSAGE = "No task leh. You so free ah??";
    private static final String TASK_NOT_FOUND = "Babes, this task doesn't even exist?? How to ";
    private ArrayList<Task> tasks;
    /**
     * Comparator to sort tasks based on date (Deadlines/Events) or alphabetically (ToDos).
     */
    private final Comparator<Task> compareByDateOrAlphabet = (t1, t2) -> {
        if (t1 instanceof Deadline && t2 instanceof Deadline) {
            return ((Deadline) t1).getBy().compareTo(((Deadline) t2).getBy());
        }
        if (t1 instanceof Event && t2 instanceof Event) {
            return ((Event) t1).getFrom().compareTo(((Event) t2).getFrom());
        }
        if (t1 instanceof Todo && t2 instanceof Todo) {
            return t1.toString().compareToIgnoreCase(t2.toString());
        }
        return 0;
    };

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
    public String addTask(Task task, Ui ui, Storage storage) throws IOException {
        tasks.add(task);
        storage.saveTasks(tasks);
        return String.format("Gotcha, I got add this task:\n%s\nNow you got %d thingies to do.", task, tasks.size());
    }

    /**
     * Deletes a task from the list and updates storage.
     * @param ind The index of the task to delete.
     * @param ui The UI instance for displaying messages.
     * @param storage The storage instance for saving tasks.
     * @throws MochiException If the task index is invalid.
     * @throws IOException If an error occurs while saving.
     */
    public String deleteTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        Task task = getTaskByIndex(ind, "delete");
        tasks.remove(task);
        storage.saveTasks(tasks);
        return String.format("Can. Take out task already.\n%s\nNow you got %d tasks in the list.", task, tasks.size());
    }

    /**
     * Marks a task as done.
     * @param ind The index of the task to mark as done.
     * @param ui The UI instance for displaying messages.
     * @param storage The storage instance for saving tasks.
     * @throws MochiException If the task index is invalid.
     * @throws IOException If an error occurs while saving.
     */
    public String markTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        Task task = getTaskByIndex(ind, "mark");
        task.markAsDone();
        storage.saveTasks(tasks);
        return String.format("Wow. You actually did something, that's one down I guess.\n%s", task);
    }

    /**
     * Marks a task as undone.
     * @param ind The index of the task to mark as not done.
     * @param ui The UI instance for displaying messages.
     * @param storage The storage instance for saving tasks.
     * @throws MochiException If the task index is invalid.
     * @throws IOException If an error occurs while saving.
     */
    public String unmarkTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        Task task = getTaskByIndex(ind, "unmark");
        task.markAsNotDone();
        storage.saveTasks(tasks);
        return String.format("Sigh, I thought you actually finished something...\n%s", task);
    }

    /**
     * Sorts the tasks based on type:
     * - Deadlines first (by date)
     * - Events second (by date)
     * - ToDos last (alphabetically)
     */
    public void sortTasks() {
        tasks.sort(Comparator
                .comparing(this::getTaskType)
                .thenComparing(compareByDateOrAlphabet));
    }

    /**
     * Lists all tasks in the TaskList, ensuring they are sorted before displaying.
     * @param ui The UI instance for displaying messages.
     * @return A string representation of the sorted task list.
     */
    public String listSortedTasks(Ui ui) {
        if (tasks.isEmpty()) {
            return NO_TASKS_MESSAGE;
        }
        sortTasks();
        return formatTaskList(tasks, "Look at the consequences of your procrastination:");
    }

    /**
     * Sorts tasks by category: todos, deadlines, or events.
     * @param category The category to sort (e.g., "todos", "deadlines", "events").
     * @return A formatted list of sorted tasks in the specified category.
     * @throws MochiException If the category is invalid.
     */
    public String listSortedTasksByCategory(String category) throws MochiException {
        List<Task> sortedTasks = getSortedTasksByCategory(category);

        if (sortedTasks.isEmpty()) {
            return "No tasks found in category: " + category;
        }

        String headerMessage = switch (category) {
        case "todos" -> "Yo here is ur todo sorted, like that's gonna help u with ur work.";
        case "deadlines" -> "Here are ur deadlines, sorted so you can still procrastinate efficiently.";
        case "events" -> "Sorted events. Maybe now u can stop missing them??";
        default -> throw new MochiException("Unexpected sorting category.");
        };

        return formatTaskList(sortedTasks, headerMessage);
    }

    /**
     * Retrieves a sorted list of tasks based on category.
     * @param category The category to filter by ("todos", "deadlines", "events").
     * @return A sorted list of tasks belonging to the given category.
     * @throws MochiException If the category is invalid.
     */
    public List<Task> getSortedTasksByCategory(String category) throws MochiException {
        return switch (category) {
        case "todos" -> tasks.stream().filter(task -> task instanceof Todo).sorted(compareByDateOrAlphabet)
                .collect(Collectors.toList());
        case "deadlines" -> tasks.stream().filter(task -> task instanceof Deadline)
                .sorted(compareByDateOrAlphabet).collect(Collectors.toList());
        case "events" -> tasks.stream().filter(task -> task instanceof Event)
                .sorted(compareByDateOrAlphabet).collect(Collectors.toList());
        default -> throw new MochiException("Oi sort what? Use: sort todos | sort deadlines | sort events.");
        };
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
            return 3; // ToDos last
        }
    }

    /**
     * Formats a list of tasks with a header message.
     * @param tasks The tasks to format.
     * @param header The header message.
     * @return A formatted string representation of the tasks.
     */
    private String formatTaskList(List<Task> tasks, String header) {
        StringBuilder sb = new StringBuilder(header + "\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Finds and lists tasks that contain the given keyword in their description.
     * @param keyword The keyword to search for.
     * @param ui The UI instance for displaying messages.
     * @return Formatted list of matching tasks.
     */
    public String findTasks(String keyword, Ui ui) {
        List<Task> matchingTasks = tasks.stream()
                .filter(task -> task.toString().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if (matchingTasks.isEmpty()) {
            return ui.showMessage("Bro no such task eh.");
        }

        return formatTaskList(matchingTasks, "Here, the tasks that match in your never-ending list:");
    }


    private Task getTaskByIndex(int ind, String action) throws MochiException {
        int taskInd = ind - 1;
        if (taskInd < 0 || taskInd >= tasks.size()) {
            throw new MochiException(TASK_NOT_FOUND + action + "?");
        }
        return tasks.get(taskInd);
    }
}

