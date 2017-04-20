package contexts.order.db.models;

import javax.persistence.*;

import play.data.validation.*;

@Entity
public class OrderBean extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @Constraints.Required
    public String orderId;

    @Constraints.Required
    public String firstName;

    @Constraints.Required
    public String lastName;

    @Constraints.Required
    public String emailAddress;

    @Constraints.Required
    public String shippingOption;

    @Constraints.Required
    public String street;

    public String apartmentNum;

    @Constraints.Required
    public String city;

    @Constraints.Required
    public String province;

    @Constraints.Required
    public String postalCode;

    public static Find<Long,OrderBean> find = new Find<Long,OrderBean>(){};

}