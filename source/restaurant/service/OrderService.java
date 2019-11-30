package restaurant.service;
import java.util.*;
import restaurant.domain.*;

public class OrderService {
    private List<Order> orders = new ArrayList<Order>();

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order, Table table) {
        order.setTable(table);
        orders.add(order);
    }

    public Order takeOrder() {
        return hasOrder() ? orders.remove(0) : null;
    }

    public boolean hasOrder() {
        return !orders.isEmpty();
    }   
}