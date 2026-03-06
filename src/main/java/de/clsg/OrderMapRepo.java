package de.clsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapRepo implements OrderRepoInterface {
  private Map<String, Order> orders = new HashMap<>();

  public void addOrder(Order order) {
    orders.put(order.id(), order);
  }

  public boolean removeOrderById(String id) {
    return orders.remove(id) != null;
  }

  public List<Order> getAll() {
    System.out.println("######## List of all orders ########");
    orders.forEach((id, order) -> System.out.println(order));
    System.out.println("######################################");
    return new ArrayList<>(orders.values());
  }

  public Order getById(String id) {
    return orders.get(id);
  }
}
