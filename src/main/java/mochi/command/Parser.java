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
        if (input == null || input.trim().isEmpty()) {
            throw new MochiException("Input cannot be empty, eh?? Please enter a valid command, bro.");
        }

        String[] parts = input.trim().split(" ", 2);
        String command = parts[0].toLowerCase();

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
        case "sort":
            return handleSort(parts);
        default:
            throw new MochiException("Babes that is an unknown command. Try again lah!");
        }
    }

    /**
     * Handles the "find" command, ensuring a keyword is provided.
     */
    private static Command handleFind(String[] parts) throws MochiException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MochiException("What you searching for? Provide a search keyword lah.");
        }
        return new FindCommand(parts[1].trim());
    }

    /**
     * Handles the "delete" command, ensuring a valid task number is provided.
     */
    private static Command handleDelete(String[] parts) throws MochiException {
        return parseTaskIndex(parts, "delete");
    }

    /**
     * Handles the "mark" and "unmark" commands, ensuring a valid task number is provided.
     */
    private static Command handleMarkUnmark(String[] parts, boolean isMark) throws MochiException {
        return parseTaskIndex(parts, isMark ? "mark" : "unmark");
    }

    /**
     * Handles the "todo" command, ensuring a description is provided.
     */
    private static Command handleTodo(String[] parts) throws MochiException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MochiException("Todo requires a description babes.");
        }
        return new AddCommand(new Todo(parts[1].trim()));
    }

    /**
     * Handles the "deadline" command, ensuring both description and date/time are provided.
     */
    private static Command handleDeadline(String[] parts) throws MochiException {
        if (parts.length < 2) {
            throw new MochiException("Oi, deadline need description and a '/by' date. What you doing?");
        }
        if (!parts[1].contains("/by")) {
            throw new MochiException("Oi, deadline missing '/by' keyword lah.");
        }

        String[] deadlineParts = parts[1].split(" /by ", 2);

        if (deadlineParts.length < 2) {
            throw new MochiException("Eh deadline missing date/time after '/by'. "
                    + "Format: deadline <description> /by <date/time>.");
        }

        if (deadlineParts[0].trim().isEmpty() && deadlineParts[1].trim().isEmpty()) {
            throw new MochiException("This is just vibes, where is the description and the date/time?");
        }

        if (deadlineParts[0].trim().isEmpty()) {
            throw new MochiException("Oi where's the description? You can't just give a date lah.");
        }

        if (deadlineParts[1].trim().isEmpty()) {
            throw new MochiException("Deadline missing date/time after '/by'. "
                    + "Format: deadline <description> /by <date/time>.");
        }

        return new AddCommand(new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim()));
    }

    /**
     * Handles the "event" command, ensuring description, start time, and end time are provided.
     */
    private static Command handleEvent(String[] parts) throws MochiException {
        if (parts.length < 2) {
            throw new MochiException("Oi event need description, a '/from' time, and a '/to' time. "
                    + "Why even use this app?");
        }
        if (!parts[1].contains("/from")) {
            throw new MochiException("Aiyo missing '/from' lah. "
                    + "Format: event <description> /from <start time> /to <end time>.");
        }
        if (!parts[1].contains("/to")) {
            throw new MochiException("Babes where is '/to'? "
                    + "Format: event <description> /from <start time> /to <end time>.");
        }

        String[] eventParts = parts[1].split("\\s*/from\\s*|\\s*/to\\s*", 3);

        if (eventParts.length < 3) {
            throw new MochiException("Eh event format is wrong. "
                    + "Format: event <description> /from <start time> /to <end time>.");
        }

        if (eventParts[0].trim().isEmpty()) {
            throw new MochiException("Oi event need a description. You want an event with no name?");
        }

        if (eventParts[1].trim().isEmpty()) {
            throw new MochiException("Where's the start time? "
                    + "Format: event <description> /from <start time> /to <end time>.");
        }

        if (eventParts[2].trim().isEmpty()) {
            throw new MochiException("Missing end time lah. "
                    + "Format: event <description> /from <start time> /to <end time>.");
        }

        return new AddCommand(new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim()));
    }

    private static Command handleSort(String[] parts) throws MochiException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MochiException("Sort what? Specify format: sort todos | sort deadlines | sort events");
        }

        String category = parts[1].trim().toLowerCase();

        if (!category.equals("todos") && !category.equals("deadlines") && !category.equals("events")) {
            throw new MochiException("No such category to sort lah! Use: sort todos | sort deadlines | sort events.");
        }
        return new SortCommand(category);
    }

    /**
     * Parses a task index and ensures it is valid.
     */
    private static Command parseTaskIndex(String[] parts, String action) throws MochiException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MochiException("Oi which task number? You must say task number to " + action + ".");
        }
        try {
            int taskIndex = Integer.parseInt(parts[1].trim());
            if (taskIndex < 1) {
                throw new MochiException("Only you can have a negative task number.");
            }
            return switch (action) {
            case "delete" -> new DeleteCommand(taskIndex);
            case "mark" -> new MarkCommand(taskIndex);
            case "unmark" -> new UnmarkCommand(taskIndex);
            default -> throw new MochiException("Invalid move, what you doing ah?");
            };
        } catch (NumberFormatException e) {
            throw new MochiException("Babes, that's not a real task number, what you doing??");
        }
    }
}
