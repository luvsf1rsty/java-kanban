package gymmaster;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(day);
        dayMap.computeIfAbsent(time, k -> new ArrayList<>()).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        List<TrainingSession> result = new ArrayList<>();
        for (TimeOfDay time : dayMap.navigableKeySet()) {
            result.addAll(dayMap.get(time));
        }
        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        List<TrainingSession> sessions = dayMap.get(timeOfDay);
        if (sessions == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(sessions);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> counters = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> dayMap : timetable.values()) {
            for (List<TrainingSession> sessions : dayMap.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    counters.merge(coach, 1, Integer::sum);
                }
            }
        }
        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : counters.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        result.sort((a, b) -> Integer.compare(b.getCount(), a.getCount()));
        return result;
    }
}
