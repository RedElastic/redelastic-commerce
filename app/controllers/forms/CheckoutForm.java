package controllers.forms;

import play.data.validation.Constraints;

public class CheckoutForm {

    @Constraints.Required protected String firstName;
    @Constraints.Required protected String lastName;
    @Constraints.Required protected String emailAddress; // TODO email address validator
    @Constraints.Required protected String shippingOptions;
    @Constraints.Required protected String street;
    @Constraints.Required protected String city;
    @Constraints.Required protected String province;
    @Constraints.Required protected String postalCode; // TODO postal code validator

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getShippingOptions() {
        return shippingOptions;
    }

    public void setShippingOptions(String shippingOptions) {
        this.shippingOptions = shippingOptions;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
