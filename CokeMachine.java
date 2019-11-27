public class CokeMachine {
    private boolean isUsing = false;

    public boolean isUsing() {
        return isUsing;
    }

    public void use(Order order) {
        isUsing = true;
        order.setCokeNumber(order.getCokeNumber() - 1);           
    }

    public void release() {
        isUsing = false;
    }
}