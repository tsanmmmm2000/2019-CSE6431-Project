public class CookThread implements Runnable {

    private Cook cook;
    private Order order;
    private Eater eater;
    private BurgerMachine burgerMachine;
    private FriesMachine friesMachine;
    private CokeMachine cokeMachine;
    private OrderService orderService;

    public CookThread(Cook cook) {
        this.cook = cook;
        burgerMachine = Utility.Repository.getBurgerMachine();
        friesMachine = Utility.Repository.getFriesMachine();
        cokeMachine = Utility.Repository.getCokeMachine();
        orderService = Utility.Repository.getOrderService();
    }

    public void run()
    {
        while (true) {
            take();
            process();
        }
    }
    
    private void take() {
        try {
            // wait order
            synchronized(orderService.getOrders()) {
                while (!orderService.hasOrder()) {
                    orderService.getOrders().wait();
                }

                order = orderService.takeOrder();
                Table table = order.getTable();
                eater = table.getEater();

                System.out.println(String.format(
                    "%s - Cook %s processes Diner %s's order.",
                    Utility.calculateTime(),
                    cook.getId(),
                    eater.getId()));
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void process() {
        try {
            // process order
                     
            int total = order.getBurgersNumber() 
                + order.getFriesNumber() 
                + order.getCokeNumber();
            
            while (total > 0) {


                while((burgerMachine.isUsing() || order.getBurgersNumber() <= 0) 
                    && (friesMachine.isUsing() || order.getFriesNumber() <= 0)
                    && (cokeMachine.isUsing() || order.getCokeNumber() <= 0)) {
                    
                    synchronized (Utility.Busy) {
                        Utility.Busy.wait();
                    }
                }
                

                if (!burgerMachine.isUsing() && order.getBurgersNumber() > 0) {
                    
                    while (order.getBurgersNumber() > 0) {
                        System.out.println(String.format(
                            "%s - Cook %s uses the burger machine.", 
                            Utility.calculateTime(), 
                            cook.getId()));
                        
                        burgerMachine.use(order);

            
                        Thread.sleep(Utility.MakingBurgerTime);

                        if (order.getBurgersNumber() == 0) {
                            burgerMachine.release();

                            synchronized (Utility.Busy){
                                Utility.Busy.notifyAll();
                            }                        
                        }
                    }
                }

                if (!friesMachine.isUsing() && order.getFriesNumber() > 0) {
                    
                    while (order.getFriesNumber() > 0) {

                        System.out.println(String.format(
                            "%s - Cook %s uses the fries machine.", 
                            Utility.calculateTime(), 
                            cook.getId()));   

                        friesMachine.use(order);

                       Thread.sleep(Utility.MakingFriesTime);

                        if (order.getFriesNumber() == 0) {
                            friesMachine.release();
                            synchronized (Utility.Busy){
                                Utility.Busy.notifyAll();
                            }                             
                        }                       
                    }
                }


                if (!cokeMachine.isUsing() && order.getCokeNumber() > 0) {
                    
                    while (order.getCokeNumber() > 0) {

                        System.out.println(String.format(
                            "%s - Cook %s uses the coke machine.", 
                            Utility.calculateTime(), 
                            cook.getId()));   

                        cokeMachine.use(order);

                        Thread.sleep(Utility.MakingCokeTime);

                        if (order.getCokeNumber() == 0) {
                            cokeMachine.release();

                            synchronized (Utility.Busy){
                                Utility.Busy.notifyAll();
                            }                             
                        }                       
                    }           
                }


                // finish order
                total = order.getBurgersNumber() 
                    + order.getFriesNumber() 
                    + order.getCokeNumber();
                if (total == 0) {
                    synchronized (eater) {
                        eater.notify();
                    }
                }
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
