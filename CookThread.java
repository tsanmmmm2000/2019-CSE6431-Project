import java.util.*;

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

                System.out.println(String.format("%s - Cook %s processes Diner %s's order.", Utility.calculateTime(),
                        cook.getId(), eater.getId()));
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
            String name = machine.getName();
            if (order.getFoodNumber(name) > 0) {
                if (!machine.isUsing()) {
                    long startMilliSecond = currentMilliSecond + (machine.getMakingTime() * order.getFoodNumber(name));
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
            String name = machine.getName();
            while (order.getFoodNumber(name) > 0) {

                System.out.println(String.format("%s - Cook %s uses the %s machine.", 
                    Utility.calculateTime(), cook.getId(), name));

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
