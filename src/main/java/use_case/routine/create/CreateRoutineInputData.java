package use_case.routine.create;

public class CreateRoutineInputData {
    private final String name;

    public CreateRoutineInputData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
