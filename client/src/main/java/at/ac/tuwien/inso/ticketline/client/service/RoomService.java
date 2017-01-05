package at.ac.tuwien.inso.ticketline.client.service;


import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.RoomDto;

public interface RoomService {

    /**
     * returns the priceRange of the room
     *
     * @param roomDto find pricerange for this room
     * @return a String containing the priceRange
     * @throws ServiceException if connection to server went wrong
     */
    String getRoomPriceRange(RoomDto roomDto) throws ServiceException;
}
