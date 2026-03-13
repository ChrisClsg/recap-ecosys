package de.clsg;

import java.util.List;
import java.util.Optional;
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

  public Optional<Product> getById(String id) {
    return Optional.ofNullable(products.stream().filter(p -> p.id().equals(id)).findFirst().orElse(null));
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

    Optional<Product> prodOpt = getById(productId);
    if (prodOpt.isEmpty()) return false;

    Product prod = prodOpt.get();
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

    Optional<Product> prodOpt = getById(productId);
    if (prodOpt.isEmpty() || prodOpt.get().stock() < amount) return false;

    Product prod = prodOpt.get();
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
