class Todo extends Task {
    public Todo(String desc) {
        super(desc);
    }

    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + desc;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
