public class CokeMachine extends BaseMachine {

    @Override
    public void use(Order order) {
        isUsing = true;     
        order.setCokeNumber(order.getCokeNumber() - 1);           
    }    
}