package mochi.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");

    public Deadline(String desc, String by) {
        super(desc);
        this.by = LocalDateTime.parse(by, INPUT_FORMAT);
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + desc + " | " + by.format(INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return " [D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
