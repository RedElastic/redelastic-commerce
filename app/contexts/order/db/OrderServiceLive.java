package contexts.order.live;

import contexts.order.api.Order;
import contexts.order.api.OrderService;
import contexts.order.live.models.OrderBean;

/**
 * Implementation details, such as ebean entities, should never "leak" out of the live package.
 * These are implementation details that must be kept isolated in order to move to microservices, etc.
 * Adhere to API spec and only accept/return API pojos.
 */
public class OrderServiceLive implements OrderService {
    @Override
    public Long saveOrder(Order order) {
        OrderBean bean = new OrderBean();
        bean.firstName = order.getFirstName();
        bean.lastName = order.getLastName();
        bean.emailAddress = order.getEmailAddress();
        bean.shippingOption = order.getShippingOption();
        bean.street = order.getStreet();
        bean.city = order.getCity();
        bean.province = order.getProvince();
        bean.postalCode = order.getPostalCode();
        bean.insert();
        return bean.id;
    }

    @Override
    public Order findOrder(Long id) {
        throw new UnsupportedOperationException();
    }
}
