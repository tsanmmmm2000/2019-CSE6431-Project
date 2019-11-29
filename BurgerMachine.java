public class BurgerMachine extends BaseMachine {

    @Override
    public void use(Order order) {
        isUsing = true;
        order.setBurgersNumber(order.getBurgersNumber() - 1);         
    }  
}