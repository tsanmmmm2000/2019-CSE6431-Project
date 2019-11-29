import java.util.*;

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

        // init order information
        List<Machine> machines = Utility.Repository.getMachines();
        HashMap<String, Integer> food1 = new HashMap<String, Integer>();
        HashMap<String, Integer> food2 = new HashMap<String, Integer>();
        HashMap<String, Integer> food3 = new HashMap<String, Integer>();
        List<Integer> food1Numbers = new ArrayList<Integer>();
        food1Numbers.add(1);
        food1Numbers.add(1);
        food1Numbers.add(1);
        List<Integer> food2Numbers = new ArrayList<Integer>();
        food2Numbers.add(2);
        food2Numbers.add(0);
        food2Numbers.add(1);
        List<Integer> food3Numbers = new ArrayList<Integer>();
        food3Numbers.add(1);
        food3Numbers.add(2);
        food3Numbers.add(1);            

        for (int i = 0; i < machines.size(); i++) {
            String name = machines.get(i).getName();
            int food1Number = food1Numbers.get(i);
            int food2Number = food2Numbers.get(i);
            int food3Number = food3Numbers.get(i);
            food1.put(name, food1Number);
            food2.put(name, food2Number);
            food3.put(name, food3Number);
        }    

        Order[] orders = new Order[eatersNumber];  
        orders[0] = new Order(food1);
        orders[1] = new Order(food2);
        orders[2] = new Order(food3);
  

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