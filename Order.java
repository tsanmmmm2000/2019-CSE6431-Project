public class Order {
    private int burgersNumber;
    private int friesNumber;
    private int cokeNumber;
    private Table table;

    public Order(int burgersNumber, int friesNumber, int cokeNumber) {
        this.burgersNumber = burgersNumber;
        this.friesNumber = friesNumber;
        this.cokeNumber = cokeNumber;
    }

    public int getBurgersNumber() {
        return burgersNumber;
    }

    public void setBurgersNumber(int number) {
        burgersNumber = number;
    }

    public int getFriesNumber() {
        return friesNumber;
    }

    public void setFriesNumber(int number) {
        friesNumber = number;
    }

    public int getCokeNumber() {
        return cokeNumber;
    }

    public void setCokeNumber(int number) {
        cokeNumber = number;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }    
  
}