public class Eater {
    private int id;
    private int arrivedTime;

    public Eater(int id, int arrivedTime) {
        this.id = id;
        this.arrivedTime = arrivedTime;
    }

    public int getId() {
        return id;
    }

    public int getArrivedTime() {
        return arrivedTime * 1000;
    }
}