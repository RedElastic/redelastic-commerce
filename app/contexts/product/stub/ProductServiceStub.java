package contexts.product.stub;

import contexts.product.api.Price;
import contexts.product.api.Product;
import contexts.product.api.ProductService;

import java.util.Arrays;
import java.util.List;

public class ProductServiceStub implements ProductService {

    public List<Product> lookupProducts() {
        return Arrays.asList(
            new Product(1l, "1000196120001", "http://i5.walmartimages.ca/images/Thumbnails/785/655/999999-27045785655.jpg", "Hamilton Beach 10-cup Coffeemaker", "The best worst coffee maker you've ever owned", new Price(10, 99)),
            new Product(2l, "2000196120002", "http://i5.walmartimages.ca/images/Thumbnails/_83/159/83159.jpg", "Super Coffee Supreme Maker", "Makes the finest espresso you've never had", new Price(10, 99)),
            new Product(3l, "3000196120003", "http://i5.walmartimages.ca/images/Thumbnails/794/272/794272.jpg", "Big Bob's Deluxe Drip", "When you don't care what it tastes like but you need a lot", new Price(10, 99)),
            new Product(4l, "4000196120004", "http://i5.walmartimages.ca/images/Thumbnails/124/458/124458.jpg", "Frankenstein's Half and Halfer", "This is the weirdest coffee you've ever had", new Price(10, 99)),
            new Product(5l, "5000196120005", "http://i5.walmartimages.ca/images/Thumbnails/316/290/1316290.jpg", "Dogwood Coffee Bonanza", "Only the finest hipster coffee. Tastes like wet dog.", new Price(10, 99)),
            new Product(6l, "6000196120006", "http://i5.walmartimages.ca/images/Thumbnails/794/374/999999-27045794374.jpg", "Biggest Coffee Maker Ever", "When you count how many coffees you've had per day in pots, not cups.", new Price(10, 99))
        );
    }

}
