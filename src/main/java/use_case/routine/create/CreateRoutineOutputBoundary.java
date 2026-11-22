package use_case.routine.create;

public interface CreateRoutineOutputBoundary {
    void prepareSuccessView(CreateRoutineOutputData outputData);
    void prepareFailView(String error);
}
