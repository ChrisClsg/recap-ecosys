package de.clsg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

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

  public List<Order> getAllWithStatus(OrderStatus status) {
    return orders.values().stream()
      .filter(o -> o.status() == status)
      .toList();
  }

  public Map<OrderStatus, Order> getOldestOrderPerStatus() {
    return orders.values().stream()
      .collect(Collectors.toMap(
        Order::status,
        o -> o,
        BinaryOperator.minBy(Comparator.comparing(Order::createdAt))
      ));
  }

  public Order getById(String id) {
    return orders.get(id);
  }
}
