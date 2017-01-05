package at.ac.tuwien.inso.ticketline.client.util;

import at.ac.tuwien.inso.ticketline.dto.AddressDto;
import at.ac.tuwien.inso.ticketline.dto.LocationDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Wrapper classe for UI, table view Location
 */
public class UILocation implements UIObject {

    private LocationDto origin;
    private StringProperty name;
    private StringProperty street;
    private StringProperty city;
    private StringProperty land;
    private StringProperty postal;
    private StringProperty roomName;


    public UILocation(LocationDto locationDto) {
        AddressDto adress = locationDto.getAddress();
        this.origin = locationDto;
        this.name = new SimpleStringProperty(locationDto.getName());
        if (adress != null) {
            this.street = new SimpleStringProperty(adress.getStreet());
            this.city = new SimpleStringProperty(adress.getCity());
            this.land = new SimpleStringProperty(adress.getCountry());
            this.postal = new SimpleStringProperty(adress.getPostalCode());
        } else {
            this.street = new SimpleStringProperty("unknown");
            this.city = new SimpleStringProperty("unknown");
            this.land = new SimpleStringProperty("unknown");
            this.postal = new SimpleStringProperty(BundleManager.getBundle().getString("generic.unknown"));
        }
        if (locationDto.getRooms().isEmpty()) {
            roomName = new SimpleStringProperty(BundleManager.getBundle().getString("rooms.none"));
        } else if (locationDto.getRooms().size()==1){
            roomName = new SimpleStringProperty(locationDto.getRooms().get(0).getName());
        } else {
            roomName = new SimpleStringProperty(BundleManager.getBundle().getString("rooms.several"));
        }
    }

    public LocationDto getLocation() {
        return origin;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty landProperty() {
        return land;
    }

    public StringProperty postalProperty() {
        return postal;
    }

    public StringProperty roomName() {
        return roomName;
    }

    @Override
    public Integer getID() {
        return origin.getId();
    }
}