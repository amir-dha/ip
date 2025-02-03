package mochi.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");

    public Event(String desc, String from, String to) {
        super(desc);
        this.from = LocalDateTime.parse(from.trim(), INPUT_FORMAT);
        this.to = LocalDateTime.parse(to.trim(), INPUT_FORMAT);
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + desc + " | " + from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return " [E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}