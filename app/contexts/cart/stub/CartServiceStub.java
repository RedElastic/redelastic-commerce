package contexts.cart.stub;

import contexts.cart.api.Cart;
import contexts.cart.api.CartService;
import contexts.cart.api.LineItem;
import contexts.pricing.api.Price;
import contexts.product.api.Product;

import java.util.Arrays;

public class CartServiceStub implements CartService {
    @Override
    public Cart getCartForUser() {
        return new Cart("1", Arrays.asList(
                new LineItem(new Product(1, "1000196120001", "http://i5.walmartimages.ca/images/Thumbnails/785/655/999999-27045785655.jpg", "Hamilton Beach 10-cup Coffeemaker", "Drip"), 1, new Price(99, 99)),
                new LineItem(new Product(3, "3000196120003", "http://i5.walmartimages.ca/images/Thumbnails/794/272/794272.jpg", "Big Bob's Deluxe Drip", "Drip"), 2, new Price(99, 99)),
                new LineItem(new Product(6, "6000196120006", "http://i5.walmartimages.ca/images/Thumbnails/794/374/999999-27045794374.jpg", "Biggest Coffee Maker Ever", "Espresso"), 1, new Price(99, 99))
            )
        );
    }
}
