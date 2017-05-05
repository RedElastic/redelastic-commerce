package core.product.api;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> lookupProducts();
    Product getProduct(UUID productId);

}
