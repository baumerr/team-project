package data_access;

import com.google.gson.*;

import entity.Routine;
import use_case.routine.RoutineRepository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

public class JsonRoutineRepository implements RoutineRepository {

    private final Gson gson;
    private final Path filePath;

    public JsonRoutineRepository(Path filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            Path parent = filePath.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            if (Files.notExists(filePath)) {
                Files.writeString(filePath, "[]");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void save(Routine routine) {
        List<Routine> routines = readRoutines();

        for (int i = 0; i < routines.size(); i++) {
            if (routines.get(i).getId().equals(routine.getId())) {
                routines.set(i, routine);
                writeRoutines(routines);
                return;
            }
        }
        routines.add(routine);
        writeRoutines(routines);
    }

    @Override
    public Routine findById(String id) {
        List<Routine> routines = readRoutines();
        for (Routine routine : routines) {
            if (routine.getId().equals(id)) {
                return routine;
            }
        }
        return null;
    }

    @Override
    public Routine findByName(String name) {
        List<Routine> routines = readRoutines();

        for (Routine routine: routines) {
            if (routine.getName().equalsIgnoreCase(name)) {
                return routine;
            }
        }
        return null;
    }

    @Override
    public List<Routine> findAll() {
        return readRoutines();
    }

    private List<Routine> readRoutines() {
        try {
            String json = Files.readString(filePath);
            JsonArray routinesArray = JsonParser.parseString(json).getAsJsonArray();

            List<Routine> routines = new ArrayList<>();

            for (JsonElement element : routinesArray) {
                Routine routine = getRoutine(element);
                routines.add(routine);
            }

            return routines;

        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read routines.", e);
        }
    }

    private static Routine getRoutine(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        String id = object.get("id").getAsString();
        String name = object.get("name").getAsString();

        List<String> exercises = new ArrayList<>();

        JsonArray exerciseArray = object.getAsJsonArray("exercises");

        for (JsonElement exercise : exerciseArray) {
            exercises.add(exercise.getAsString());
        }

        return new Routine(id, name, exercises);
    }

    private void writeRoutines(List<Routine> routines) {
        String json = gson.toJson(routines);

        try {
            Files.writeString(filePath, json);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write routines.", e);
        }
    }
}
