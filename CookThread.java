public class CookThread implements Runnable {

    private final Cook cook;
    private Order order;
    private Eater eater;
    private final BurgerMachine burgerMachine;
    private final FriesMachine friesMachine;
    private final CokeMachine cokeMachine;
    private final OrderService orderService;

    public CookThread(final Cook cook) {
        this.cook = cook;
        burgerMachine = Utility.Repository.getBurgerMachine();
        friesMachine = Utility.Repository.getFriesMachine();
        cokeMachine = Utility.Repository.getCokeMachine();
        orderService = Utility.Repository.getOrderService();
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
                final Table table = order.getTable();
                eater = table.getEater();

                System.out.println(String.format("%s - Cook %s processes Diner %s's order.", Utility.calculateTime(),
                        cook.getId(), eater.getId()));
            }
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void process() {
        try {
            // process order
            int total = order.getBurgersNumber() + order.getFriesNumber() + order.getCokeNumber();
            while (total > 0) {
  
                long currentMilliSecond = System.currentTimeMillis();           
                if (order.getBurgersNumber() > 0) {
                    if (!burgerMachine.isUsing()) {
                        long startMilliSecond = currentMilliSecond + (Utility.MakingBurgerTime * order.getBurgersNumber());
                        burgerMachine.setExpectedFinishTime(Utility.calculateTime(startMilliSecond));                        
                        makeBurger();
                    } else {
                        if (Utility.calculateTime(currentMilliSecond).equals(burgerMachine.getExpectedFinishTime())) {   
                            synchronized (burgerMachine) {
                                burgerMachine.wait();
                            }
                            makeBurger();
                        }              
                    }
                }

                currentMilliSecond = System.currentTimeMillis();
                if (order.getFriesNumber() > 0) {
                    if (!friesMachine.isUsing()) {
                        long startMilliSecond = currentMilliSecond + (Utility.MakingFriesTime * order.getFriesNumber());
                        friesMachine.setExpectedFinishTime(Utility.calculateTime(startMilliSecond));
                        makeFries();                
                    } else {
                        if (Utility.calculateTime(currentMilliSecond).equals(friesMachine.getExpectedFinishTime())) {   
                            synchronized (friesMachine) {
                                friesMachine.wait();
                            }
                            makeFries(); 
                        }
                    }
                }

                currentMilliSecond = System.currentTimeMillis();
                if (order.getCokeNumber() > 0) {
                    if (!cokeMachine.isUsing()) {
                        long startMilliSecond = currentMilliSecond + (Utility.MakingCokeTime * order.getCokeNumber());
                        cokeMachine.setExpectedFinishTime(Utility.calculateTime(startMilliSecond));                     
                        makeCoke();        
                    } else {
                        if (Utility.calculateTime(currentMilliSecond).equals(cokeMachine.getExpectedFinishTime())) {   
                            synchronized (cokeMachine) {
                                cokeMachine.wait();
                            }
                            makeCoke();                             
                        }
                    }
                }


                // finish order
                synchronized (eater) {
                    total = order.getBurgersNumber() + order.getFriesNumber() + order.getCokeNumber();
                    if (total == 0) {
                        eater.notify();
                    }
                }
            }

        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void makeBurger() {
        try {
            while (order.getBurgersNumber() > 0) {
                System.out.println(String.format("%s - Cook %s uses the burger machine.",
                        Utility.calculateTime(), cook.getId()));

                burgerMachine.use(order);

                Thread.sleep(Utility.MakingBurgerTime);
            }
            burgerMachine.release();

            synchronized (burgerMachine) {
                burgerMachine.notifyAll();
            }
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        }        
    }

    private void makeFries() {
        try {
            while (order.getFriesNumber() > 0) {

                System.out.println(String.format("%s - Cook %s uses the fries machine.",
                        Utility.calculateTime(), cook.getId()));

                friesMachine.use(order);                       

                Thread.sleep(Utility.MakingFriesTime);
            }

            friesMachine.release();
            synchronized (friesMachine) {
                friesMachine.notifyAll();
            } 
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        }        
    }

    private void makeCoke() {
        try {
            while (order.getCokeNumber() > 0) {

                System.out.println(String.format("%s - Cook %s uses the coke machine.", 
                    Utility.calculateTime(), cook.getId()));

                cokeMachine.use(order);                       

                Thread.sleep(Utility.MakingCokeTime);
            }
            cokeMachine.release();
            synchronized (cokeMachine) {
                cokeMachine.notifyAll();
            }
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        }
           
    }

}
