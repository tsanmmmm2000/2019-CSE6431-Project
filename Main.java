import java.util.*;
import java.io.*; 

public class Main {

    private static List<Eater> eaters = new ArrayList<Eater>();
    private static List<Cook> cooks = new ArrayList<Cook>();
    private static List<Order> orders = new ArrayList<Order>();

    public static void main(String args[]) {

        if(args.length <= 0) return;

        prepareParameters(args);

        try {

            // thread for each cook
            for (int i = 0; i < cooks.size(); i++) {
                Thread thread = new Thread(new CookThread(cooks.get(i)));
                thread.start();           
            }

            // thread for each eater
            int priorArrivedTime = 0;
            for (int i = 0; i < eaters.size(); i++) {
                Eater eater = eaters.get(i);
                int arrivedTime = eater.getArrivedTime();
                int waitingTime = arrivedTime - priorArrivedTime;
                Thread thread = new Thread(new EaterThread(eater, orders.get(i)));
                Thread.sleep(waitingTime);
                thread.start();
                priorArrivedTime = arrivedTime;
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }        
    }

    private static void prepareParameters(String[] args) {
        BufferedReader reader = null;
        try {
            String file = args[0];
            reader = new BufferedReader(new FileReader(file)); 

            // init role number
            // line order: number of eaters, number of tables, number of cooks
            // ex:
            // 3
            // 2
            // 2 
            int eatersNumber = Integer.parseInt(reader.readLine().trim());
            int tablesNumber = Integer.parseInt(reader.readLine().trim());
            int cooksNumber = Integer.parseInt(reader.readLine().trim());

            // init eater and order
            // in order: eater arrived time, number of burgers, number of fries, number of coke
            // ex: 5,1,1,1
            Utility.setCurrentEatersNumber(eatersNumber);
            for (int i = 1; i <= eatersNumber; i++) {
                
                String numbersLine = reader.readLine();
                String[] numbers = numbersLine.split(",");
                
                // init eater
                int arrivedTime = Integer.parseInt(numbers[0].trim());
                eaters.add(new Eater(i, arrivedTime));
                
                // init order
                int burgersNumber = Integer.parseInt(numbers[1].trim());
                int friesNumber = Integer.parseInt(numbers[2].trim());
                int cokeNumber = Integer.parseInt(numbers[3].trim());
                HashMap<String, Integer> food = new HashMap<String, Integer>();
                food.put(Utility.Burger, burgersNumber);
                food.put(Utility.Fries, friesNumber);
                food.put(Utility.Coke, cokeNumber);
                orders.add(new Order(food));
            }

            // init cooks
            for (int i = 1; i <= cooksNumber; i++) {
                cooks.add(new Cook(i));
            }

            // init tables
            TableService tableService = Utility.Repository.getTableService();
            for (int i = 1; i <= tablesNumber; i++) {
                tableService.addTable(new Table(i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
        }
    }

}