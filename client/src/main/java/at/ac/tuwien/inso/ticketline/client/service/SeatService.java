package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.*;

import java.util.List;

/**
 * Created by Hannes on 17.05.2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
public interface SeatService {

    List<SeatDto> getSeatsByRow(RowDto row) throws ServiceException;

    List<SeatDto> getSeatsByRoom(RoomDto room) throws ServiceException;

    //-------------------

    UsedSeatsDto getLockedSeats(Integer showId) throws ServiceException;

    MessageDto lockSeats(UsedSeatsDto usedSeats) throws ServiceException;

    MessageDto unlockSeats(Integer showId) throws ServiceException;

    MessageDto unlockSeat(Integer showId, Integer seatId) throws ServiceException;

    UsedSeatsDto getSoldSeats(Integer showId) throws ServiceException;

    UsedSeatsDto getReservedSeats(Integer showId) throws ServiceException;
}
