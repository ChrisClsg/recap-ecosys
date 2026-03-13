package de.clsg;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderListRepoTest {
  private OrderListRepo or;
  private Order o1;
  private Order o2;

  @BeforeEach
  void setUp() {

    or = new OrderListRepo();
    o1 = new Order(
      Instant.now(),
      2,
      OrderStatus.PROCESSING,
      UUID.randomUUID().toString(),
      "P001"
    );
    o2 = new Order(
      Instant.now(),
      1,
      OrderStatus.PROCESSING,
      UUID.randomUUID().toString(),
      "P002"
    );
  }

  @Test
  void getAll_returnsEmptyListInitially() {
    assertEquals(0, or.getAll().size());
  }

  @Test
  void addOrder_addsOrder_soGetAllContainsIt() {
    or.addOrder(o1);

    List<Order> all = or.getAll();
    assertEquals(1, all.size());
    assertEquals(o1, all.get(0));
  }

  @Test
  void addOrder_keepsInsertionOrder() {
    or.addOrder(o1);
    or.addOrder(o2);

    List<Order> all = or.getAll();
    assertEquals(2, all.size());
    assertEquals(o1, all.get(0));
    assertEquals(o2, all.get(1));
  }

  @Test
  void getById_returnsNull_whenNotFound() {
    or.addOrder(o1);

    assertNull(or.getById("does-not-exist"));
  }

  @Test
  void getById_returnsOrder_whenFound() {
    or.addOrder(o1);
    or.addOrder(o2);

    Order found = or.getById(o2.id());
    assertNotNull(found);
    assertEquals(o2, found);
    assertEquals("P002", found.productId());
    assertEquals(1, found.quantity());
  }

  @Test
  void removeOrderById_returnsFalse_whenNotFound() {
    or.addOrder(o1);

    assertFalse(or.removeOrderById("nope"));
    assertEquals(1, or.getAll().size());
  }

  @Test
  void removeOrderById_removesOrder_andReturnsTrue_whenFound() {
    or.addOrder(o1);
    or.addOrder(o2);

    assertTrue(or.removeOrderById(o1.id()));

    assertEquals(1, or.getAll().size());
    assertNull(or.getById(o1.id()));
    assertNotNull(or.getById(o2.id()));
  }
}