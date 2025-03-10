package mochi.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import mochi.MochiException;
/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter EXPECTED_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");
    protected LocalDateTime by;
    /**
     * Constructs a Deadline task with a description and a due date.
     * @param desc The task description.
     * @param by The deadline in "yyyy-MM-dd HHmm" format.
     */
    public Deadline(String desc, String by) throws MochiException {
        super(desc);
        try {
            this.by = LocalDateTime.parse(by, EXPECTED_FORMAT);
        } catch (DateTimeParseException e) {
            throw new MochiException("Oi, invalid time format! Use 'yyyy-MM-dd HHmm' only. "
                    + "No need for ':' or AM/PM nonsense.");
        }

    }

    /**
     * Converts the Deadline task into a formatted string for file storage.
     * @return The file representation of the task.
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + desc + " | " + by.format(EXPECTED_FORMAT);
    }

    public LocalDateTime getBy() {
        return by;
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
