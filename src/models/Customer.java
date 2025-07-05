package models;

public class Customer {
    private double balance;

    public Customer(double balance) {
        this.balance = balance;
    }

    public boolean checkout(Cart cart) {
        double subtotal = cart.calculateSubtotal();
        double shippingFee = calculateShippingFee(cart);
        double amount = subtotal + shippingFee;

        if (balance >= amount && !cart.getItems().isEmpty()) {
            balance -= amount;
            printCheckoutDetails(cart, shippingFee, amount);
            return true;
        } else if (cart.getItems().isEmpty()) {
            System.out.println("Cart is empty");
            return false;
        } else {
            System.out.println("Customer balance is insufficient");
            return false;
        }
    }

    private double calculateShippingFee(Cart cart) {
        double fee = 0;
        for (Product p : cart.getItems()) {
            if (p.isRequiresShipping()) {
                fee += 15; 
            }
        }
        return fee;
    }

    private void printCheckoutDetails(Cart cart, double shippingFee, double amount) {
        System.out.println("** Shipment notice **");
        for (Product p : cart.getItems()) {
            System.out.println(cart.getQuantities().get(p) + "x " + p.getName() + " " + (p.getWeight() * cart.getQuantities().get(p) * 1000) + "g");
        }
        System.out.println("Total package weight " + calculateTotalWeight(cart) + "kg");
        System.out.println("** Checkout receipt **");
        for (Product p : cart.getItems()) {
            System.out.println(cart.getQuantities().get(p) + "x " + p.getName() + " " + (p.getPrice() * cart.getQuantities().get(p)));
        }
         System.out.println("------------");
        System.out.println("Subtotal " + cart.calculateSubtotal());
        System.out.println("Shipping " + shippingFee);
        System.out.println("Amount " + amount);
        System.out.println("Customer current balance after payment " + balance);
    }

    private double calculateTotalWeight(Cart cart) {
        double total = 0;
        for (Product p : cart.getItems()) {
            if (p.isRequiresShipping()) {
                total += p.getWeight() * cart.getQuantities().get(p);
            }
        }
        return total;
    }
}