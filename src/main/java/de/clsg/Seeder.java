package de.clsg;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class Seeder {
  public static void seed(ProductRepo pr) {
    try {
      Files.readAllLines(Path.of("src/main/java/de/clsg/productSeed.txt")).stream()
      .map(l -> l.split(";"))
      .forEach(p -> pr.addProduct(new Product(
        new BigDecimal(p[0].trim()),
        Integer.parseInt(p[1].trim()),
        p[2].trim(),
        p[3].trim(),
        p[4].trim(),
        p[5].trim(),
        p[6].trim()
      )));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
