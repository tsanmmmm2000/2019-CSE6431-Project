package restaurant.machine.impl;
import restaurant.*;

public class CokeMachine extends BaseMachine {

    private final int MakingCokeTime = 1000;

    @Override
    public int getMakingTime() {
        return MakingCokeTime;
    }

    @Override
    public String getName() {
        return Utility.Coke;
    }
}