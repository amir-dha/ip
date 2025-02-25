package mochi.task;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mochi.storage.Storage;
import mochi.ui.Ui;

public class TaskListTest {
    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() {
        taskList = new TaskList(new ArrayList<>());
        ui = new Ui();
        storage = new Storage("data/mochi-test.txt");
    }


    @Test
    void testAddTodoTask() throws IOException {
        Task task = new Todo("Read book");
        taskList.addTask(task, ui, storage);

        assert taskList.getTasks().size() == 1 : "TaskList size should be 1 after adding a task";
        assert taskList.getTasks().get(0).toString().equals(" [T][ ] Read book")
                : "Added task should match expected format";
    }

}
