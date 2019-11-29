public class EaterThread implements Runnable {
    
    private Eater eater;
    private Order order;
    private Table table;
    private OrderService orderService;
    private TableService tableService;

    public EaterThread(Eater eater, Order order) {
        this.eater = eater;
        this.order = order;
        orderService = Utility.Repository.getOrderService();
        tableService = Utility.Repository.getTableService();
    }

    public void run()
    {
        arrive();
        seat();
        eat();
        leave();
        finish();
    }
    
    private void arrive() {
        System.out.println(String.format(
            "%s - Diner %s arrives.", Utility.calculateTime(), eater.getId()));
    }

    private void seat() {
        try {
            // wait table     
            synchronized (tableService.getTables()) { 
                while (!tableService.isTableAvailable()) {
                    tableService.getTables().wait();
                } 

                table = tableService.getAvailableTable();
                tableService.assignTable(table, eater);

                System.out.println(String.format(
                    "%s - Diner %s is seated at table %s.", 
                    Utility.calculateTime(),
                    table.getEater().getId(), 
                    table.getId()));
            }

            // add order
            synchronized (orderService.getOrders()) {
                orderService.addOrder(order, table);
                orderService.getOrders().notify();
            }        
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void eat() {
        try {
            // wait order
            synchronized (eater) {
                eater.wait(); 

                // eating
                System.out.println(String.format(
                    "%s - Diner %s's order is ready. Diner %s starts eating.", 
                    Utility.calculateTime(),
                    eater.getId(),
                    eater.getId()));

                Thread.sleep(Utility.EatingTime);
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } 
    }

    private void leave() {
        // release table
        synchronized (tableService.getTables()) {

            System.out.println(String.format(
                "%s - Diner %s finishes. Diner %s leaves the restaurant.",
                Utility.calculateTime(),
                eater.getId(),
                eater.getId()));

            tableService.releaseTable(table);
            tableService.getTables().notify();
        }

        synchronized (Utility.getCurrentEaterNumber()) {
            Utility.setCurrentEaterNumber(Utility.getCurrentEaterNumber() - 1);
        }
    }

    private void finish() {
        synchronized(Utility.getCurrentEaterNumber()) {
            if (Utility.getCurrentEaterNumber() == 0) {
                System.out.println(String.format(
                    "%s - The last diner leaves the restaurant.", Utility.calculateTime()));
                System.exit(0);
            }
        }
    }
}