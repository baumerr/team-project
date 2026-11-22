package use_case.routine.create;

import entity.Routine;
import use_case.routine.RoutineRepository;

import java.util.UUID;

public class CreateRoutineInteractor implements CreateRoutineInputBoundary{

    private final RoutineRepository repository;
    private final CreateRoutineOutputBoundary outputBoundary;

    public CreateRoutineInteractor(
            RoutineRepository repository,
            CreateRoutineOutputBoundary outputBoundary) {
        this.repository = repository;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute (CreateRoutineInputData inputData) {
        String id = UUID.randomUUID().toString();

        String name = inputData.getName();
        if(repository.findByName(name) != null) {
            outputBoundary.prepareFailView("A routine with this name already exists.");
            return;
        }

        if (name == null || name.isBlank()) {
            outputBoundary.prepareFailView("Routine name cannot be blank.");
            return;
        }

        Routine routine = new Routine(id, inputData.getName());
        repository.save(routine);

        CreateRoutineOutputData outputData = new CreateRoutineOutputData(id, name);
        outputBoundary.prepareSuccessView(outputData);
    }
}
