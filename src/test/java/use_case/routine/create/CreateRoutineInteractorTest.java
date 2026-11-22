package use_case.routine.create;

import data_access.InMemoryRoutineRepository;
import entity.Routine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateRoutineInteractorTest {
    private InMemoryRoutineRepository repository;
    private CreateRoutineInteractor interactor;
    private TestCreateRoutinePresenter outputBoundary;

    @BeforeEach
    void setup() {
        repository = new InMemoryRoutineRepository();
        outputBoundary = new TestCreateRoutinePresenter();
        interactor = new CreateRoutineInteractor(repository, outputBoundary);
    }

    @Test
    void testCreateRoutine (){
        CreateRoutineInputData inputData = new CreateRoutineInputData("Chest Day");

        interactor.execute(inputData);

        Routine savedRoutine = repository.findByName("Chest Day");

        assertTrue(outputBoundary.successCalled);
        assertFalse(outputBoundary.failureCalled);
        assertEquals(savedRoutine.getId(), outputBoundary.receivedOutputData.getId());
        assertEquals(savedRoutine.getName(), outputBoundary.receivedOutputData.getName());

        assertNotNull(savedRoutine);
        assertEquals("Chest Day", savedRoutine.getName());
        assertNotNull(savedRoutine.getId());
        assertTrue(savedRoutine.getExercises().isEmpty());
    }

    @Test
    void testDuplicateRoutineNames() {
        CreateRoutineInputData inputData1 = new CreateRoutineInputData("Chest Day");
        CreateRoutineInputData inputData2 = new CreateRoutineInputData("Chest Day");

        interactor.execute(inputData1);

        interactor.execute(inputData2);
        assertTrue(outputBoundary.failureCalled);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testInvalidRoutineNames() {
        CreateRoutineInputData inputData1 = new CreateRoutineInputData(null);
        CreateRoutineInputData inputData2 = new CreateRoutineInputData("");
        CreateRoutineInputData inputData3 = new CreateRoutineInputData("  ");

        interactor.execute(inputData1);
        assertTrue(outputBoundary.failureCalled);

        outputBoundary.failureCalled = false;

        interactor.execute(inputData2);
        assertTrue(outputBoundary.failureCalled);

        outputBoundary.failureCalled = false;

        interactor.execute(inputData3);
        assertTrue(outputBoundary.failureCalled);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void testCaseInsensitiveRoutineName() {
        CreateRoutineInputData inputData1 = new CreateRoutineInputData("Chest Day");
        CreateRoutineInputData inputData2 = new CreateRoutineInputData("chest day");

        interactor.execute(inputData1);

        interactor.execute(inputData2);
        assertTrue(outputBoundary.failureCalled);
    }
}
