package restaurant.machine.impl;
import restaurant.*;

public class FriesMachine extends BaseMachine {

    private final int MakingFriesTime = 3000;

    @Override
    public int getMakingTime() {
        return MakingFriesTime;
    }

    @Override
    public String getName() {
        return Utility.Fries;
    }
}