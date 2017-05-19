package core.order.api;

public class ShippingInfo {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String street;
    private String apartmentNum;
    private String city;
    private String province;
    private String postalCode;

    public ShippingInfo(
            String firstName,
            String lastName,
            String emailAddress,
            String street,
            String apartmentNum,
            String city,
            String province,
            String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.street = street;
        this.apartmentNum = apartmentNum;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }

    // Added Constructor
    public ShippingInfo() {
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

    public String getStreet() {
        return street;
    }

    public String getApartmentNum() {
        return apartmentNum;
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
