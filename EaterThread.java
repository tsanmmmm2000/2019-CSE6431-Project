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
        Utility.printArriveLog(eater);
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

                Utility.printSeatLog(table);
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
                Utility.printEatLog(eater);

                Thread.sleep(Utility.EatingTime);
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } 
    }

    private void leave() {
        // release table
        synchronized (tableService.getTables()) {
            Utility.printLeaveLog(eater);
            tableService.releaseTable(table);
            tableService.getTables().notify();
        }

        synchronized (Utility.getCurrentEatersNumber()) {
            Utility.setCurrentEatersNumber(Utility.getCurrentEatersNumber() - 1);
        }
    }

    private void finish() {
        synchronized(Utility.getCurrentEatersNumber()) {
            if (Utility.getCurrentEatersNumber() == 0) {
                Utility.printFinishLog();
                System.exit(0);
            }
        }
    }
}