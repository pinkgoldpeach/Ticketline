package at.ac.tuwien.inso.ticketline.dao;


import at.ac.tuwien.inso.ticketline.model.ProcessingSeat;

import java.util.List;

/**
 * @author Alexander Poschenreithner (1328924)
 */
public interface ProcessingSeatDaoCustom {

    /**
     * Returns all seats in processing state for a show
     *
     * @param showId of show
     * @return List of seats in processing state for this show
     */
    List<ProcessingSeat> getProcessingSeats(int showId);

    /**
     * Removes records older than given amount of seconds
     *
     * @param seconds to check
     */
    void removeDeprecatedProcessingSeat(int seconds);

    /**
     * Frees a seat in processing state
     *
     * @param processingSeat seat to be freed
     * @param username       of whoms locks should be deleted
     */
    void freeProcessingSeat(ProcessingSeat processingSeat, String username);

    /**
     * Returns a seat in processing state
     *
     * @param processingSeat seat to be looked up
     * @return found seat
     */
    ProcessingSeat getProcessingSeat(ProcessingSeat processingSeat);

    /**
     * Removes all entries for a user
     *
     * @param username of whoms locks should be deleted
     */
    void removeProcessingSeatsForUser(String username);
}
