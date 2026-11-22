package data_access;

import entity.Routine;
import use_case.routine.RoutineRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRoutineRepository implements RoutineRepository {
    private final Map<String, Routine> routines = new HashMap<>();

    @Override
    public void save(Routine routine) {
        routines.put(routine.getId(), routine);
    }

    @Override
    public Routine findById(String id) {
        return routines.get(id);
    }

    @Override
    public Routine findByName(String name) {
        for (Routine routine: routines.values()) {
            if (routine.getName().equalsIgnoreCase(name)) {
                return routine;
            }
        }
        return null;
    }

    @Override
    public List<Routine> findAll() {
        return new ArrayList<>(routines.values());
    }
}
