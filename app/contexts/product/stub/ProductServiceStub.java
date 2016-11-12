package contexts.product.stub;

import contexts.product.api.Product;
import contexts.product.api.ProductService;

import java.util.Arrays;
import java.util.List;

public class ProductServiceStub implements ProductService {

    public List<Product> lookupProducts() {
        return Arrays.asList(
            new Product(1, "123456789", "Hamilton Beach 10-cup Coffeemaker", "d"),
            new Product(1, "123456789", "c", "d"),
            new Product(1, "123456789", "c", "d"),
            new Product(1, "123456789", "c", "d"),
            new Product(1, "123456789", "c", "d"),
            new Product(1, "123456789", "c", "d"),
            new Product(1, "123456789", "c", "d"),
            new Product(1, "123456789", "c", "d")
        );
    }

    public List<Product> topProducts() { return null; }

}
