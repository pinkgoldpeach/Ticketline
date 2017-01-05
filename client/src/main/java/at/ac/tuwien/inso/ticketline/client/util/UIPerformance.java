package at.ac.tuwien.inso.ticketline.client.util;

import at.ac.tuwien.inso.ticketline.dto.PerformanceDto;
import at.ac.tuwien.inso.ticketline.dto.PerformanceTypeDto;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;


/**
 * Wrapper classe for UI, table view
 */
public class UIPerformance implements UIObject {

    private PerformanceDto origin;
    private StringProperty name;
    private IntegerProperty duration;
    private StringProperty description;
    private StringProperty type;


    public UIPerformance(PerformanceDto performanceDto) {
        this.origin = performanceDto;
        this.name = new SimpleStringProperty(performanceDto.getName());
        this.duration = new SimpleIntegerProperty(performanceDto.getDuration());
        this.description = new SimpleStringProperty(performanceDto.getDescription());
        PerformanceTypeDto typeDto = performanceDto.getPerformanceType();
        if (typeDto != null) {
            this.type = new SimpleStringProperty(typeDto.toString());
        } else {
            this.type = new SimpleStringProperty("unknown");
        }

    }

    public PerformanceDto getPerformance() {
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


    public IntegerProperty durationProperty() {
        return duration;
    }


    public StringProperty descriptionProperty() {
        return description;
    }


    public StringProperty typeProperty() {
        return type;
    }

    @Override
    public Integer getID() {
        return origin.getId();
    }

}
