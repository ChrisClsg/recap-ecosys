package de.clsg;

import java.util.List;
import java.util.ArrayList;

public class ProductRepo {
  private List<Product> products = new ArrayList<>();

  public void addProduct(Product prod) {
    products.add(prod);
  }

  public boolean removeProductById(String id) {
    return products.removeIf(p -> p.id().equals(id));
  }

  public List<Product> getAll() {
    return products;
  }

  public Product getById(String id) {
    return products.stream().filter(p -> p.id().equals(id)).findFirst().orElse(null);
  }

  private boolean updateProduct(Product updated) {
    for (int i = 0; i < products.size(); i++) {
      if (products.get(i).id().equals(updated.id())) {
        products.set(i, updated);
        return true;
      }
    }

    return false;
  }

  public boolean increaseStock(String productId, int amount) {
    if (amount <= 0) return false;
    Product prod = getById(productId);
    if (prod == null) return false;

    Product updated = new Product(
      prod.price(),
      prod.stock() + amount,
      prod.brand(),
      prod.category(),
      prod.ean(),
      prod.id(),
      prod.name()
    );

    return updateProduct(updated);
  }

  public boolean decreaseStock(String productId, int amount) {
    if (amount <= 0) return false;
    Product prod = getById(productId);

    if (prod == null || prod.stock() < amount) return false;

    Product updated = new Product(
      prod.price(),
      prod.stock() - amount,
      prod.brand(),
      prod.category(),
      prod.ean(),
      prod.id(),
      prod.name()
    );

    return updateProduct(updated);
  }
}
