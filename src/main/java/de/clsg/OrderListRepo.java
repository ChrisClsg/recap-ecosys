package de.clsg;

import java.util.ArrayList;
import java.util.List;

public class OrderListRepo {
  private List<Order> orders = new ArrayList<>();

  public void addOrder(Order order) {
    orders.add(order);
  }

  public boolean removeOrderById(String id) {
    return orders.removeIf(o -> o.id().equals(id));
  }

  public List<Order> getAll() {
    System.out.println("######## List of all orders ########");
    orders.forEach(o -> System.out.println(o));
    System.out.println("######################################");
    return orders;
  }

  public Order getById(String id) {
    return orders.stream().filter(o -> o.id().equals(id)).findFirst().orElse(null);
  }
}
