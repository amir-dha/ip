package mochi;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mochi.storage.Storage;
import mochi.task.TaskList;
import mochi.task.Todo;
import mochi.ui.Ui;

public class MochiTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Mochi mochi;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        mochi = new Mochi("./data/mochi-test.txt");
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testAddTodo() {
        String response = mochi.getResponse("todo read book");
        assertTrue(response.contains("[T][ ] read book"));
    }

    @Test
    void testAddDeadline() {
        String response = mochi.getResponse("deadline finish assignment /by 2024-07-02 1800");
        assertTrue(response.contains("[D][ ] finish assignment"));
    }

    @Test
    void testAddEvent() {
        String response = mochi.getResponse("event party /from 2024-07-02 1800 /to 2024-07-02 2300");
        assertTrue(response.contains("[E][ ] party"));
    }

    @Test
    void testMarkAsDone() throws IOException, MochiException { // Add `MochiException`
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");

        Todo task = new Todo("Test marking");
        tasks.addTask(task, ui, storage);

        assertFalse(task.isTaskDone(), "Task should be initially not done.");

        tasks.markTask(1, ui, storage);

        assertTrue(task.isTaskDone(), "Task should be marked as done.");
    }

    @Test
    void testUnmarkAsDone() throws IOException, MochiException { // Add `MochiException`
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test.txt");

        Todo task = new Todo("Test unmarking");
        tasks.addTask(task, ui, storage);

        tasks.markTask(1, ui, storage);
        assertTrue(task.isTaskDone(), "Task should be marked as done before unmarking.");

        tasks.unmarkTask(1, ui, storage);
        assertFalse(task.isTaskDone(), "Task should be marked as not done after unmarking.");
    }
}
