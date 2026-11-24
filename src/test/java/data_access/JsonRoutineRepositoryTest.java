package data_access;

import entity.Routine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonRoutineRepositoryTest {
    @TempDir
    Path tempDir;

    private Path filePath;
    private JsonRoutineRepository repository;

    @BeforeEach
    void setup() {
        filePath = tempDir.resolve("routines.json");
        repository = new JsonRoutineRepository(filePath);
    }

    @Test
    void testCreatesJsonFile() {
        assertTrue(Files.exists(filePath));
    }

    @Test
    void testFindById() {
        Routine routine1 = new Routine("1", "Chest Day");
        repository.save(routine1);
        Routine routine2 = new Routine("2", "Leg Day");
        repository.save(routine2);

        Routine loadedRoutine1 = repository.findById("1");
        assertNotNull(loadedRoutine1);

        Routine loadedRoutine2 = repository.findById("2");
        assertNotNull(loadedRoutine2);

        assertEquals(routine1.getId(), loadedRoutine1.getId());
        assertEquals(routine1.getName(), loadedRoutine1.getName());

        assertEquals(routine2.getId(), loadedRoutine2.getId());
        assertEquals(routine2.getName(), loadedRoutine2.getName());

        assertNull(repository.findById("3"));
    }

    @Test
    void testFindByName() {
        Routine routine1 = new Routine("1", "Chest Day");
        repository.save(routine1);
        Routine routine2 = new Routine("2", "Leg Day");
        repository.save(routine2);

        Routine loadedRoutine1 = repository.findByName("chest day");
        assertNotNull(loadedRoutine1);

        Routine loadedRoutine2 = repository.findByName("Leg Day");
        assertNotNull(loadedRoutine2);

        assertEquals(routine1.getId(), loadedRoutine1.getId());
        assertEquals(routine1.getName(), loadedRoutine1.getName());

        assertEquals(routine2.getId(), loadedRoutine2.getId());
        assertEquals(routine2.getName(), loadedRoutine2.getName());

        assertNull(repository.findByName("Shoulder Day"));
    }

    @Test
    void testFindAll() {
        assertEquals(0, repository.findAll().size());

        Routine routine1 = new Routine("1", "Chest Day");
        repository.save(routine1);
        Routine routine2 = new Routine("2", "Leg Day");
        repository.save(routine2);

        List<Routine> loadedRoutines = repository.findAll();

        assertEquals(2, loadedRoutines.size());

        assertEquals(routine1.getId(), loadedRoutines.get(0).getId());
        assertEquals(routine1.getName(), loadedRoutines.get(0).getName());

        assertEquals(routine2.getId(), loadedRoutines.get(1).getId());
        assertEquals(routine2.getName(), loadedRoutines.get(1).getName());

    }

    @Test
    void testSaveRoutine() {
        Routine savedRoutine = new Routine("1", "Chest Day");
        savedRoutine.addExercise("Bench Press");
        
        repository.save(savedRoutine);

        Routine loadedRoutine = repository.findById("1");
        assertNotNull(loadedRoutine);
        
        assertEquals(savedRoutine.getId(), loadedRoutine.getId());
        assertEquals(savedRoutine.getName(), loadedRoutine.getName());
        assertEquals(savedRoutine.getExercises(), loadedRoutine.getExercises());
    }

    @Test
    void testUpdateRoutine() {
        Routine routine = new Routine("1", "Chest Day");
        repository.save(routine);

        routine.addExercise("Bench Press");
        repository.save(routine);

        assertEquals(1, repository.findAll().size());

        List<Routine> routines = repository.findAll();
        assertEquals(List.of("Bench Press"), routines.get(0).getExercises());
    }

    @Test
    void testPersistence() {
        Routine routine = new Routine("1", "Chest Day");
        routine.addExercise("Bench Press");
        repository.save(routine);

        JsonRoutineRepository repository2 = new JsonRoutineRepository(filePath);

        Routine loadedRoutine = repository2.findById("1");
        assertNotNull(loadedRoutine);

        assertEquals(routine.getId(), loadedRoutine.getId());
        assertEquals(routine.getName(), loadedRoutine.getName());
        assertEquals(routine.getExercises(), loadedRoutine.getExercises());
    }

}
