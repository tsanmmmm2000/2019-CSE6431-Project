public class BurgerMachine {
    private boolean isUsing = false;

    public boolean isUsing() {
        return isUsing;
    }

    public void use(Order order) {
        isUsing = true;
        order.setBurgersNumber(order.getBurgersNumber() - 1);         
    }

    public void release() {
        isUsing = false;
    }
}