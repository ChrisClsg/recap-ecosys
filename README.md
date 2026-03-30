# Shop CLI

A command-line interface application for managing products and orders. This project demonstrates object-oriented programming principles with a shop management system.

## Prerequisites

- Java 25 or higher
- Maven 3.6 or higher

## Building the Project

To compile and build the project:

```bash
mvn clean compile
```

Or to also run tests:

```bash
mvn clean verify
```

To package the project as a JAR:

```bash
mvn package
```

## Running the Application

Start the Shop CLI:

```bash
mvn clean compile exec:java -Dexec.mainClass="de.clsg.App"
```

Or, if you've already packaged the project:

```bash
java -cp target/recap-oop-1.0-SNAPSHOT.jar de.clsg.App
```

Once running, the CLI will display a prompt and wait for commands.

## Available Commands

### General

| Command | Description |
|---------|-------------|
| `help` | Display all available commands |
| `exit` | Exit the application |

### Product Management

| Command | Usage | Description |
|---------|-------|-------------|
| `products` | `products` | List all products with stock status (OK, LOW, OUT) |
| `product` | `product <id>` | Get detailed information about a specific product |
| `stock` | `stock <id>` | Check stock level of a product |
| `receive` | `receive <productId> <qty>` | Increase stock of a product (e.g., `receive P001 10`) |
| `ship` | `ship <productId> <qty>` | Decrease stock of a product (e.g., `ship P001 5`) |

### Order Management

| Command | Usage | Description |
|---------|-------|-------------|
| `order` | `order <productId> <qty>` | Place a new order (e.g., `order P001 3`) |
| `orders` | `orders [STATUS]` | List all orders, optionally filtered by status |
| `updateorderstatus` | `updateorderstatus <orderId> <newStatus>` | Update order status (e.g., `updateorderstatus ORD001 IN_DELIVERY`) |
| `oldestorders` | `oldestorders` | Show the oldest order for each order status |

### Order Statuses

Orders can have the following statuses:
- `PROCESSING` - Order is being prepared
- `IN_DELIVERY` - Order is on its way
- `COMPLETED` - Order has been delivered

### Example Workflow

```
> help
> products
> product P001
> order P001 2
> orders
> orders PROCESSING
> updateorderstatus ORD001 IN_DELIVERY
> orders IN_DELIVERY
> updateorderstatus ORD001 COMPLETED
> exit
```

## Project Structure

```
src/
├── main/java/de/clsg/
│   ├── App.java                  # Entry point
│   ├── ConsoleApp.java           # CLI implementation
│   ├── ShopService.java          # Business logic
│   ├── Product.java              # Product entity
│   ├── Order.java                # Order entity
│   ├── OrderStatus.java          # Order status enum
│   ├── ProductRepo.java          # Product repository
│   ├── OrderMapRepo.java         # Order repository (Map-based)
│   ├── OrderListRepo.java        # Order repository (List-based)
│   ├── OrderRepoInterface.java   # Repository interface
│   ├── Seeder.java               # Database seed data
│   ├── Ansi.java                 # ANSI color formatting utility
│   └── productSeed.txt           # Product seed data file
└── test/java/de/clsg/            # Unit tests

```

## Technologies

- **Language**: Java 25
- **Build Tool**: Maven
- **Testing**: JUnit 5
- **Utilities**: Lombok
- **Color Output**: ANSI color codes

## Testing

Run all tests:

```bash
mvn test
```

Run a specific test class:

```bash
mvn test -Dtest=ProductRepoTest
```

## Notes

- The CLI persists only during the current session. Data is not saved between runs.
- Product inventory data is seeded from `productSeed.txt` on startup.
- Stock levels are tracked in real-time and updated as orders are placed and shipments are received.
