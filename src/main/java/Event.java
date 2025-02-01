import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORM = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORM = DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");

    public Event(String desc, String from, String to) {
        super(desc);
        this.from = LocalDateTime.parse(from.trim(), INPUT_FORM);
        this.to = LocalDateTime.parse(to.trim(), INPUT_FORM);
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + desc + " | " + from.format(INPUT_FORM) + " | " + to.format(INPUT_FORM);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORM) + " to: " + to.format(OUTPUT_FORM) + ")";
    }
}