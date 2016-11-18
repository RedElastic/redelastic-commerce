package contexts.order.api;

public interface OrderService {
    Long saveOrder(Order order);
    Order findOrder(Long id);
}
