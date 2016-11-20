package contexts.order.api;

public class Order {

    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final String shippingOption;
    private final String street;
    private final String city;
    private final String province;
    private final String postalCode;

    public Order(String firstName,
                 String lastName,
                 String emailAddress,
                 String shippingOption,
                 String street,
                 String city,
                 String province,
                 String postalCode) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.shippingOption = shippingOption;
        this.street = street;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getShippingOption() {
        return shippingOption;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
