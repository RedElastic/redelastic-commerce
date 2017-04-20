package contexts.order.db;

import contexts.order.api.Order;
import contexts.order.api.OrderService;
import contexts.order.db.models.OrderBean;

/**
 * Implementation details, such as ebean entities, should never "leak" out of the live package.
 * These are implementation details that must be kept isolated in order to move to microservices, etc.
 * Adhere to API spec and only accept/return API pojos.
 */
public class OrderServiceLive implements OrderService {

    @Override
    public void placeOrder(Order order) {
        OrderBean bean = new OrderBean();
        bean.orderId = "666";
        bean.firstName = order.getFirstName();
        bean.lastName = order.getLastName();
        bean.emailAddress = order.getEmailAddress();
        bean.street = order.getStreet();
        bean.apartmentNum = order.getApartmentNum();
        bean.city = order.getCity();
        bean.province = order.getProvince();
        bean.postalCode = order.getPostalCode();
        bean.insert();
    }
}
