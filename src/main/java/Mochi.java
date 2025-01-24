import java.util.ArrayList;
import java.util.Scanner;

public class Mochi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = " ----------------------------------------------------------------------------";
        String botName = "Mochi";
        ArrayList<Task> tasks = new ArrayList<>();


        System.out.println(line);
        System.out.println(" It's you again.. " + botName + " at your service miserably.");
        System.out.println(" What you want?");
        System.out.println(line);

        while (true) {
            String input = sc.nextLine();

            try {
                if (input.equals("bye")) {
                    System.out.println(line);
                    System.out.println(" Bye. Sayonara. Be gone.");
                    System.out.println(line);
                    break;

                } else if (input.equals("list")) {
                    System.out.println(line);
                    System.out.println(" Look at the consequences of your procrastination:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println(line);

                } else if (input.startsWith("mark")) {
                    if (input.length() <= 4 || input.substring(5).trim().isEmpty()) {
                        throw new MochiException(" Oi, which task to mark? Can give me number or not?");
                    }
                    int taskInd = Integer.parseInt(input.substring(5)) - 1;
                    if (taskInd < 0 || taskInd >= tasks.size()) {
                        throw new MochiException(" Babes, this task don't even exist?? How to mark?");
                    }
                    tasks.get(taskInd).markAsDone();
                    System.out.println(line);
                    System.out.println(" Wow. You actually did something, that's one down I guess. " +
                            "You are becoming a decent human being.");
                    System.out.println(" " + tasks.get(taskInd));
                    System.out.println(line);

                } else if (input.startsWith("unmark")) {
                    if (input.length() <= 6 || input.substring(7).trim().isEmpty()) {
                        throw new MochiException(" Great, now you want me to read your mind to unmark a task.");
                    }
                    int taskInd = Integer.parseInt(input.substring(7)) - 1;
                    if (taskInd < 0 || taskInd >= tasks.size()) {
                        throw new MochiException(" Babes, this task don't even exist?? How to unmark?");
                    }
                    tasks.get(taskInd).markAsNotDone();
                    System.out.println(line);
                    System.out.println(" Sigh, it was my fault to think you actually finished a task..");
                    System.out.println(" " + tasks.get(taskInd));
                    System.out.println(line);

                } else if (input.startsWith("todo")) {
                    if (input.length() <= 4) {
                        throw new MochiException(" Aigoo, you need description to make a todo sis!");
                    }
                    String desc = input.substring(5).trim();
                    if (desc.isEmpty()) {
                        throw new MochiException(" Aigoo, you need description to make a todo sis!");
                    }
                    Task task = new Todo(desc);
                    tasks.add(task);
                    System.out.println(line);
                    System.out.println(" Gotcha. I got add this task:");
                    System.out.println("  " + task);
                    System.out.println(" Now you got " + tasks.size() + " thingies to do.");
                    System.out.println(line);

                } else if (input.startsWith("deadline")) {
                    if (input.length() <= 8) {
                        throw new MochiException(" Yo, deadlines need description, and when to do '/by'.");
                    }
                    String[] p = input.substring(9).split(" /by ");
                    if (p.length < 2 || p[0].isEmpty() || p[1].isEmpty()) {
                        throw new MochiException(" Yo, deadlines need description, and when to do '/by'.");
                    }
                    Task task = new Deadline(p[0], p[1]);
                    tasks.add(task);
                    System.out.println(line);
                    System.out.println(" Gotcha. I got add this task:");
                    System.out.println("  " + task);
                    System.out.println(" Now you got " + tasks.size() + " tasks in the list.");
                    System.out.println(line);

                } else if (input.startsWith("event")) {
                    if (input.length() <= 5) {
                        throw new MochiException(" Babes, events need a description, a '/from' time and a '/to' time");
                    }
                    String[] p = input.substring(6).split("/from | /to");
                    if (p.length < 3 || p[0].isEmpty() || p[1].isEmpty() || p[2].isEmpty()) {
                        throw new MochiException(" Babes, events need a description, a '/from' time and a '/to' time");
                    }
                    Task task = new Event(p[0], p[1], p[2]);
                    tasks.add(task);
                    System.out.println(line);
                    System.out.println(" Gotcha. I got add this task:");
                    System.out.println("  " + task);
                    System.out.println(" Now you got " + tasks.size() + " tasks in the list.");
                    System.out.println(line);

                } else if (input.startsWith("delete")) {
                    if (input.length() <= 6 || input.substring(7).trim().isEmpty()) {
                        throw new MochiException(" Great, now you cant't even delete tasks.. " +
                                "Bro, tell me which task to delete.");
                    }
                    try {
                        int taskInd = Integer.parseInt(input.substring(7).trim()) - 1;
                        if (taskInd < 0 || taskInd >= tasks.size()) {
                            throw new MochiException(" Babes this task doesn't exist. Give me a task that exists.");
                        }
                        Task task = tasks.remove(taskInd);
                        System.out.println(line);
                        System.out.println(" Can. Take out task already.");
                        System.out.println(" " + task);
                        System.out.println(" Now you got " + tasks.size() + " tasks in the list.");
                        System.out.println(line);
                    } catch (NumberFormatException e) {
                        throw new MochiException(" Eh can give me valid number to delete or not?!");
                    }

                } else {
                    throw new MochiException(" Bro nani are you saying? Can you please make sense? Jebal!!");
                }
            }
            catch(MochiException e){
                    System.out.println(line);
                    System.out.println(" " + e.getMessage());
                    System.out.println(line);

            } catch(Exception e){
                System.out.println(line);
                System.out.println(" Life as your bot is a mess. you keep doing things like this: "
                            + e.getMessage());
                System.out.println(line);
            }
        }
            sc.close();
    }
}

class Task {
    protected String desc;
    protected boolean isDone;

    public Task(String desc) {
        this.desc = desc;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getStatus() {
        return (isDone ? "[X]" : "[ ]");
    }

    @Override
    public String toString() {
        return getStatus() + " " + desc;
    }
}

class Todo extends Task {
    public Todo(String desc) {
        super(desc);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

class Deadline extends Task {
    protected String by;

    public Deadline(String desc, String by) {
        super(desc);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

class Event extends Task {
    protected String from;
    protected String to;

    public Event(String desc, String from, String to) {
        super(desc);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

class MochiException extends Exception {
    public MochiException(String message) {
        super(message);
    }
}

