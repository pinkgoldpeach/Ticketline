package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.AreaDto;
import at.ac.tuwien.inso.ticketline.dto.RoomDto;

import java.util.List;

/**
 * Created by Hannes on 16.05.2016.
 */
public interface AreaService {

    /**
     * Gets all areas in a room
     *
     * @param room to search by
     * @return list of areas
     * @throws ServiceException on error
     */
    List<AreaDto> getAreasByRoom(RoomDto room) throws ServiceException;

    Integer getNumberOfAvailableTickets(Integer showID, Integer areaID) throws ServiceException;
}
