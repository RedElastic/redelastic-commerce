package contexts.product.api;

import java.util.List;

public interface ProductService {

    List<Product> lookupProducts();
    List<Product> topProducts();

}
