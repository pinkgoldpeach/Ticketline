package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Carla on 15/05/2016.
 */
public interface ShowDaoCustom {

    /**
     * find all shows which have a row with a specific price and are taking place in the future
     * @param row the row with a specific price
     * @return a list of all shows with that price
     */
    List<Show> findByRowPrice(Row row);

    /**
     * find all shows which have an area with a specific price and are taking place in the future
     * @param area an area with a specific price
     * @return a list of all shows with that price
     */
    List<Show> findByAreaPrice(Area area);

    /**
     * find shows by performance that are taking place in the future
     * @param performance the performance you want the shows for
     * @return all shows for that performance
     */
    List<Show> findByPerformance(Performance performance);

    /**
     * find shows that take place in a specific room at some time in the future
     * @param room the room the shows take place in
     * @return a list of all shows that take place in that room
     */
    List<Show> findByRoom(Room room);

    /**
     * find shows that take place in a specific location at some time in the future
     * @param location the location the shows take place in
     * @return a list of all shows that take place in that location
     */
    List<Show> findByLocation(Location location);

    /**
     * returns all shows between a range of dates
     * @param min starting date
     * @param max end date
     * @return list of all found shows
     */
    List<Show> findByDateRange(Date min, Date max);

    /**
     * the overall top ten events of the last month
     * @return a hashmap of the most sold shows
     */
    HashMap<Show, Long> findTopTen();

    /**
     * the top ten events of a specific category of the last month from today till 30 days ago
     * @param performanceType the performancetype you want the top ten events for
     * @return a hashmap of the most sold shows that belong to that performancetype
     */
    HashMap<Show, Long> findTopTenByPerformanceType(PerformanceType performanceType);


    }
