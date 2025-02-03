package mochi.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORM = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORM = DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");

    /**
     * Constructs an Event task with a description, start time and end time.
     * @param desc The event description.
     * @param from The start time in "yyyy-MM-dd HHmm" format.
     * @param to The end time in "yyyy-MM-dd HHmm" format.
     */
    public Event(String desc, String from, String to) {
        super(desc);
        this.from = LocalDateTime.parse(from.trim(), INPUT_FORM);
        this.to = LocalDateTime.parse(to.trim(), INPUT_FORM);
    }

    /**
     * Converts the Event task into a formatted string for file storage.
     * @return The file representation of the task.
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + desc + " | " + from.format(INPUT_FORM) + " | " + to.format(INPUT_FORM);
    }

    /**
     * Returns a string representation of the Event task.
     * @return The formatted task string.
     */
    @Override
    public String toString() {
        return " [E]" + super.toString() + " (from: " + from.format(OUTPUT_FORM) + " to: " + to.format(OUTPUT_FORM) + ")";
    }
}