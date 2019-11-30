package restaurant.machine.impl;
import restaurant.*;

public class BurgerMachine extends BaseMachine {

    private final int MakingBurgerTime = 5000;

    @Override
    public int getMakingTime() {
        return MakingBurgerTime;
    }

    @Override
    public String getName() {
        return Utility.Burger;
    }
}