import java.util.ArrayList;
import java.util.Scanner;

public class Mochi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = "------------------------------------------------";
        String botName = "Mochi";
        ArrayList<Task> tasks = new ArrayList<>();


        System.out.println(" " + line);
        System.out.println(" It's you again.. " + botName + " at your service miserably.");
        System.out.println(" What you want?");
        System.out.println(" " + line);

        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println(" " + line);
                System.out.println(" " + "Bye. Sayonara. Be gone.");
                System.out.println(" " + line);
                break;

            } else if (input.equals("list")) {
                System.out.println(" " + line);
                System.out.println(" Look at the consequences of your procrastination:");
                for (int i = 0; i < tasks.size() ; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                }
                System.out.println(" " + line);

            } else if (input.startsWith("mark ")) {
                int taskInd = Integer.parseInt(input.substring(5)) - 1;
                tasks.get(taskInd).markAsDone();
                System.out.println(" " + line);
                System.out.println(" Wow. You actually did something, that's one down I guess. " +
                        "You are becoming a decent human being.");
                System.out.println(" " + tasks.get(taskInd));
                System.out.println(" " + line);

            } else if (input.startsWith("unmark ")) {
                int taskInd = Integer.parseInt(input.substring(7)) - 1;
                tasks.get(taskInd).markAsNotDone();
                System.out.println(" " + line);
                System.out.println("Sigh, it was my fault to think you actually finished a task..");
                System.out.println(" " + tasks.get(taskInd));
                System.out.println(" " + line);

            } else {
                tasks.add(new Task(input));
                System.out.println(" " + line);
                System.out.println(" I got add: " + input + ". You are welcome.");
                System.out.println(" " + line);
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
        return (isDone ? "[X]" :"[ ]");
    }

    @Override
    public String toString() {
        return getStatus() + " " + desc;
    }
}
