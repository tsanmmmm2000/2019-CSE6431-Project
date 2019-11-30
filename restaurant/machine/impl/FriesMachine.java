package restaurant.machine.impl;

public class FriesMachine extends BaseMachine {

    private final int makingFriesTime = 3000;
    private final String fries = "fries";    

    @Override
    public int getMakingTime() {
        return makingFriesTime;
    }

    @Override
    public String getName() {
        return fries;
    }
}