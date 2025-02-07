	import java.io.*;
	import java.util.*;
	import java.util.concurrent.*;

	class Product implements Serializable {
	    private String productID, name;
	    private int quantity;
	    private Location location;

	    public Product(String productID, String name, int quantity, Location location) {
	        this.productID = productID;
	        this.name = name;
	        this.quantity = quantity;
	        this.location = location;
	    }

	    public String getProductID() { return productID; }
	    public String getName() { return name; }
	    public int getQuantity() { return quantity; }
	    public Location getLocation() { return location; }
	    public void setQuantity(int quantity) { this.quantity = quantity; }
	}

	class Location implements Serializable {
	    private int aisle, shelf, bin;

	    public Location(int aisle, int shelf, int bin) {
	        this.aisle = aisle;
	        this.shelf = shelf;
	        this.bin = bin;
	    }

	    public int getAisle() { return aisle; }
	    public int getShelf() { return shelf; }
	    public int getBin() { return bin; }
	}

	class Order {
	    private String orderID;
	    private List<String> productIDs;
	    private Priority priority;

	    public enum Priority {
	        STANDARD, EXPEDITED
	    }

	    public Order(String orderID, List<String> productIDs, Priority priority) {
	        this.orderID = orderID;
	        this.productIDs = productIDs;
	        this.priority = priority;
	    }

	    public String getOrderID() { return orderID; }
	    public List<String> getProductIDs() { return productIDs; }
	    public Priority getPriority() { return priority; }
	}

	class OutOfStockException extends Exception {
	    public OutOfStockException(String message) { super(message); }
	}
	class InvalidLocationException extends Exception {
	    public InvalidLocationException(String message) { super(message); }
	}

	class InventoryManager {
	    private ConcurrentHashMap<String, Product> products = new ConcurrentHashMap<>();
	    private PriorityBlockingQueue<Order> orderQueue = new PriorityBlockingQueue<>(
	            10, Comparator.comparing(Order::getPriority));

	    public synchronized void addProduct(Product product) {
	        products.put(product.getProductID(), product);
	    }

	    public void processOrders() {
	        ExecutorService executor = Executors.newFixedThreadPool(3);
	        while (!orderQueue.isEmpty()) {
	            executor.execute(() -> {
	                try {
	                    Order order = orderQueue.poll();
	                    if (order != null) fulfillOrder(order);
	                } catch (Exception e) {
	                    System.out.println("Error processing order: " + e.getMessage());
	                }
	            });
	        }
	        executor.shutdown();
	    }

	    private void fulfillOrder(Order order) throws OutOfStockException {
	        for (String productID : order.getProductIDs()) {
	            Product product = products.get(productID);
	            if (product == null || product.getQuantity() == 0) {
	                throw new OutOfStockException("Product " + productID + " is out of stock");
	            }
	            product.setQuantity(product.getQuantity() - 1);
	        }
	        System.out.println("Order " + order.getOrderID() + " fulfilled.");
	    }
	}

	public class WarehouseInventoryManagement {
	    public static void main(String[] args) {
	        InventoryManager inventoryManager = new InventoryManager();
	        inventoryManager.addProduct(new Product("P001", "Laptop", 10, new Location(1, 2, 3)));
	        inventoryManager.addProduct(new Product("P002", "Mouse", 20, new Location(2, 1, 5)));

	        List<String> productList = Arrays.asList("P001", "P002");
	        Order order1 = new Order("O001", productList, Order.Priority.EXPEDITED);
	        Order order2 = new Order("O002", Collections.singletonList("P001"), Order.Priority.STANDARD);
	        
	        inventoryManager.processOrders();
	    }
	}
