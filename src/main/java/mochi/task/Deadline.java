package mochi.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");

    /**
     * Constructs a Deadline task with a description and a due date.
     * @param desc The task description.
     * @param by The deadline in "yyyy-MM-dd HHmm" format.
     */
    public Deadline(String desc, String by) {
        super(desc);
        this.by = LocalDateTime.parse(by, INPUT_FORMAT);
    }

    /**
     * Converts the Deadline task into a formatted string for file storage.
     * @return The file representation of the task.
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + desc + " | " + by.format(INPUT_FORMAT);
    }

    /**
     * Returns a string representation of the Deadline task.
     * @return The formatted task string.
     */
    @Override
    public String toString() {
        return " [D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
