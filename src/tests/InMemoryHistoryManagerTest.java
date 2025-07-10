package tests;

import Class.HistoryManager;
import Class.Managers;
import Class.Task;
import Class.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void shouldAddTasksToHistory() {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "History should contain one task");
        assertEquals(task, history.get(0), "History should contain the added task");
    }

    @Test
    void shouldRetainPreviousVersionOfTaskInHistory() {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        historyManager.add(task);

        task.setStatus(Status.DONE); // Modify task after adding to history

        List<Task> history = historyManager.getHistory();
        assertEquals(Status.NEW, history.get(0).getStatus(),
                "History should retain the previous version of the task");
    }

    @Test
    void shouldLimitHistoryToTenTasks() {
        for (int i = 1; i <= 11; i++) {
            Task task = new Task("Task " + i, "Description " + i, Status.NEW);
            historyManager.add(task);
        }

        List<Task> history = historyManager.getHistory();
        assertEquals(10, history.size(), "History should contain only 10 tasks");
        assertEquals("Task 2", history.get(0).getTitle(), "Oldest task in history should be 'Task 2'");
    }
}
