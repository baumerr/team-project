package use_case.routine.create;

public class TestCreateRoutinePresenter  implements CreateRoutineOutputBoundary{
    boolean successCalled;
    boolean failureCalled;
    CreateRoutineOutputData receivedOutputData;

    @Override
    public void prepareSuccessView(CreateRoutineOutputData outputData) {
        successCalled = true;
        this.receivedOutputData = outputData;
    }

    @Override
    public void prepareFailView(String error) {
        failureCalled = true;
    }
}
