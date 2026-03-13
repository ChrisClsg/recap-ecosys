package de.clsg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {
  private ProductRepo pr;
  private OrderListRepo or;
  private ShopService shop;
  private Product validProduct;

  @BeforeEach
  void setUp() {
    pr = new ProductRepo();
    or = new OrderListRepo();
    shop = new ShopService(pr, or);
    validProduct = new Product(new BigDecimal("10.00"), 10, "Bosch", "Werkzeuge", "400...", "P001", "Akkuschrauber");
  }

  @Test
  void placeOrder_success_reducesStock_andStoresOrder() {
    pr.addProduct(validProduct);

    Order o = shop.placeOrder(3, "P001");

    assertEquals(7, pr.getById("P001").get().stock());
    // Assertions for correct order creation
    assertNotNull(o);
    assertEquals(1, or.getAll().size());
    assertEquals(3, o.quantity());
    assertEquals("P001", o.productId());
    assertEquals(OrderStatus.PROCESSING, o.status());
    assertNotNull(o.createdAt());
    assertNotNull(o.id());
    assertFalse(o.id().isBlank());
  }

  @Test
  void placeOrder_throwsIllegalArgument_whenProductNotFound() {
    assertThrows(IllegalArgumentException.class, () -> shop.placeOrder(1, "NOPE"));
    assertEquals(0, or.getAll().size());
  }

  @Test
  void placeOrder_returnsNull_whenNotEnoughStock() {
    pr.addProduct(validProduct);

    Order o = shop.placeOrder(100, "P001");

    assertNull(o);
    assertEquals(0, or.getAll().size());
    assertEquals(validProduct.stock(), pr.getById("P001").get().stock());
  }

  @Test
  void updateOrderStatus_savesAndReturnsUpdatedOrder_whenGivenValidOrderStatus() {
    pr.addProduct(validProduct);
    Order o = shop.placeOrder(5, "P001");

    assertEquals(OrderStatus.PROCESSING, o.status());
    Order updatedO = shop.updateOrderStatus(o, OrderStatus.IN_DELIVERY);

    assertEquals(OrderStatus.IN_DELIVERY, updatedO.status());
    assertEquals(or.getById(o.id()), updatedO);
  }
}
