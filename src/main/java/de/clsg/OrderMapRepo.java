package de.clsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapRepo implements OrderRepoInterface {
  private Map<String, Order> orders = new HashMap<>();

  public Order save(Order order) {
    orders.put(order.id(), order);
    return order;
  }

  public boolean removeOrderById(String id) {
    return orders.remove(id) != null;
  }

  public List<Order> getAll() {
    return new ArrayList<>(orders.values());
  }

  public Order getById(String id) {
    return orders.get(id);
  }
}
