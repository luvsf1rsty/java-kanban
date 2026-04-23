package gymmaster;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, monday.size());
        assertSame(singleTrainingSession, monday.get(0));

        List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesday.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, monday.size());

        List<TrainingSession> thursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursday.size());
        assertEquals(new TimeOfDay(13, 0), thursday.get(0).getTimeOfDay());
        assertEquals(new TimeOfDay(20, 0), thursday.get(1).getTimeOfDay());

        List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesday.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> at13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(1, at13.size());
        assertSame(singleTrainingSession, at13.get(0));

        List<TrainingSession> at14 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertTrue(at14.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeTwoSessionsSameTime() {
        Timetable timetable = new Timetable();

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession s1 = new TrainingSession(groupChild, coach, DayOfWeek.TUESDAY, new TimeOfDay(18, 0));
        TrainingSession s2 = new TrainingSession(groupAdult, coach, DayOfWeek.TUESDAY, new TimeOfDay(18, 0));

        timetable.addNewTrainingSession(s1);
        timetable.addNewTrainingSession(s2);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.TUESDAY, new TimeOfDay(18, 0));
        assertEquals(2, sessions.size());
    }

    @Test
    void testGetCountByCoachesSortedDescending() {
        Timetable timetable = new Timetable();

        Coach coachIzmailov = new Coach("Измайлов", "Константин", "Витальевич");
        Coach coachSemenov  = new Coach("Семёнов",  "Виталий",    "Константинович");

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coachIzmailov,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coachIzmailov,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coachIzmailov,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coachSemenov,
                DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertEquals(2, result.size());
        assertEquals(coachIzmailov, result.get(0).getCoach());
        assertEquals(3, result.get(0).getCount());
        assertEquals(coachSemenov, result.get(1).getCoach());
        assertEquals(1, result.get(1).getCount());
    }

    @Test
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();
        List<CounterOfTrainings> result = timetable.getCountByCoaches();
        assertTrue(result.isEmpty());
    }
}
