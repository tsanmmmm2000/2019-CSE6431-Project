public class Utility {
    public final static int EatingTime = 30000;
    public final static int MakingBurgerTime = 5000;
    public final static int MakingFriesTime = 3000;
    public final static int MakingCokeTime = 1000;
    
    public static RepositoryFactory Repository = new RepositoryFactory();

    private final static long beginSecond = System.currentTimeMillis() / 1000;
    private final static String timePattern = "%s:%s";
    private static Integer currentEaterNumber = 0; 

    public static void setCurrentEaterNumber(int number) {
        currentEaterNumber = number;
    }
    public static Integer getCurrentEaterNumber() {
        return currentEaterNumber;
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
}