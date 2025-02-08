package mochi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




public class MochiTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private void simulateInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    @BeforeEach
    void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testAddTodo() {
        simulateInput("todo read book\nlist\nbye\n");
        Mochi.main(new String[]{});
        assertTrue(outContent.toString().contains(" [T][ ] read book"));
    }

    @Test
    void testAddDeadline() {
        simulateInput("deadline finish assignment /by 2024-07-02 1800\nlist\nbye\n");
        Mochi.main(new String[]{});
        assertTrue(outContent.toString().contains(" [D][ ] finish assignment (by: 2 Jul 2024, 6:00 PM)"));
    }

    @Test
    void testAddEvent() {
        simulateInput("event party /from 2024-07-02 1800 /to 2024-07-02 2300\nlist\nbye\n");
        Mochi.main(new String[]{});
        assertTrue(outContent.toString()
                .contains(" [E][ ] party (from: 2 Jul 2024, 6:00 PM to: 2 Jul 2024, 11:00 PM)"));
    }

    @Test
    void testMarkAsDone() {
        simulateInput("mark 1\nlist\nbye\n");
        Mochi.main(new String[]{});
        assertTrue(outContent.toString().contains(" [T][X] read book"));
    }

    @Test
    void testUnmarkAsDone() {
        simulateInput("unmark 1\nlist\nbye\n");
        Mochi.main(new String[]{});
        assertTrue(outContent.toString().contains(" [T][ ] read book"));
    }
}
