package at.ac.tuwien.inso.ticketline.client.util;

import at.ac.tuwien.inso.ticketline.dto.ArtistDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Wrapper for UI Artist Dto
 */
public class UIArtist implements UIObject {

    private ArtistDto origin;
    private StringProperty firstname;
    private StringProperty lastname;
    private StringProperty description;


    public UIArtist(ArtistDto artistDto) {
        this.origin = artistDto;
        this.lastname = new SimpleStringProperty(artistDto.getLastname());
        this.firstname = new SimpleStringProperty(artistDto.getFirstname());
        this.description = new SimpleStringProperty(artistDto.getDescription());

    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getFirstname() {
        return firstname.get();
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public String getLastname() {
        return lastname.get();
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }


    @Override
    public Integer getID() {
        return origin.getId();
    }
}