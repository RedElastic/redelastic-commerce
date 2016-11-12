package contexts.product.stub;

import contexts.product.api.Product;
import contexts.product.api.ProductService;

import java.util.Arrays;
import java.util.List;

public class ProductServiceStub implements ProductService {

    public List<Product> lookupProducts() {
        return Arrays.asList(
            new Product(1, "http://i5.walmartimages.ca/images/Thumbnails/785/655/999999-27045785655.jpg", "Hamilton Beach 10-cup Coffeemaker", "Drip", ""),
            new Product(1, "http://i5.walmartimages.ca/images/Thumbnails/_83/159/83159.jpg", "Super Coffee Supreme Maker", "Espresso", ""),
            new Product(1, "http://i5.walmartimages.ca/images/Thumbnails/794/272/794272.jpg", "Big Bob's Deluxe Drip", "Drip", ""),
            new Product(1, "http://i5.walmartimages.ca/images/Thumbnails/124/458/124458.jpg", "Frankenstein's Half and Halfer", "Kettle", ""),
            new Product(1, "http://i5.walmartimages.ca/images/Thumbnails/316/290/1316290.jpg", "Dogwood Coffee Bonanza", "Drip", ""),
            new Product(1, "http://i5.walmartimages.ca/images/Thumbnails/794/374/999999-27045794374.jpg", "Biggest Coffee Maker Ever", "Espresso", ""),
            new Product(1, "http://i5.walmartimages.ca/images/Thumbnails/067/102/999999-76753067102.jpg", "Big Bob's Deluxe Drip", "Espresso", ""),
            new Product(1, "http://i5.walmartimages.ca/images/Thumbnails/233/886/999999-72179233886.jpg", "Frankenstein's Half and Halfer", "Kettle", "")
        );
    }

    public List<Product> topProducts() { return null; }

}
