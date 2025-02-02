package mochi.task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import mochi.storage.Storage;
import java.io.IOException;
import mochi.ui.Ui;

public class TaskListTest {
    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() throws IOException {
        taskList = new TaskList(new ArrayList<>());
        ui = new Ui();
        storage = new Storage("test.txt");
    }

    @Test
    void testAddTodoTask() throws IOException {
        Task task = new Todo("Read book");
        taskList.addTask(task, ui, storage);
        assertEquals(1, taskList.getTasks().size());
        assertEquals(" [T][ ] Read book", taskList.getTasks().get(0).toString());
    }
}
