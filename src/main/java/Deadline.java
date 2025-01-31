class Deadline extends Task {
    protected String by;

    public Deadline(String desc, String by) {
        super(desc);
        this.by = by;
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + desc + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
