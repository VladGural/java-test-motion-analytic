/**
 * @author Vladyslav Gural
 * @version 2024-11-27
 */
package pro.gural.analytic.address;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

class AddressId implements Serializable {

    private String eventId;

    private String id;

    public String getEventId() {
        return eventId;
    }

    public AddressId setEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public String getId() {
        return id;
    }

    public AddressId setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "CompanyId{" +
                "eventId='" + eventId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

@Entity
@IdClass(AddressId.class)
@Table(name = "company_address")
class AddressEntity {
    @Id
    @Column(name = "event_id")
    private String eventId;

    @Id
    private String id;

    @Column(name = "company_id")
    private String companyId;

    private String country;

    private String city;

    private String street;

    private String zip;

    @Column(name = "category")
    private String addressCategory;

    @Column(name = "event_time")
    private Instant eventTime;

    public String getEventId() {
        return eventId;
    }

    public AddressEntity setEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public String getId() {
        return id;
    }

    public AddressEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public AddressEntity setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public AddressEntity setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public AddressEntity setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public AddressEntity setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getAddressCategory() {
        return addressCategory;
    }

    public AddressEntity setAddressCategory(String addressCategory) {
        this.addressCategory = addressCategory;
        return this;
    }

    public Instant getEventTime() {
        return eventTime;
    }

    public AddressEntity setEventTime(Instant eventTime) {
        this.eventTime = eventTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return Objects.equals(eventId, that.eventId) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, id);
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "eventId='" + eventId + '\'' +
                ", id='" + id + '\'' +
                ", companyId='" + companyId + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zip='" + zip + '\'' +
                ", addressCategory='" + addressCategory + '\'' +
                ", eventTime=" + eventTime +
                '}';
    }
}