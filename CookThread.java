import java.util.*;
import restaurant.domain.*;
import restaurant.service.*;
import restaurant.machine.*;

public class CookThread implements Runnable {

    private Cook cook;
    private Order order;
    private Eater eater;
    private OrderService orderService;
    private List<Machine> machines; 

    public CookThread(Cook cook) {
        this.cook = cook;
        orderService = Utility.Repository.getOrderService();
        machines = Utility.Repository.getMachines();
    }

    public void run() {
        while (true) {
            take();
            process();
        }
    }

    private void take() {
        try {
            // wait order
            synchronized (orderService.getOrders()) {
                while (!orderService.hasOrder()) {
                    orderService.getOrders().wait();
                }

                order = orderService.takeOrder();
                Table table = order.getTable();
                eater = table.getEater();

                Utility.printTakeLog(cook, eater);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private int getFoodNumber() {
        int foodNumber = 0;
        for (Machine machine : machines) {
            foodNumber += order.getFoodNumber(machine.getName());
        }
        return foodNumber;
    }

    private void handleOrder(Machine machine) {
        try {
            long currentMilliSecond = System.currentTimeMillis();
            if (order.getFoodNumber(machine.getName()) > 0) {
                if (!machine.isUsing()) {
                    long startMilliSecond = currentMilliSecond + (machine.getMakingTime() * order.getFoodNumber(machine.getName()));
                    machine.setExpectedFinishTime(Utility.calculateTime(startMilliSecond));                     
                    makeFood(machine);        
                } else {
                    if (Utility.calculateTime(currentMilliSecond).equals(machine.getExpectedFinishTime())) {   
                        synchronized (machine) {
                            machine.wait();
                        }
                        makeFood(machine);                             
                    }
                }
            }  
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void makeFood(Machine machine) {
        try {
            while (order.getFoodNumber(machine.getName()) > 0) {

                Utility.printUseLog(cook, machine);

                machine.use(order);                       

                Thread.sleep(machine.getMakingTime());
            }
            machine.release();
            synchronized (machine) {
                machine.notifyAll();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
           
    }    

    private void process() {
        // process order                    
        while (getFoodNumber() > 0) {
            
            for (Machine machine : machines) {
                handleOrder(machine);
            }

            // finish order
            synchronized (eater) {
                if (getFoodNumber() == 0) {
                    eater.notify();
                }
            }
        }
    }
}
