package restaurant.machine.impl;

public class BurgerMachine extends BaseMachine {

    private final int makingBurgerTime = 5000;
    private final String burger = "burger";

    @Override
    public int getMakingTime() {
        return makingBurgerTime;
    }

    @Override
    public String getName() {
        return burger;
    }
}