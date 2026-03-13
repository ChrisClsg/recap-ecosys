package de.clsg;

import java.util.List;

public interface OrderRepoInterface {
  public boolean removeOrderById(String id);
  public List<Order> getAll();
  public Order getById(String id);
  public Order save(Order order);
}
