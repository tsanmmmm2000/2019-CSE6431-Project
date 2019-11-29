public abstract class BaseMachine implements Machine{
   
    protected boolean isUsing = false;
    protected String expectedFinishTime;

    public abstract int getMakingTime();
    public abstract String getName();
    //public abstract void use(Order order);

    public void use(Order order) {
        isUsing = true;
        order.setFoodNumber(getName(), order.getFoodNumber(getName()) - 1);         
    } 

    public void release() {
        isUsing = false;
    }

    public boolean isUsing() {
        return isUsing;
    }    

    public String getExpectedFinishTime() {
        return expectedFinishTime;
    }
    
    public void setExpectedFinishTime (String expectedFinishTime) {
        this.expectedFinishTime = expectedFinishTime;
    }
}