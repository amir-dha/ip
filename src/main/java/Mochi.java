import java.util.Scanner;

public class Mochi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = "------------------------------------------------";
        String botName = "Mochi";
        System.out.println(" " + line);
        System.out.println(" Hello! I'm " + botName);
        System.out.println(" What can I do for you?");
        System.out.println(" " + line);

        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println(" " + line);
                System.out.println(" " + "Bye. Hope to see you again soon!");
                System.out.println(" " + line);
                break;
            }
            System.out.println(" " + line);
            System.out.println(" " + input);
            System.out.println(" " + line);
        }
        sc.close();
    }
}
