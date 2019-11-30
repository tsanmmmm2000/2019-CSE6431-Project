package restaurant.domain;

public class Table {

    private int id;
    private TableStatus status;
    private Eater eater;

    public Table(int id) {
        this.id = id;
        this.status = TableStatus.Available;
    }

    public int getId() {
        return id;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }

    public void setEater(Eater eater) {
        this.eater = eater;
    }

    public Eater getEater() {
        return eater;
    }    
}