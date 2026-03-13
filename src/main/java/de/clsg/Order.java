package de.clsg;

import java.time.Instant;

import lombok.With;

@With
public record Order(
  Instant createdAt,
  int quantity,
  OrderStatus status,
  String id,
  String productId
) {
  public Order {
    if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
    if (productId == null || productId.isBlank()) throw new IllegalArgumentException("productId required");
    if (id == null || id.isBlank()) throw new IllegalArgumentException("id required");
    if (createdAt == null) throw new IllegalArgumentException("createdAt required");
  }
}
