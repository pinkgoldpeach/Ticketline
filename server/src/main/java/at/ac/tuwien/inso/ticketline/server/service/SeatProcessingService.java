package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.dto.UsedSeatsDto;
import at.ac.tuwien.inso.ticketline.model.ProcessingSeat;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Created by Patrick on 20/05/16.
 *
 * @author Patrick Weiszkirchner
 * @author Alexander Poschenreithner
 */
public interface SeatProcessingService {

    /**
     * add a List of seats of a certain show which are currently used by the client
     *
     * @param showId   show
     * @param seats    list of Seats
     * @param username Name of logged in user
     * @return true if all could be set to processing state
     * @throws ServiceException on error
     */
    boolean addToProcessingSeats(int showId, List<Integer> seats, String username) throws ServiceException;

    /**
     * Wrapper method for addToProcessingSeats(int showId, List Integer seats)
     *
     * @param usedSeatsDto show
     * @param username     Name of logged in user
     * @return true if all could be set to processing state
     * @throws ServiceException on error
     */
    boolean addToProcessingSeats(UsedSeatsDto usedSeatsDto, String username) throws ServiceException;


    /**
     * Checks if all given seats for a show are free
     *
     * @param showId show
     * @param seats  List of seats
     * @return true if all seats are free
     * @throws ServiceException on error
     */
    boolean checkProcessingSeats(int showId, List<Integer> seats) throws ServiceException;

    /**
     * gets an Dto containing a list of seat IDs of a certain show which are currently used by clients
     *
     * @param showId show
     * @return Dto of reserved seats for a show
     * @throws ServiceException on error
     */
    UsedSeatsDto getProcessingSeats(int showId) throws ServiceException;

    /**
     * Frees a single seat
     *
     * @param processingSeat seat to be freed
     * @param username       Name of logged in user
     * @throws ServiceException on error
     */
    void freeProcessingSeat(ProcessingSeat processingSeat, String username) throws ServiceException;

    /**
     * Removes all seats with processing state longer than a amount of seconds
     *
     * @param seconds of tolerance
     * @throws ServiceException on error
     */
    void removeDeprecatedProcessingSeat(int seconds) throws ServiceException;

    /**
     * Removes a all reservations for a show of a logged in user
     * Username will be loaded over context / authentification
     *
     * @param username Name of logged in user
     * @throws ServiceException on error
     */
    void removeProcessingSeatsForUser(String username) throws ServiceException;
}
