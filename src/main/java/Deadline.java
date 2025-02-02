import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORM = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORM = DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");

    public Deadline(String desc, String by) {
        super(desc);
        this.by = LocalDateTime.parse(by, INPUT_FORM);
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + desc + " | " + by.format(INPUT_FORM);
    }

    @Override
    public String toString() {
        return " [D]" + super.toString() + " (by: " + by.format(OUTPUT_FORM) + ")";
    }
}
