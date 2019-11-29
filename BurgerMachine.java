public class BurgerMachine extends BaseMachine {

    private final int MakingBurgerTime = 5000;
    private final String Burger = "burger";

    @Override
    public int getMakingTime() {
        return MakingBurgerTime;
    }

    @Override
    public String getName() {
        return Burger;
    }
}