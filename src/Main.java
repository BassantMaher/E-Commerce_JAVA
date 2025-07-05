
import models.Cart;
import models.Customer;
import models.Product;
import Services.ShippingService;

import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cart cart = new Cart();

        List<Product> availableProducts = new ArrayList<>();
        availableProducts.add(new Product("Cheese", 150.0, true, 0.9, 5, false)); // 5 in stock
        availableProducts.add(new Product("Biscuits", 150.0, true, 0.7, 3, false)); // 3 in stock
        availableProducts.add(new Product("ScratchCard", 30.0, false, 0.0, 10, false)); // 10 in stock
        availableProducts.add(new Product("TV", 500.0, true, 10.0, 2, false)); // 2 in stock

        System.out.println("Available Products:");
        for (int i = 0; i < availableProducts.size(); i++) {
            Product p = availableProducts.get(i);
            System.out.println(i + ". " + p.getName() + " - $" + p.getPrice() + " (Shipping: " + (p.isRequiresShipping() ? "Yes" : "No") + ", Stock: " + p.getStock() + ")");
        }

        double balance = 0;
        boolean validBalance = false;
        while (!validBalance) {
            System.out.print("Enter your balance: $");
            try {
                balance = scanner.nextDouble();
                if (balance >= 0) {
                    validBalance = true;
                } else {
                    System.out.println("Balance cannot be negative. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }

        Customer customer = new Customer(balance);

        boolean addMore = true;
        while (addMore) {
            System.out.print("Select product (enter number 0-" + (availableProducts.size() - 1) + ") or -1 to finish: ");
            int choice = -2;
            boolean validChoice = false;

            while (!validChoice) {
                try {
                    choice = scanner.nextInt();
                    if (choice == -1 || (choice >= 0 && choice < availableProducts.size())) {
                        validChoice = true;
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 0-" + (availableProducts.size() - 1) + " or -1 to finish.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next();
                }
            }

            if (choice == -1) {
                addMore = false;
            } else {
                System.out.print("Enter quantity for " + availableProducts.get(choice).getName() + " (Stock: " + availableProducts.get(choice).getStock() + "): ");
                int quantity = -1;
                boolean validQuantity = false;

                while (!validQuantity) {
                    try {
                        quantity = scanner.nextInt();
                        if (quantity > 0 && quantity <= availableProducts.get(choice).getStock()) {
                            validQuantity = true;
                            cart.add(availableProducts.get(choice), quantity);
                        } else {
                            System.out.println("Invalid quantity. Please enter a number between 1 and " + availableProducts.get(choice).getStock() + ".");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next(); 
                    }
                }
            }
        }
        boolean checkoutSuccess = customer.checkout(cart);
        if (!checkoutSuccess) {
            cart = new Cart();
        }

        ShippingService shippingService = new ShippingService() {
            @Override
            public List<Product> getShippableItems(List<Product> items) {
                List<Product> shippable = new ArrayList<>();
                for (Product p : items) {
                    if (p.isRequiresShipping()) {
                        shippable.add(p);
                    }
                }
                return shippable;
            }
        };
        List<Product> shippableItems = shippingService.getShippableItems(cart.getItems());
        System.out.println("Shippable items: " + shippableItems.size());

        scanner.close();
    }
}