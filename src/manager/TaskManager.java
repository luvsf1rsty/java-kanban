package manager;

import task.*;

import java.util.List;

public interface TaskManager {
    List<Task> getAllTasks();
    void deleteAllTasks();
    Task getTaskById(int id);
    void createTask(Task task);
    void updateTask(Task task);
    void deleteTaskById(int id);

    List<Epic> getAllEpics();
    void deleteAllEpics();
    Epic getEpicById(int id);
    void createEpic(Epic epic);
    void updateEpic(Epic epic);
    void deleteEpicById(int id);

    List<Subtask> getAllSubtasks();
    void deleteAllSubtasks();
    Subtask getSubtaskById(int id);
    void createSubtask(Subtask subtask);
    void updateSubtask(Subtask subtask);
    void deleteSubtaskById(int id);

    List<Task> getHistory();

    List<Subtask> getSubtasksForEpic(int epicId);
}
