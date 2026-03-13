package de.clsg;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductRepoTest {
  private ProductRepo pr;
  private Product prod1;
  private Product prod2;

  @BeforeEach
  void setUp() {
    pr = new ProductRepo();
    prod1 = new Product(new BigDecimal("129.99"), 12, "Bosch", "Akkuschrauber", "4001234567895", "P001", "Akkuschrauber 18V");
    prod2 = new Product(new BigDecimal("12.49"), 55, "Stanley", "Handwerkzeug", "4002345678901", "P002", "Hammer 300g");
  }

  @Test
  void getAll_returnsEmptyListInitially() {
    assertEquals(0, pr.getAll().size());
  }

  @Test
  void addProduct_addsProduct_soGetAllContainsIt() {
    pr.addProduct(prod1);

    List<Product> all = pr.getAll();
    assertEquals(1, all.size());
    assertSame(prod1, all.get(0));
  }

  @Test
  void addProduct_addsMultipleProducts_inInsertionOrder() {
    pr.addProduct(prod1);
    pr.addProduct(prod2);

    List<Product> all = pr.getAll();
    assertEquals(2, all.size());
    assertSame(prod1, all.get(0));
    assertSame(prod2, all.get(1));
  }

  @Test
  void decreaseStock_returnsFalse_whenProductNotFound() {
    assertFalse(pr.decreaseStock("NOPE", 1));
  }

  @Test
  void decreaseStock_returnsFalse_whenNotEnoughStock() {
    pr.addProduct(prod1);

    boolean ok = pr.decreaseStock("P001", 999);
    assertFalse(ok);

    Product still = pr.getById("P001").orElseThrow();
    assertEquals(12, still.stock());
  }

  @Test
  void decreaseStock_decreasesAProductsStock_byGivenAmount() {
    pr.addProduct(prod1);
    String id = prod1.id();
    int stockBefore = prod1.stock();

    boolean bool = pr.decreaseStock(id, 10);
    assertTrue(bool);

    Product updated = pr.getById(id).orElseThrow();
    assertEquals(stockBefore - 10, updated.stock());
  }

  @Test
  void getById_returnsEmptyOptional_whenRepoEmpty() {
    assertTrue(pr.getById("P001").isEmpty());
  }

  @Test
  void getById_returnsEmptyOptional_whenIdNotFound() {
    pr.addProduct(prod1);
    assertTrue(pr.getById("DOES_NOT_EXIST").isEmpty());
  }

  @Test
  void getById_returnsProduct_whenIdExists() {
    pr.addProduct(prod1);
    Optional<Product> found = pr.getById("P001");

    assertFalse(found.isEmpty());
    assertEquals("P001", found.get().id());
    assertEquals(prod1, found.get());
  }

  @Test
  void increaseStock_returnsFalse_whenProductNotFound() {
    assertFalse(pr.increaseStock("NOPE", 1));
  }

  @Test
  void increaseStock_increasesAProductsStock_byGivenAmount() {
    pr.addProduct(prod1);
    String id = prod1.id();
    int stockBefore = prod1.stock();

    pr.increaseStock(id, 10);

    Product updated = pr.getById(id).orElseThrow();
    assertEquals(stockBefore + 10, updated.stock());
  }

  @Test
  void removeProductById_returnsFalse_whenIdNotFound() {
    pr.addProduct(prod1);
    assertFalse(pr.removeProductById("NOPE"));
    assertEquals(1, pr.getAll().size());
  }

  @Test
  void removeProductById_removesProduct_andReturnsTrue_whenIdExists() {
    pr.addProduct(prod1);
    pr.addProduct(prod2);

    assertTrue(pr.removeProductById("P001"));
    assertEquals(1, pr.getAll().size());
    assertTrue(pr.getById("P001").isEmpty());
    assertFalse(pr.getById("P002").isEmpty());
  }
}
