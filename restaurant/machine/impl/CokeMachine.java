package restaurant.machine.impl;

public class CokeMachine extends BaseMachine {

    private final int makingCokeTime = 1000;
    private final String coke = "coke";    

    @Override
    public int getMakingTime() {
        return makingCokeTime;
    }

    @Override
    public String getName() {
        return coke;
    }
}