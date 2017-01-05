package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.*;

import java.util.List;

/**
 * Created by Hannes on 20.05.2016.
 */
public interface ReservationService {

    /**
     * returns a list of all reservations
     *
     * @return list of all reservations
     * @throws ServiceException if connection to server went wrong
     */
    List<ReservationDto> getAllReservations() throws ServiceException;

    /**
     * saves a reservations on the server and returns the id of the reservations
     *
     * @param reservation the reservations to save
     * @return id of the reservation encapsulated in a MessageDto
     * @throws ServiceException if connection to server went wrong
     */
    MessageDto saveReservation(ReservationPublishDto reservation) throws ServiceException;

    /**
     * gets a reservation with a certain reservationnumber
     *
     * @param number find reservation by number
     * @return reservation object with given number
     * @throws ServiceException if connection to server went wrong
     */
    ReservationDto findByReservationNumber(String number) throws ServiceException;

    /**
     * gets a reservation by the lastname of the customer
     *
     * @param lastname find reservation by lastname
     * @return reservation object with given lastname
     * @throws ServiceException if connection to server went wrong
     */
    List<ReservationDto> findReservationByLastname(String lastname) throws ServiceException;

    /**
     * @param cancelTicketDto cancel rservation with this ticket
     * @return message with indicates ic cancellation was successful
     * @throws ServiceException if connection to server went wrong
     */
    MessageDto cancelReservationPosition(CancelTicketDto cancelTicketDto) throws ServiceException;

    /**
     * cancels (completely deletes) a reservation by reservationnumber
     *
     * @param reservationnumber cancel reservation with this number
     * @return message with indicates ic cancellation was successful
     * @throws ServiceException if connection to server went wrong
     */
    MessageDto cancelReservationByNumber(MessageDto reservationnumber) throws ServiceException;


    List<ReservationDto> findReservationByPerformanceID(Integer id) throws ServiceException;

    List<ReservationDto> findReservationByPerformanceName(String name) throws ServiceException;
}



