package Services;


import models.Product;
import java.util.List;

public interface ShippingService {
    List<Product> getShippableItems(List<Product> items);
}
