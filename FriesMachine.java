public class FriesMachine extends BaseMachine {

    @Override
    public void use(Order order) {
        isUsing = true;
        order.setFriesNumber(order.getFriesNumber() - 1); 
    }   
}