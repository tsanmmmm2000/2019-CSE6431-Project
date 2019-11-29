public class Main {
    public static void main(String args[]) {
        int eatersNumber = 3;
        int tablesNumber = 2;
        int cooksNumber = 2;
        
        // init eaters information
        Eater[] eaters = new Eater[eatersNumber];
        eaters[0] = new Eater(1, 5);
        eaters[1] = new Eater(2, 10); // 6 
        eaters[2] = new Eater(3, 60); // 7

        Order[] orders = new Order[eatersNumber];
        orders[0] = new Order(1, 1, 1);
        orders[1] = new Order(2, 0, 1);
        orders[2] = new Order(1, 2, 1);        

        // init cooks information
        Cook[] cooks = new Cook[cooksNumber];
        cooks[0] = new Cook(1);
        cooks[1] = new Cook(2);

        // init tables information
        TableService tableService = Utility.Repository.getTableService();
        tableService.addTable(new Table(1));
        tableService.addTable(new Table(2));


        // thread for each cook
        for (int i = 0; i < cooksNumber; i++) {
            Thread thread = new Thread(new CookThread(cooks[i]));
            thread.start();           
        }

        try {
            // thread for each eater
            int priorArrivedTime = 0;
            Utility.setCurrentEaterNumber(eaters.length);
            for (int i = 0; i < eaters.length; i++) {
                int arrivedTime = eaters[i].getArrivedTime();
                int waitingTime = arrivedTime - priorArrivedTime;
                Thread thread = new Thread(new EaterThread(eaters[i], orders[i]));
                Thread.sleep(waitingTime);
                thread.start();
                priorArrivedTime = arrivedTime;
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }        
    }
}