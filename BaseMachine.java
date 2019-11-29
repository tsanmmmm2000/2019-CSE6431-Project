public abstract class BaseMachine {
   
    protected boolean isUsing = false;
    protected String expectedFinishTime;

    public abstract void use(Order order);

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