package core.product.stub;

import core.product.api.Price;
import core.product.api.Product;
import core.product.api.ProductService;

import java.util.*;
import java.util.stream.Collectors;

public class ProductServiceStub implements ProductService {

    Map<UUID, Product> productsMap = new HashMap<>();

    public ProductServiceStub() {
        productsMap.put(UUID.fromString("123e4567-e89b-12d3-a456-426655440000"), new Product(UUID.fromString("123e4567-e89b-12d3-a456-426655440000"), "http://i5.walmartimages.ca/images/Thumbnails/785/655/999999-27045785655.jpg", "Hamilton Beach 10-cup Coffeemaker", "The best worst coffee maker you've ever owned", new Price(10, 99)));
        productsMap.put(UUID.fromString("456e4567-e89b-12d3-a456-426655440001"), new Product(UUID.fromString("456e4567-e89b-12d3-a456-426655440001"), "http://i5.walmartimages.ca/images/Thumbnails/_83/159/83159.jpg", "Super Coffee Supreme Maker", "Makes the finest espresso you've never had", new Price(55, 99)));
        productsMap.put(UUID.fromString("789e4567-e89b-12d3-a456-426655440002"), new Product(UUID.fromString("789e4567-e89b-12d3-a456-426655440002"), "http://i5.walmartimages.ca/images/Thumbnails/794/272/794272.jpg", "Big Bob's Deluxe Drip", "When you don't care what it tastes like but you need a lot", new Price(5, 99)));
        productsMap.put(UUID.fromString("123f4567-e89b-12d3-a456-426655440003"), new Product(UUID.fromString("123f4567-e89b-12d3-a456-426655440003"), "http://i5.walmartimages.ca/images/Thumbnails/124/458/124458.jpg", "Frankenstein's Half and Halfer", "This is the weirdest coffee you've ever had", new Price(500, 99)));
        productsMap.put(UUID.fromString("456f4567-e89b-12d3-a456-426655440004"), new Product(UUID.fromString("456f4567-e89b-12d3-a456-426655440004"), "http://i5.walmartimages.ca/images/Thumbnails/316/290/1316290.jpg", "Dogwood Coffee Bonanza", "Only the finest hipster coffee. Tastes like wet dog and wetter wood.", new Price(42, 99)));
        productsMap.put(UUID.fromString("789f4567-e89b-12d3-a456-426655440005"), new Product(UUID.fromString("789f4567-e89b-12d3-a456-426655440005"), "http://i5.walmartimages.ca/images/Thumbnails/794/374/999999-27045794374.jpg", "Biggest Coffee Maker Ever", "When you count how many coffees you've had per day in pots, not cups.", new Price(49, 99)));
    }

    public List<Product> lookupProducts() {
        return productsMap.values().stream().collect(Collectors.toList());
    }

    public Product getProduct(UUID id) {
        return productsMap.get(id);
    }

}
