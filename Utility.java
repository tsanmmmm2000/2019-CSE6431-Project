import restaurant.domain.*;
import restaurant.machine.*;

public class Utility {
    public final static int EatingTime = 30000;  
    public static RepositoryFactory Repository = new RepositoryFactory();

    private final static long beginSecond = System.currentTimeMillis() / 1000;
    private final static String timePattern = "%s:%s";
    private final static String arrive = "%s - Diner %s arrives.";
    private final static String seat = "%s - Diner %s is seated at table %s.";
    private final static String eat = "%s - Diner %s's order is ready. Diner %s starts eating.";
    private final static String leave = "%s - Diner %s finishes. Diner %s leaves the restaurant.";
    private final static String finish = "%s - The last diner leaves the restaurant.";
    private final static String take = "%s - Cook %s processes Diner %s's order.";
    private final static String use = "%s - Cook %s uses the %s machine.";
    private static Integer currentEatersNumber = 0; 

    public static void setCurrentEatersNumber(int number) {
        currentEatersNumber = number;
    }

    public static Integer getCurrentEatersNumber() {
        return currentEatersNumber;
    }
    
    public static String calculateTime(long startMilliSecond) {
        // assume the maximum time of this program is 59:59
        long startSecond = startMilliSecond / 1000;
        long programSecond = startSecond - beginSecond;
        long programMinute = 0;

        if (programSecond >= 60) {
            programMinute = programSecond / 60;
            programSecond = programSecond % 60;

            if (programMinute >= 60) {
                // time limit exceeded
            }
        }

        String minute = Long.toString(programMinute);
        String second = Long.toString(programSecond);

        minute = (programMinute < 10) ? "0" + minute : minute;
        second = (programSecond < 10) ? "0" + second : second;

        return String.format(timePattern, minute, second);
    }

    public static String calculateTime() {
        return calculateTime(System.currentTimeMillis());
    }

    public static void printArriveLog(Eater eater) {
        System.out.println(String.format(arrive, calculateTime(), eater.getId()));
    }

    public static void printSeatLog(Table table) {
        System.out.println(String.format(seat, 
            calculateTime(), table.getEater().getId(), table.getId()));       
    }

    public static void printEatLog(Eater eater) {
        System.out.println(String.format(eat,
            calculateTime(), eater.getId(), eater.getId()));       
    }

    public static void printLeaveLog(Eater eater) {
        System.out.println(String.format(leave, 
            calculateTime(), eater.getId(), eater.getId()));       
    }

    public static void printFinishLog() {
        System.out.println(String.format(finish, calculateTime()));        
    }

    public static void printTakeLog(Cook cook, Eater eater) {
        System.out.println(String.format(take, 
            calculateTime(), cook.getId(), eater.getId()));
    }

    public static void printUseLog(Cook cook, Machine machine) {
        System.out.println(String.format(use, 
            calculateTime(), cook.getId(), machine.getName()));
    }    

}