package Tests;

import Class.Task;
import Class.Epic;
import Class.Subtask;
import Class.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task1 = new Task(1, "Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(1, "Task 1", "Description 1", Status.NEW);

        assertEquals(task1, task2, "Tasks with same ID should be equal");
    }

    @Test
    void subtasksWithSameIdShouldBeEqual() {
        Subtask subtask1 = new Subtask(1, "Subtask 1", "Description 1", Status.NEW, 2);
        Subtask subtask2 = new Subtask(1, "Subtask 1", "Description 1", Status.NEW, 2);

        assertEquals(subtask1, subtask2, "Subtasks with same ID should be equal");
    }

    @Test
    void epicsWithSameIdShouldBeEqual() {
        Epic epic1 = new Epic(1, "Epic 1", "Description 1");
        Epic epic2 = new Epic(1, "Epic 1", "Description 1");

        assertEquals(epic1, epic2, "Epics with same ID should be equal");
    }
}
