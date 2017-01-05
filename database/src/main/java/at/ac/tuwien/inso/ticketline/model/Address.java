package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Embeddable address.
 */
@Embeddable
public class Address implements Serializable {

    private static final long serialVersionUID = 2120941944570241863L;

    @Column(nullable = false, length = 50)
    private String street;

    @Column(nullable = false, length = 25)
    private String postalCode;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 50)
    private String country;

    /**
     * Instantiates a new address.
     */
    public Address() {
    }

    /**
     * Instantiates a new address.
     *
     * @param street the street
     * @param postalCode the postal code
     * @param city the city
     * @param country the country
     */
    public Address(String street, String postalCode, String city, String country) {
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    /**
     * Gets the street.
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street.
     *
     * @param street the new street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code.
     *
     * @param postalCode the new postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country.
     *
     * @param country the new country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString(){
        return "street: " + this.street + ", zipCode: " + postalCode + ", city: " + this.city + ", country: " + this.country;
    }
}
