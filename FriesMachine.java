public class FriesMachine {
    private boolean isUsing = false;

    public boolean isUsing() {
        return isUsing;
    }

    public void use(Order order) {
        isUsing = true;
        order.setFriesNumber(order.getFriesNumber() - 1); 
    }

    public void release() {
        isUsing = false;
    }
}