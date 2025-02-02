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

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task, Ui ui, Storage storage) throws IOException {
        tasks.add(task);
        storage.saveTasks(tasks);
        ui.showMessage(" Gotcha, I got add this task:\n" + task + "\n Now you got " + tasks.size() + " thingies to do.");
    }

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

    public void markTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        int taskInd = ind - 1;
        if (taskInd < 0 || taskInd >= tasks.size()) {
            throw new MochiException(" Babes, this task don't even exist?? How to mark?");
        }
        tasks.get(taskInd).markAsDone();
        storage.saveTasks(tasks);
        ui.showMessage(" Wow. You actually did something, that's one down I guess.\n" + tasks.get(taskInd));
    }

    public void unmarkTask(int ind, Ui ui, Storage storage) throws MochiException, IOException {
        int taskInd = ind - 1;
        if (taskInd < 0 || taskInd >= tasks.size()) {
            throw new MochiException(" Babes, this task don't even exist?? How to unmark?");
        }
        tasks.get(taskInd).markAsNotDone();
        storage.saveTasks(tasks);
        ui.showMessage(" Sigh, it was my fault to think you actually finished a task..\n" + tasks.get(taskInd));
    }

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
}
