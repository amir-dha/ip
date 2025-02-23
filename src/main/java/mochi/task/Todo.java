package mochi.task;

/**
 * Represents a to-do task.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with a given description.
     * @param desc The task description.
     */
    public Todo(String desc) {
        super(desc);
    }
    /**
     * Converts the Todo task into a formatted string for file storage.
     * @return The file representation of the task.
     */
    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + desc;
    }

    /**
     * Returns a string representation of the Todo task.
     * @return The formatted task string.
     */
    @Override
    public String toString() {
        return " [T]" + super.toString();
    }
}
