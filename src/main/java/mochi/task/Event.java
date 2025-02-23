package mochi.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");
    protected LocalDateTime from;
    protected LocalDateTime to;
    /**
     * Constructs an Event task with a description, start time and end time.
     * @param desc The event description.
     * @param from The start time in "yyyy-MM-dd HHmm" format.
     * @param to The end time in "yyyy-MM-dd HHmm" format.
     */
    public Event(String desc, String from, String to) {
        super(desc);
        this.from = LocalDateTime.parse(from.trim(), INPUT_FORMAT);
        this.to = LocalDateTime.parse(to.trim(), INPUT_FORMAT);
    }

    /**
     * Converts the Event task into a formatted string for file storage.
     * @return The file representation of the task.
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | "
                + desc + " | " + from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns a string representation of the Event task.
     * @return The formatted task string.
     */
    @Override
    public String toString() {
        return " [E]" + super.toString() + " (from: "
                + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
