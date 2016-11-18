package contexts.order.stub;

import contexts.order.api.Order;
import contexts.order.api.OrderService;

public class OrderServiceStub implements OrderService {
    @Override
    public Long saveOrder(Order order) {
        return 1l;
    }

    @Override
    public Order findOrder(Long id) {
        return null;
    }
}
