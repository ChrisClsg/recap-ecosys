package de.clsg;

import java.util.Map;
import java.util.Scanner;

public class ConsoleApp {

  public static void run() {
    ProductRepo pr = new ProductRepo();
    Seeder.seed(pr);

    OrderMapRepo or = new OrderMapRepo();
    ShopService shop = new ShopService(pr, or);

    Scanner sc = new Scanner(System.in);

    System.out.println(Ansi.title("Shop CLI") + " – Type " + Ansi.info("help") + " for an overview of the commands");

    while (true) {
      System.out.print(Ansi.BLUE + "> " + Ansi.RESET);
      if (!sc.hasNextLine()) break;

      String line = sc.nextLine().trim();
      if (line.isBlank()) continue;

      String[] parts = line.split("\\s+");
      String cmd = parts[0].toLowerCase();

      try {
        switch (cmd) {
          case "help" -> printHelp();

          case "products" -> {
            System.out.println(Ansi.title("Produkte"));
            pr.getAll().forEach(p -> {
              String badge = (p.stock() == 0)
                  ? Ansi.err("OUT")
                  : (p.stock() < 5 ? Ansi.warn("LOW") : Ansi.ok("OK"));
              System.out.println(" - " + Ansi.info(p.id()) + " | " + p.name()
                  + " | " + p.category()
                  + " | stock=" + p.stock() + " " + badge);
            });
          }

          case "product" -> {
            if (parts.length != 2) { System.out.println(Ansi.warn("usage: product <id>")); break; }

            pr.getById(parts[1]).ifPresentOrElse(
              p -> {
                System.out.println(Ansi.title("Produkt"));
                System.out.println(p);
              },
              () -> System.out.println(Ansi.err("not found: ") + parts[1])
            );
          }

          case "stock" -> {
            if (parts.length != 2) { System.out.println(Ansi.warn("usage: stock <id>")); break; }

            pr.getById(parts[1]).ifPresentOrElse(
              p -> {
                String badge = (p.stock() == 0)
                ? Ansi.err("OUT")
                : (p.stock() < 5 ? Ansi.warn("LOW") : Ansi.ok("OK"));
                System.out.println("stock=" + p.stock() + " " + badge);
              },
              () -> System.out.println(Ansi.err("not found: ") + parts[1])
            );
          }

          case "order" -> {
            if (parts.length != 3) { System.out.println(Ansi.warn("usage: order <productId> <qty>")); break; }
            String productId = parts[1];
            int qty = Integer.parseInt(parts[2]);

            Order o = shop.placeOrder(qty, productId);
            System.out.println(o == null ? Ansi.err("order failed") : Ansi.ok("order ok: ") + o);
          }

          case "receive" -> {
            if (parts.length != 3) { System.out.println(Ansi.warn("usage: receive <productId> <qty>")); break; }
            String productId = parts[1];
            int qty = Integer.parseInt(parts[2]);

            boolean ok = pr.increaseStock(productId, qty);
            System.out.println(ok ? Ansi.ok("ok") : Ansi.err("failed"));
          }

          case "ship" -> {
            if (parts.length != 3) { System.out.println(Ansi.warn("usage: ship <productId> <qty>")); break; }
            String productId = parts[1];
            int qty = Integer.parseInt(parts[2]);

            boolean ok = pr.decreaseStock(productId, qty);
            System.out.println(ok ? Ansi.ok("ok") : Ansi.err("failed"));
          }

          case "orders" -> {
            if (parts.length != 1 && parts.length != 2) {
              System.out.println(Ansi.warn("usage: orders (Optional: <ORDERSTATUS: PROCESSING | IN_DELIVERY | COMPLETED>)"));
              break;
            }
            System.out.println(Ansi.title("Orders"));
            if (parts.length == 1) {
              if (or.getAll().isEmpty()) {
                System.out.println(Ansi.warn("no orders yet"));
              } else {
                or.getAll().forEach(o -> System.out.println(" - " + Ansi.info(o.id()) + " | " + o.productId() + " x " + o.quantity() + " | " + o.status() + " | " + o.createdAt()));
              }
            } else {
              try {
                OrderStatus status = OrderStatus.valueOf(parts[1].toUpperCase());

                if (or.getAllWithStatus(status).isEmpty()) {
                  System.out.println(Ansi.warn("no orders with this status"));
                } else {
                  or.getAllWithStatus(status).forEach(o -> System.out.println(" - " + Ansi.info(o.id()) + " | " + o.productId() + " x " + o.quantity() + " | " + o.status() + " | " + o.createdAt()));
                }
              } catch (IllegalArgumentException e) {
                System.out.println(Ansi.err("invalid status: ") + parts[2]);
                System.out.println("Allowed: " + java.util.Arrays.toString(OrderStatus.values()));
              }
            }
          }

          case "updateorderstatus" -> {
            if (parts.length != 3) {
              System.out.println(Ansi.warn("usage: updateorderstatus <id> <newStatus>"));
              break;
            }

            try {
              OrderStatus newStatus = OrderStatus.valueOf(parts[2].toUpperCase());
              Order o = or.getById(parts[1]);

              Order updated = shop.updateOrderStatus(o, newStatus);

              if (updated == null) {
                System.out.println(Ansi.err("not found: ") + parts[1]);
              } else {
                System.out.println(Ansi.title("Order updated to:"));
                System.out.println(updated);
              }
            } catch (IllegalArgumentException e) {
              System.out.println(Ansi.err("invalid status: ") + parts[2]);
              System.out.println("Allowed: " + java.util.Arrays.toString(OrderStatus.values()));
            }
          }

          case "oldestorders" -> {
            if (parts.length != 1) {
              System.out.println(Ansi.warn("usage: oldestorders"));
              break;
            }

            Map<OrderStatus, Order> map = or.getOldestOrderPerStatus();
            map.forEach((key, val) -> {
              System.out.println(Ansi.title("Oldest " + key + " order: "));
              System.out.println(" - " + Ansi.info(val.id()) + " | " + val.productId() + " x " + val.quantity() + " | " + val.status() + " | " + val.createdAt());
            });
          }

          case "exit" -> {
            System.out.println(Ansi.ok("bye"));
            sc.close();
            return;
          }

          default -> System.out.println(Ansi.warn("unknown command (type 'help')"));
        }
      } catch (Exception e) {
        System.out.println(Ansi.err("ERROR: ") + e.getMessage());
      }
    }
  }

  private static void printHelp() {
    System.out.println("""
Commands:
  help
  products
  product <id>
  stock <id>
  order <productId> <qty>
  receive <productId> <qty>
  ship <productId> <qty>
  orders (Optional: <ORDERSTATUS: PROCESSING | IN_DELIVERY | COMPLETED>)
  oldestorders
  updateorderstatus <orderId> <NEW_STATUS> (PROCESSING, IN_DELIVERY, COMPLETED)
  exit
""");
  }
}