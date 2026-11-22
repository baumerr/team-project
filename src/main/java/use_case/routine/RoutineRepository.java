package use_case.routine;

import entity.Routine;

import java.util.List;

public interface RoutineRepository {
    void save(Routine routine);

    Routine findById(String id);

    Routine findByName(String name);

    List<Routine> findAll();
}
