package mochi.command;

import mochi.MochiException;
import mochi.task.Deadline;
import mochi.task.Event;
import mochi.task.Todo;

/**
 * Parses user input and returns the corresponding command.
 */
public class Parser {

    /**
     * Parses a user command from input.
     * @param input The raw user input.
     * @return A command instance representing the user's input.
     * @throws MochiException MochiException if the input is invalid.
     */
    public static Command parse(String input) throws MochiException {
        String[] pts = input.split(" ", 2);
        switch (pts[0]) {
        case "bye":
            return new ByeCommand();
        case "list":
            return new ListCommand();
        case "mark":
            if (pts.length < 2) {
                throw new MochiException(" Bro, which task you want me to mark??");
            }
            try {
                int taskInd = Integer.parseInt(pts[1]);
                return new MarkCommand(taskInd);
            } catch (NumberFormatException e) {
                throw new MochiException(" Eh can give me a valid number to mark or not?!");
            }
        case "unmark":
            if (pts.length < 2) {
                throw new MochiException(" Great, now you want me to read your mind to unmark a task.");
            }
            try {
                int taskInd = Integer.parseInt(pts[1]);
                return new UnmarkCommand(taskInd);
            } catch (NumberFormatException e) {
                throw new MochiException(" Eh can give me a valid number to unmark or not?!");
            }
        case "todo":
            if (pts.length < 2) {
                throw new MochiException(" Aigoo, you need description to make todo sis!!");
            }
            return new AddCommand(new Todo(pts[1]));
        case "deadline":
            String[] deadlinePts = pts[1].split(" /by ");
            if (deadlinePts.length < 2) {
                throw new MochiException(" Yo, deadlines need description, and when to do '/by'.");
            }
            return new AddCommand(new Deadline(deadlinePts[0], deadlinePts[1]));
        case "event":
            String[] eventPts = pts[1].split("\\s*/from\\s*|\\s*/to\\s*");
            if (eventPts.length < 3) {
                throw new MochiException(" Babes, events need a description, a '/from' time and a '/to' time.");
            }
            return new AddCommand(new Event(eventPts[0], eventPts[1], eventPts[2]));
        case "delete":
            if (pts.length < 2) {
                throw new MochiException(" Oi you need tell me which number to delete??");
            }
            try {
                int taskInd = Integer.parseInt(pts[1]);
                return new DeleteCommand(taskInd);
            } catch (NumberFormatException e) {
                throw new MochiException(" Eh can give me a valid number to delete or not?!");
            }
        case "find":
            if (pts.length < 2) {
                throw new MochiException(" Oi, what you looking for bro??");
            }
            return new FindCommand(pts[1]);
        default:
            throw new MochiException(" Bro nani are you saying? Can you please make sense? Jebal!!");
        }
    }
}
