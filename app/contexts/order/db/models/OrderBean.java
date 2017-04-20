package contexts.order.db.models;

import javax.persistence.*;

import contexts.order.api.OrderedItem;
import contexts.order.api.ShippingInfo;
import play.data.validation.*;

import java.util.List;

@Entity
public class OrderBean extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @Constraints.Required
    public ShippingInfo shippingInfo;

    @Constraints.Required
    public List<OrderedItem> items;

    public static Find<Long,OrderBean> find = new Find<Long,OrderBean>(){};

}