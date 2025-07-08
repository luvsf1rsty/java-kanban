import manager.TaskManager;
import task.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        Task task1 = new Task("Переезд", "Упаковать вещи", TaskStatus.NEW);
        Task task2 = new Task("Позвонить", "Позвонить бабушке", TaskStatus.NEW);
        manager.addTask(task1);
        manager.addTask(task2);

        Epic epic1 = new Epic("Организация свадьбы", "Все этапы");
        manager.addEpic(epic1);

        SubTask sub1 = new SubTask("Забронировать зал", "Позвонить в ресторан", TaskStatus.NEW, epic1.getId());
        SubTask sub2 = new SubTask("Найти фотографа", "Позвонить Сергею", TaskStatus.NEW, epic1.getId());
        manager.addSubtask(sub1);
        manager.addSubtask(sub2);

        Epic epic2 = new Epic("Покупка квартиры", "Все шаги до сделки");
        manager.addEpic(epic2);
        SubTask sub3 = new SubTask("Ипотека", "Подать заявку", TaskStatus.NEW, epic2.getId());
        manager.addSubtask(sub3);

        System.out.println("Задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());

        sub1.setStatus(TaskStatus.DONE);
        sub2.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateSubtask(sub1);
        manager.updateSubtask(sub2);

        System.out.println("После изменения статусов:");
        System.out.println("Эпики: " + manager.getAllEpics());

        manager.removeTaskById(task1.getId());
        manager.removeEpicById(epic2.getId());

        System.out.println("После удаления:");
        System.out.println("Задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());
    }
}
