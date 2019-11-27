public class RepositoryFactory {
    
    private TableService tableService;
    private OrderService orderService;
    private BurgerMachine burgerMachine;
    private FriesMachine friesMachine;
    private CokeMachine cokeMachine;

    public RepositoryFactory() {
        tableService = new TableService();
        orderService = new OrderService();
        burgerMachine = new BurgerMachine();
        friesMachine = new FriesMachine();
        cokeMachine = new CokeMachine();
    }

    public TableService getTableService() {
        return tableService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public BurgerMachine getBurgerMachine() {
        return burgerMachine;
    }

    public FriesMachine getFriesMachine() {
        return friesMachine;
    }

    public CokeMachine getCokeMachine() {
        return cokeMachine;
    }         
}