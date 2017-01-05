package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Area;
import at.ac.tuwien.inso.ticketline.model.Show;

import java.util.List;

/**
 * @author Alexander Poschenreithner (1328924)
 * @author Patrick Weiszkirchner
 */
public interface TicketDaoCustom {

    /**
     * Returns all sold seats
     * @param showId to get sold seats for
     * @return List of sold seats
     */
    List<Integer> getSoldSeats(Integer showId);

    /**
     * Returns all reserved seats
     * @param showId to get reserved seats for
     * @return List of reserved seats
     */
    List<Integer> getReservedSeats(Integer showId);


}
