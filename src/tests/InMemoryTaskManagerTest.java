package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
    }

    @Test
    void shouldAddAndFindTasksById() {
        Task task = new Task("Class.Task", "Description", Status.NEW);
        Epic epic = new Epic("Class.Class.Epic", "Class.Class.Epic Description");
        Subtask subtask = new Subtask("Class.Subtask", "Class.Subtask Description", Status.NEW, epic.getId());

        taskManager.createTask(task);
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask);

        assertEquals(task, taskManager.getTaskById(task.getId()), "Class.Task should be retrievable by ID");
        assertEquals(epic, taskManager.getEpicById(epic.getId()), "Class.Class.Epic should be retrievable by ID");
        assertEquals(subtask, taskManager.getSubtaskById(subtask.getId()), "Class.Subtask should be retrievable by ID");
    }

    @Test
    void shouldHandleTaskWithGeneratedAndSetIds() {
        Task task1 = new Task("Class.Task 1", "Description", Status.NEW);
        Task task2 = new Task(1, "Class.Task 2", "Another Description", Status.NEW);

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        assertEquals(2, taskManager.getAllTasks().size(), "Tasks should not conflict by ID");
    }

    @Test
    void shouldMaintainTaskImmutabilityAfterAdding() {
        Task task = new Task("Immutable Class.Task", "Description", Status.NEW);
        taskManager.createTask(task);

        Task retrievedTask = taskManager.getTaskById(task.getId());
        assertEquals("Immutable Class.Task", retrievedTask.getTitle(), "Class.Task title should remain unchanged");
        assertEquals("Description", retrievedTask.getDescription(), "Class.Task description should remain unchanged");
        assertEquals(Status.NEW, retrievedTask.getStatus(), "Class.Task status should remain unchanged");
    }
}
