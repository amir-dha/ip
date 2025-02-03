package mochi.task;

/**
 * Represents a general task with a description and completion status.
 */
public abstract class Task {
    protected String desc;
    protected boolean isDone;

    /**
     * Constructs a Task with a given description.
     * @param desc The task description.
     */
    public Task(String desc) {
        this.desc = desc;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * retrieves the task's status.
     * @return A string indicating whether the task is completed.
     */
    public String getStatus() {
        return (isDone ? "[X]" : "[ ]");
    }

    /**
     * Converts the task to a format suitable for the file storage.
     * @return The file representation of the task.
     */
    public abstract String toFileString();

    /**
     * Returns a string representation of the task.
     * @return The formatted task string.
     */
    @Override
    public String toString() {
        return getStatus() + " " + desc;
    }
}