package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.RoomDto;
import at.ac.tuwien.inso.ticketline.dto.RowDto;

import java.util.List;

/**
 * Created by Hannes on 16.05.2016.
 */
public interface RowService {

    /**
     * Gets all rows in a room
     *
     * @param room to be searched by
     * @return list of rows
     * @throws ServiceException on error
     */
    List<RowDto> getRowsByRoom(RoomDto room) throws ServiceException;
}
