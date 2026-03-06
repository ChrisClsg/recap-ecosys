package de.clsg;

import java.util.UUID;

public record Order(
  int quantity,
  String id,
  String productId
) {
  public Order(int quantity, String productId) {
    if (quantity <= 0) throw new IllegalArgumentException("amount must be > 0");
    if (productId == null || productId.isBlank()) throw new IllegalArgumentException("productId required");

    this(quantity, UUID.randomUUID().toString(), productId);
  }
}
