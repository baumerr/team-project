package entity;

import java.util.List;
import java.util.ArrayList;

public class Routine {
    private final String id;
    private String name;
    private final List<String> exercises;

    public Routine(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Routine ID cannot be blank.");
        }

        this.id = id;
        setName(name);

        this.exercises = new ArrayList<>();
    }

    public Routine(String id, String name, List<String> exercises) {
        this(id, name);

        if (exercises == null) {
            throw new IllegalArgumentException("Exercise list cannot be null");
        }

        for (String exercise : exercises) {
            addExercise(exercise);
        }
    }

    public void addExercise(String exercise) {
        if (exercise == null || exercise.isBlank()) {
            throw new IllegalArgumentException("Exercise cannot be blank.");
        }
        if (this.exercises.contains(exercise)) {
            throw new IllegalArgumentException("Exercise already exists in routine.");
        }
        this.exercises.add(exercise);
    }

    public void removeExercise(String exercise) {
        if (!this.exercises.remove(exercise)) {
            throw new IllegalArgumentException("Exercise does not exist in routine.");
        }
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Routine name cannot be blank.");
        }
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getExercises() {
        return new ArrayList<>(this.exercises);
    }
}
