package Entity;

import entity.Routine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoutineTest {
    private Routine routine;

    @BeforeEach
    void setUp () {
        routine = new Routine("1", "Leg Day");
    }

    @Test
    void testInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Routine(null, "Chest Day");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Routine("", "Chest Day");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Routine("  ", "Chest Day");
        });
    }

    @Test
    void testInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Routine("1", null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Routine("1", "");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Routine("1", "  ");
        });
    }

    @Test
    void testInvalidExercise() {
        assertThrows(IllegalArgumentException.class, () -> {
            routine.addExercise(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            routine.addExercise("");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            routine.addExercise("  ");
        });
    }

    @Test
    void testSecondConstructor() {
        List<String> list = List.of("Bench", "Fly's");
        Routine routine1 = new Routine("2", "Chest Day", list);
        assertEquals("2", routine1.getId());
        assertEquals("Chest Day", routine1.getName());
        assertEquals(list, routine1.getExercises());
    }

    @Test
    void testInvalidSecondConstructor() {
        List<String> list1 = new ArrayList<>();
        list1.add(null);

        List<String> list2 = List.of("");
        List<String> list3 = List.of("  ");

        assertThrows(IllegalArgumentException.class, () -> {
            new Routine("1", "Chest Day", list1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Routine("1", "Chest Day", list2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Routine("1", "Chest Day", list3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Routine("1", "Chest Day", null);
        });
    }

    @Test
    void testSetName() {
        routine.setName("Chest Day");
        assertEquals("Chest Day", routine.getName());
    }

    @Test
    void testSetInvalidName(){
        assertThrows(IllegalArgumentException.class, () -> {
            routine.setName(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            routine.setName("");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            routine.setName("  ");
        });
    }

    @Test
    void testConstructor() {
        assertEquals("1", routine.getId());
        assertEquals("Leg Day", routine.getName());
        assertTrue(routine.getExercises().isEmpty());
    }

    @Test
    void testAddExercise() {
        routine.addExercise("Squats");

        assertTrue(routine.getExercises().contains("Squats"));
    }

    @Test
    void testAddDuplicateExercise() {
        routine.addExercise("Squats");

        assertThrows(IllegalArgumentException.class, () -> {
            routine.addExercise("Squats");
        });
    }

    @Test
    void testRemoveExercise() {
        routine.addExercise("Squats");
        routine.removeExercise("Squats");

        assertTrue(routine.getExercises().isEmpty());
    }

    @Test
    void testRemoveNonexistentExercise() {
        assertThrows(IllegalArgumentException.class, () -> {
            routine.removeExercise("Squats");
        });
    }

    @Test
    void testGetExercisesIsCopy() {
        routine.addExercise("Squats");
        List<String> exercises2 = routine.getExercises();
        exercises2.add("Curls");
        assertEquals(List.of("Squats"), routine.getExercises());
    }

}
