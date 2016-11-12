package contexts.product.live;

import contexts.product.api.Product;
import contexts.product.api.ProductService;

import java.util.List;

public class ProductServiceLive implements ProductService {

    @Override
    public List<Product> lookupProducts() {
        return null;
    }

    @Override
    public List<Product> topProducts() { return null; }

}
