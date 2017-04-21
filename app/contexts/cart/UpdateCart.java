package contexts.cart;

import contexts.product.api.Product;
import javaslang.Tuple2;
import javaslang.collection.Set;

import java.io.Serializable;

public class UpdateCart implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Set<Tuple2<Product, Integer>> contents;

    public UpdateCart(Set<Tuple2<Product, Integer>> contents) {
        this.contents = contents;
    }

    public Set<Tuple2<Product, Integer>> getContents() {
        return contents;
    }
}
