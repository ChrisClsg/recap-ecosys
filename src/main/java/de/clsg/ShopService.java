package de.clsg;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class ShopService {
  private final ProductRepo pr;
  private final OrderRepoInterface or;

  public ShopService(ProductRepo pr, OrderRepoInterface or) {
    this.pr = pr;
    this.or = or;
  }

  public Order placeOrder(int quantity, String productId) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be > 0");
    }

    Optional<Product> pOpt = pr.getById(productId);
    if (pOpt.isEmpty()) {
      System.out.println("Product with ID " + productId + " not found.");
      return null;
    }

    boolean ok = pr.decreaseStock(productId, quantity);
    if (!ok) {
      System.out.println("Not enough stock for product: " + productId);
      return null;
    }

    Order order = new Order(
      Instant.now(),
      quantity,
      OrderStatus.PROCESSING,
      UUID.randomUUID().toString(),
      productId
    );

    return or.save(order);
  }

  public Order updateOrderStatus(Order order, OrderStatus newStatus) {
    return or.save(order.withStatus(newStatus));
  }
}
