package mochi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void testMarkAsDone() {
        mochi.getResponse("todo read book"); // Add a task first
        String response = mochi.getResponse("mark 1");
        assertTrue(response.contains("[T][X] read book"));
    }

    @Test
    void testUnmarkAsDone() {
        mochi.getResponse("todo read book"); // Add a task first
        mochi.getResponse("mark 1"); // Mark it
        String response = mochi.getResponse("unmark 1");
        assertTrue(response.contains("[T][ ] read book"));
    }
}
