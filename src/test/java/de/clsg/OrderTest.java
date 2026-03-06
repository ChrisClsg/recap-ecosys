package de.clsg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
  @Test
  void constructor_generatesId_and_setsFields() {
    Order o = new Order(2, "P001");

    assertEquals(2, o.quantity());
    assertEquals("P001", o.productId());
    assertNotNull(o.id());
    assertFalse(o.id().isBlank());
  }

  @Test
  void constructor_generatesDifferentIds_forDifferentOrders() {
    Order o1 = new Order(1, "P001");
    Order o2 = new Order(1, "P001");

    assertNotEquals(o1.id(), o2.id());
  }

  @Test
  void constructor_throws_whenQuantityZeroOrNegative() {
    assertThrows(IllegalArgumentException.class, () -> new Order(0, "P001"));
    assertThrows(IllegalArgumentException.class, () -> new Order(-1, "P001"));
  }

  @Test
  void constructor_throws_whenProductIdNullOrBlank() {
    assertThrows(IllegalArgumentException.class, () -> new Order(1, null));
    assertThrows(IllegalArgumentException.class, () -> new Order(1, ""));
    assertThrows(IllegalArgumentException.class, () -> new Order(1, "   "));
  }
}