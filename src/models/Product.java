package models;

public class Product {
    private String name;
    private double price;
    private boolean requiresShipping;
    private double weight; 
    private int stock; 
    private boolean isExpired;

    public Product(String name, double price, boolean requiresShipping, double weight, int stock, boolean isExpired) {
        this.name = name;
        this.price = price;
        this.requiresShipping = requiresShipping;
        this.weight = weight;
        this.stock = stock;
        this.isExpired = isExpired;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    public boolean isRequiresShipping() {
        return requiresShipping;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isExpired() {
        return isExpired;
    }
}