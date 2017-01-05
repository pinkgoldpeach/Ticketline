package at.ac.tuwien.inso.ticketline.client.util;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.service.RoomService;
import at.ac.tuwien.inso.ticketline.dto.ShowDto;
import javafx.beans.property.*;

/**
 * Wrapper class to fill table with data
 */
public class UIShowWithPrice extends UIShow {

    private StringProperty priceRange;
    private DoubleProperty minPrice;
    private DoubleProperty maxPrice;

    public UIShowWithPrice(ShowDto showDto, RoomService roomService) throws ServiceException {
        super(showDto);

        if(showDto.getRoom() != null){
            String stringPriceRange = roomService.getRoomPriceRange(showDto.getRoom());
            priceRange = new SimpleStringProperty(stringPriceRange);

            int first = stringPriceRange.indexOf("-");

            String firstValue = stringPriceRange.substring(0, first-2);
            String secondValue = stringPriceRange.substring((first+2), stringPriceRange.length()-1);

            try{
                firstValue.replace(" ", "").replace(",", ".");
                double min = Double.parseDouble(firstValue);
                minPrice = new SimpleDoubleProperty(min);
            } catch (NumberFormatException ex){
                minPrice = new SimpleDoubleProperty(0);
            }

            try{
                secondValue.replace(" ", "").replace(",", ".");
                double max = Double.parseDouble(secondValue);
                maxPrice = new SimpleDoubleProperty(max);
            } catch (NumberFormatException ex){
                maxPrice = new SimpleDoubleProperty(0);
            }

        } else {
            priceRange = new SimpleStringProperty("0.00€");
            minPrice = new SimpleDoubleProperty(0);
            maxPrice = new SimpleDoubleProperty(0);
        }

    }

    public StringProperty getPriceRange() {
        return priceRange;
    }

    public double getMaxPrice() {
        return maxPrice.getValue();
    }

    public double getMinPrice() {
        return minPrice.getValue();
    }
}