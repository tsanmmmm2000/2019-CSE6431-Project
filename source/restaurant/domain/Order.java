package restaurant.domain;
import java.util.*;

public class Order {

    private Table table;

    // key: food name
    // value: food number
    private HashMap<String, Integer> food;

    public Order(HashMap<String, Integer> food) {
        this.food = food;
    }

    public int getFoodNumber(String name) {
        return food.getOrDefault(name, 0);
    }

    public void setFoodNumber(String name, int number) {
        food.put(name, number);
    }    

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }    
  
}