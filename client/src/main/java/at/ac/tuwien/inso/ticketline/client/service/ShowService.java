package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.gui.controller.SearchEventsOverviewController;
import at.ac.tuwien.inso.ticketline.client.util.UIShow;
import at.ac.tuwien.inso.ticketline.client.util.UIShowWithPrice;
import at.ac.tuwien.inso.ticketline.dto.PerformanceTypeDto;
import at.ac.tuwien.inso.ticketline.dto.ShowDto;
import at.ac.tuwien.inso.ticketline.dto.ShowTopTenDto;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * Methods for ShowDto
 */
public interface ShowService {

    /**
     * Gets all shows
     *
     * @return all shows as a list
     * @throws ServiceException    if connection to server went wrong
     * @throws ValidationException if validation fails
     */
    List<ShowDto> getShows() throws ServiceException;

    /**
     * Gets all shows
     *
     * @param page Page Number
     * @return all shows as a list
     * @throws ServiceException if connection to server went wrong
     */
    List<ShowDto> getSpecificPage(int page) throws ServiceException;

    /**
     * Gets all shows by given performanceID
     *
     * @param performanceID should be an unique, positive ID
     * @return all shows with given performanceID as list
     * @throws ServiceException if connection to server went wrong
     */
    List<ShowDto> getShowByPerformance(Integer performanceID) throws ServiceException;

    /**
     * Gets all shows by given locationID
     *
     * @param locationID should be an unique, positive ID
     * @return all shows with given locationID as list
     * @throws ServiceException if connection to server went wrong
     */
    List<ShowDto> getShowByLocation(Integer locationID) throws ServiceException;

    /**
     * Gets all shows by given artistID
     *
     * @param artistID should be an unique, positive ID
     * @return all shows with given artistID as list
     * @throws ServiceException if connection to server went wrong
     */
    List<ShowDto> getShowByArtist(Integer artistID) throws ServiceException;

    /**
     * Gets all shows
     *
     * @param date should be a non-null valid date in format YYYY-MM-DD
     * @param time should be a non-null valid date in format HH:MM:SS
     * @return all shows with given date and time as list
     * @throws ServiceException if connection to server went wrong
     */
    List<ShowDto> getShowByDateAndTime(String date, String time) throws ServiceException;

    /**
     * Gets all shows
     *
     * @param date should be a non-null valid date in format YYYY-MM-DD
     * @return all shows with given date as list
     * @throws ServiceException if connection to server went wrong
     */
    List<ShowDto> getShowByDate(String date) throws ServiceException;

    /**
     * Gets all shows from given date
     *
     * @param date should be a non-null valid date in format YYYY-MM-DD
     * @return all showsfrom given date as list
     * @throws ServiceException if connection to server went wrong
     */
    List<ShowDto> getShowFromDate(String date) throws ServiceException;

    /**
     * Gets all shows in a given date range
     *
     * @param fromDate should be a non-null valid date in format YYYY-MM-DD and smaller toDate
     * @param toDate   should be a non-null valid date in format YYYY-MM-DD and bigger than fromDate
     * @return all showsfrom within give dates as list
     * @throws ServiceException if connection to server went wrong
     */
    List<ShowDto> getShowFromToDate(String fromDate, String toDate) throws ServiceException;

    /**
     * Gets all shows
     *
     * @param roomID should be an unique, positive ID
     * @return all shows with given roomID as list
     * @throws ServiceException if connection to server went wrong
     */
    List<ShowDto> getShowByRoom(Integer roomID) throws ServiceException;

    /**
     * Gets all shows
     *
     * @param price should be a non-null, positive integer
     * @return all shows with given price as list
     * @throws ServiceException if connection to server went wrong
     */
    List<ShowDto> getShowByPrice(Double price) throws ServiceException;

    /**
     * Gets all top10 sold shows
     *
     * @return a list with 10 best sold shows
     * @throws ServiceException if connection to server went wrong
     */
    List<ShowTopTenDto> getTop10SoldShows() throws ServiceException;

    /**
     * Gets all top10 sold shows
     *
     * @param type find top 10 shows by this type
     * @return a list with 10 best sold shows
     * @throws ServiceException if connection to server went wrong
     */
    List<ShowTopTenDto> getTop10SoldShowsByCategory(PerformanceTypeDto type) throws ServiceException;

    /**
     * Returns the number of pages which will be provided by the server
     *
     * @return a number, which represends the number of total pages
     * @throws ServiceException will be thrown if there is an error while communicating with the server
     */
    Integer getPageCount() throws ServiceException;

    /**
     * Cache ShowData in Client
     *
     * @throws ServiceException will be thrown if there is an error while communicating with the server
     */
    void loadData() throws ServiceException;

    /**
     * Service for cached ShowList
     *
     * @param price average price of ticket/row/seat
     * @return list of shows by price
     */
    List<UIShowWithPrice> cachedServiceGetShowByPrice(double price);

    /**
     * Service for cached ShowList
     *
     * @param fromDate starting date
     * @return list of shows from date
     */
    List<UIShowWithPrice> cachedServiceGetShowFromDate(LocalDate fromDate);

    /**
     * Service for cached ShowList
     *
     * @param fromDate starting date
     * @param toDate   to date
     * @return list of shows from to given dates
     */
    List<UIShowWithPrice> cachedServiceGetShowFromTo(LocalDate fromDate, LocalDate toDate);

    /**
     * @return Map with cached shows, where key is pagenumber and value is a lsit with UIShowWithPrice
     */
    HashMap<Integer, ObservableList<UIShowWithPrice>> getCachedMap();
}
