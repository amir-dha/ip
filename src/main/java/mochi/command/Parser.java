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
     *
     * @param input The raw user input.
     * @return A command instance representing the user's input.
     * @throws MochiException MochiException if the input is invalid.
     */
    public static Command parse(String input) throws MochiException {
        String[] parts = input.trim().split(" ", 2);
        String command = parts[0];

        switch (command) {
        case "bye":
            return new ByeCommand();
        case "list":
            return new ListCommand();
        case "find":
            return handleFind(parts);
        case "delete":
            return handleDelete(parts);
        case "mark":
            return handleMarkUnmark(parts, true);
        case "unmark":
            return handleMarkUnmark(parts, false);
        case "todo":
            return handleTodo(parts);
        case "deadline":
            return handleDeadline(parts);
        case "event":
            return handleEvent(parts);
        default:
            throw new MochiException(" Unknown command. Please try again!");
        }
    }

    private static Command handleFind(String[] parts) throws MochiException {
        if (parts.length < 2) {
            throw new MochiException(" Please provide a search keyword.");
        }
        return new FindCommand(parts[1]);
    }

    private static Command handleDelete(String[] parts) throws MochiException {
        return parseTaskIndex(parts, "delete");
    }

    private static Command handleMarkUnmark(String[] parts, boolean isMark) throws MochiException {
        return parseTaskIndex(parts, isMark ? "mark" : "unmark");
    }

    private static Command handleTodo(String[] parts) throws MochiException {
        if (parts.length < 2) {
            throw new MochiException(" Todo requires a description.");
        }
        return new AddCommand(new Todo(parts[1]));
    }

    private static Command handleDeadline(String[] parts) throws MochiException {
        String[] deadlineParts = parts[1].split(" /by ");
        if (deadlineParts.length < 2) {
            throw new MochiException(" Deadline needs a description and '/by' date.");
        }
        return new AddCommand(new Deadline(deadlineParts[0], deadlineParts[1]));
    }

    private static Command handleEvent(String[] parts) throws MochiException {
        String[] eventParts = parts[1].split("\\s*/from\\s*|\\s*/to\\s*");
        if (eventParts.length < 3) {
            throw new MochiException(" Event requires description, '/from' time, and '/to' time.");
        }
        return new AddCommand(new Event(eventParts[0], eventParts[1], eventParts[2]));
    }

    private static Command parseTaskIndex(String[] parts, String action) throws MochiException {
        if (parts.length < 2) {
            throw new MochiException(" Please specify a task number to " + action + ".");
        }
        try {
            int taskIndex = Integer.parseInt(parts[1]);
            return switch (action) {
            case "delete" -> new DeleteCommand(taskIndex);
            case "mark" -> new MarkCommand(taskIndex);
            case "unmark" -> new UnmarkCommand(taskIndex);
            default -> throw new MochiException(" Invalid action.");
            };
        } catch (NumberFormatException e) {
            throw new MochiException(" Please enter a valid task number.");
        }
    }
}
