import manager.Managers;
import manager.TaskManager;
import task.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        Task task1 = new Task("Переезд", "Упаковать вещи", Status.NEW);
        Task task2 = new Task("Позвонить", "Позвонить бабушке", Status.NEW);
        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic1 = new Epic("Организация свадьбы", "Все этапы");
        manager.createEpic(epic1);

        Subtask sub1 = new Subtask("Забронировать зал", "Позвонить в ресторан", Status.NEW, epic1.getId());
        Subtask sub2 = new Subtask("Найти фотографа", "Позвонить Сергею", Status.NEW, epic1.getId());
        manager.createSubtask(sub1);
        manager.createSubtask(sub2);

        Epic epic2 = new Epic("Покупка квартиры", "Все шаги до сделки");
        manager.createEpic(epic2);
        Subtask sub3 = new Subtask("Ипотека", "Подать заявку", Status.NEW, epic2.getId());
        manager.createSubtask(sub3);

        System.out.println("Задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());

        sub1.setStatus(Status.DONE);
        sub2.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(sub1);
        manager.updateSubtask(sub2);

        System.out.println("После изменения статусов:");
        System.out.println("Эпики: " + manager.getAllEpics());

        manager.deleteTaskById(task1.getId());
        manager.deleteEpicById(epic2.getId());

        System.out.println("После удаления:");
        System.out.println("Задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());
    }
}
