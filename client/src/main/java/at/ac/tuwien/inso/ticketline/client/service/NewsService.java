package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.NewsDto;

import java.util.ArrayList;


/**
 * The Interface NewsService.
 */
public interface NewsService {

    /**
     * Returns a list of all news ever published to the server
     *
     * @return - ArrayList of all NewsDTOs ever created
     * @throws ServiceException - thrown, if something goes wrong when communicating
     */
    ArrayList<NewsDto> getAllNews() throws ServiceException;

    /**
     * Creates a new news on the server.
     *
     * @param news - a filled NewsDTO with necessary data for the server
     * @return - MessageDto with the status of the creatinon of the news
     * @throws ServiceException    - will be thrown, if something goes wrong with the communication
     * @throws ValidationException - thrown, if a input does not
     *                             match the standards e.g. wrong characters or to long values
     */
    MessageDto publishNews(NewsDto news) throws ServiceException;

    /**
     * Returns a list of all news since the last request of them.
     *
     * @return - Array list of new news, if the arrayList size is null, no new news where published
     * @throws ServiceException - in case something goes wrong with communication
     */
    ArrayList<NewsDto> getSpecificNews() throws ServiceException;

}
