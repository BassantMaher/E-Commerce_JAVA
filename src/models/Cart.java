package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private List<Product> items;
    private Map<Product, Integer> quantities;

    public Cart() {
        this.items = new ArrayList<>();
        this.quantities = new HashMap<>();
    }

    public void add(Product product, int quantity) {
        if (!product.isExpired() && quantity > 0 && quantity <= product.getStock()) {
            items.add(product);
            quantities.put(product, quantities.getOrDefault(product, 0) + quantity);
            product.setStock(product.getStock() - quantity);
        } else if (quantity > product.getStock()) {
            System.out.println("Not enough stock for " + product.getName() + ". Available: " + product.getStock());
        }
    }

    public double calculateSubtotal() {
        double total = 0;
        for (Product p : items) {
            total += p.getPrice() * quantities.get(p);
        }
        return total;
    }

    public List<Product> getItems() {
        return new ArrayList<>(items);
    }

    public Map<Product, Integer> getQuantities() {
        return new HashMap<>(quantities);
    }
}