import java.util.*;

public class TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, SubTask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private int nextId = 1;

    private int generateId() {
        return nextId++;
    }

    // Методы для задач
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void clearTasks() {
        tasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public void addTask(Task task) {
        task.id = generateId();
        tasks.put(task.id, task);
    }

    public void updateTask(Task task) {
        tasks.put(task.id, task);
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    // Методы для эпиков
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void clearEpics() {
        epics.clear();
        subtasks.clear();
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public void addEpic(Epic epic) {
        epic.id = generateId();
        epics.put(epic.id, epic);
    }

    public void updateEpic(Epic epic) {
        Epic existingEpic = epics.get(epic.id);
        if (existingEpic != null) {
            epic.getSubtaskIds().addAll(existingEpic.getSubtaskIds());
            epics.put(epic.id, epic);
            updateEpicStatus(epic);
        }
    }

    public void removeEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    // Методы для подзадач
    public List<SubTask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public List<SubTask> getSubtasksOfEpic(int epicId) {
        List<SubTask> result = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            for (int id : epic.getSubtaskIds()) {
                result.add(subtasks.get(id));
            }
        }
        return result;
    }

    public void addSubtask(SubTask subtask) {
        subtask.id = generateId();
        subtasks.put(subtask.id, subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtask(subtask.id);
            updateEpicStatus(epic);
        }
    }

    public void updateSubtask(SubTask subtask) {
        subtasks.put(subtask.id, subtask);
        updateEpicStatus(epics.get(subtask.getEpicId()));
    }

    public void removeSubtaskById(int id) {
        SubTask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(id);
                updateEpicStatus(epic);
            }
        }
    }

    private void updateEpicStatus(Epic epic) {
        List<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.status = TaskStatus.NEW;
            return;
        }

        boolean allNew = true;
        boolean allDone = true;
        for (int id : subtaskIds) {
            TaskStatus status = subtasks.get(id).status;
            if (status != TaskStatus.NEW) allNew = false;
            if (status != TaskStatus.DONE) allDone = false;
        }

        if (allDone) {
            epic.status = TaskStatus.DONE;
        } else if (allNew) {
            epic.status = TaskStatus.NEW;
        } else {
            epic.status = TaskStatus.IN_PROGRESS;
        }
    }
}
