package Tests;

import Class.Epic;
import Class.Subtask;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void shouldNotAddEpicAsItsOwnSubtask() {
        Epic epic = new Epic(1, "Epic", "Description");

        assertThrows(IllegalArgumentException.class, () -> epic.addSubtaskId(epic.getId()),
                "Epic should not allow itself as a subtask");
    }

    @Test
    void shouldNotMakeSubtaskItsOwnEpic() {
        Subtask subtask = new Subtask(1, "Subtask", "Description", Class.Status.NEW, 1);

        assertNotEquals(subtask.getId(), subtask.getEpicId(),
                "Subtask should not have itself as its Epic");
    }

    static class EpicTest {

    }
}
