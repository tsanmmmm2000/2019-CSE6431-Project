package restaurant;
import java.util.*;
import restaurant.machine.*;
import restaurant.machine.impl.*;
import restaurant.service.*;

public class RepositoryFactory {
    
    private TableService tableService;
    private OrderService orderService;
    private List<Machine> machines;

    public RepositoryFactory() {
        tableService = new TableService();
        orderService = new OrderService();

        machines = new ArrayList<Machine>();       
        machines.add(new BurgerMachine());
        machines.add(new FriesMachine());
        machines.add(new CokeMachine());
    }

    public TableService getTableService() {
        return tableService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public Machine getMachine(String name) {
        for (Machine machine : machines) {
            if (machine.getName().equals(name)) return machine;
        }
        return null;
    }           
}