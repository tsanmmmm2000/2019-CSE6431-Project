package restaurant.machine;
import restaurant.domain.*;

public interface Machine {  
    int getMakingTime();
    String getName(); 
    String getExpectedFinishTime();
    boolean isUsing();
    void use(Order order);
    void release(); 
    void setExpectedFinishTime (String expectedFinishTime);
}