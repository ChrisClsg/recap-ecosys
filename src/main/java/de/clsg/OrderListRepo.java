package de.clsg;

import java.util.ArrayList;
import java.util.List;

public class OrderListRepo implements OrderRepoInterface {
  private List<Order> orders = new ArrayList<>();

  public Order save(Order order) {
    for (int i = 0; i < orders.size(); i++) {
      if(orders.get(i).id().equals(order.id())) {
        orders.set(i, order);
        return order;
      }
    }

    orders.add(order);
    return order;
  }

  public boolean removeOrderById(String id) {
    return orders.removeIf(o -> o.id().equals(id));
  }

  public List<Order> getAll() {
    return orders;
  }

  public Order getById(String id) {
    return orders.stream().filter(o -> o.id().equals(id)).findFirst().orElse(null);
  }
}
