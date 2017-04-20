package contexts.order.db.models;

import javax.persistence.*;

import contexts.order.api.OrderedItem;
import contexts.order.api.ShippingInfo;
import play.data.validation.*;

import java.util.List;
import java.util.UUID;

@Entity
public class OrderBean extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @Id
    public UUID id;

    //@Constraints.Required
    //public ShippingInfo shippingInfo;

    //@ManyToMany(cascade=CascadeType.ALL)
    //public List<OrderedItem> items;

    public static Find<Long,OrderBean> find = new Find<Long,OrderBean>(){};

}