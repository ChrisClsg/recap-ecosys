package de.clsg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.UUID;

class OrderTest {
  private Order validOrder;

  @BeforeEach
  void setUp() {
    validOrder = new Order(
      Instant.now(),
      2,
      OrderStatus.PROCESSING,
      UUID.randomUUID().toString(),
      "P001"
    );
  }

  @Test
  void constructor_throws_whenQuantityZeroOrNegative() {
    assertThrows(
      IllegalArgumentException.class,
      () -> validOrder.withQuantity(0)
    );
    assertThrows(
      IllegalArgumentException.class,
      () -> validOrder.withQuantity(-1)
    );
  }

  @Test
  void constructor_throws_whenProductIdNullOrBlank() {
    assertThrows(
      IllegalArgumentException.class,
      () -> validOrder.withProductId(null)
    );
    assertThrows(
      IllegalArgumentException.class,
      () -> validOrder.withProductId("")
    );
    assertThrows(
      IllegalArgumentException.class,
      () -> validOrder.withProductId("   ")
    );
  }

  @Test
  void constructor_throws_whenIdNullOrBlank() {
    assertThrows(
      IllegalArgumentException.class,
      () -> validOrder.withId(null)
    );
    assertThrows(
      IllegalArgumentException.class,
      () -> validOrder.withId("")
    );
    assertThrows(
      IllegalArgumentException.class,
      () -> validOrder.withId("   ")
    );
  }

  @Test
  void constructor_throws_whenCreatedAtIsNull() {
    assertThrows(
      IllegalArgumentException.class,
      () -> validOrder.withCreatedAt(null)
    );
  }
}