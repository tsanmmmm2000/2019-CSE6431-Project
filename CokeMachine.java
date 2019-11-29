public class CokeMachine extends BaseMachine {

    private final int MakingCokeTime = 1000;
    private final String Coke = "coke";

    @Override
    public int getMakingTime() {
        return MakingCokeTime;
    }

    @Override
    public String getName() {
        return Coke;
    }
}