package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dao.ShowDao;
import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.dto.PerformanceTypeDto;
import at.ac.tuwien.inso.ticketline.dto.ShowDto;
import at.ac.tuwien.inso.ticketline.dto.ShowTopTenDto;
import at.ac.tuwien.inso.ticketline.model.*;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.ShowService;
import at.ac.tuwien.inso.ticketline.server.util.DtoToEntity;
import at.ac.tuwien.inso.ticketline.server.util.EntityToDto;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Carla on 14/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
@Api(value = "show", description = "Show REST service")
@RestController
@RequestMapping(value = "/show")

public class ShowController extends AbstractPaginationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    private ShowService showService;

    /**
     * Gets all show items.
     *
     * @return list of all show items
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all shows", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getAll() throws ServiceException {
        LOGGER.info("getAll() called");
        return EntityToDto.convertShows(showService.getAllShows(), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all shows of a page", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getShowsPage(@ApiParam(name = "pagenumber", value = "Number of show page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getShowsPage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertShows(showService.getPageOfShows(pageNumber, ENTRIES_PER_PAGE), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all not canceled shows of a page", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getNotCanceledShowsPage/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getNotCanceledShowsPage(@ApiParam(name = "pagenumber", value = "Number of show page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getNotCanceledShowsPage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertShows(showService.getPageOfNotCanceledShows(pageNumber), new ArrayList<Class<?>>());
    }

    /**
     * finds all shows by the given date
     *
     * @param dateOfShow the day of the show
     * @param timeOfShow the time of the show
     * @return a list of all found shows
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown if the date or time is in an invalid format
     */
    @ApiOperation(value = "Gets all shows of that date", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getBySpecificDate", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getByDate(@RequestParam("dateOfShow") String dateOfShow, @RequestParam("timeOfShow") String timeOfShow) throws ServiceException, ValidationException {
        LOGGER.info("getByDate() called");
        dateOfShow = dateOfShow.trim();
        timeOfShow = timeOfShow.trim();
        if (dateOfShow == null || timeOfShow == null || dateOfShow.isEmpty() || timeOfShow.isEmpty()) {
            throw new ServiceException("Date of show is null");
        }
        dateOfShow += " " + timeOfShow;
        inputValidation.checkDateTime(dateOfShow);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date dateUtil = null;
        try {
            dateUtil = formatter.parse(dateOfShow);
        } catch (ParseException e) {
            throw new ValidationException("Date cannot be parsed");
        }
        Show show = new Show();
        show.setDateOfPerformance(dateUtil);
        return EntityToDto.convertShows(showService.getShowsByDateAndTime(show), new ArrayList<Class<?>>());
    }

    /**
     * finds all shows after a given date
     * if only dateOfShow is set, the time will be set to midnight of that day 00:00:00
     * if only the time is set, the date will be set to the current date
     *
     * @param dateOfShow the day from which the search starts
     * @param timeOfShow the time from which the search starts
     * @return a list of all found shows
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown if the date or time is in an invalid format
     */
    @ApiOperation(value = "Gets all shows after and at that date", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByDateFrom", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getByDateFrom(@RequestParam("dateOfShow") String dateOfShow, @RequestParam("timeOfShow") String timeOfShow) throws ServiceException, ValidationException {
        LOGGER.info("getByDate() called");
        dateOfShow = dateOfShow.trim();
        timeOfShow = timeOfShow.trim();
        if (dateOfShow == null || timeOfShow == null || (dateOfShow.isEmpty() && timeOfShow.isEmpty())) {
            throw new ValidationException("Date of show is null");
        }

        if (dateOfShow.isEmpty()) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //get current date time with Date()
            Date date = new Date();
            //get current date time with Calendar()
            Calendar cal = Calendar.getInstance();
            dateOfShow = dateFormat.format(cal.getTime());

        }

        if (timeOfShow.isEmpty()) {
            timeOfShow = "00:00:00";
        }
        dateOfShow += " " + timeOfShow;

        inputValidation.checkDateTime(dateOfShow);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date dateUtil = null;
        try {
            dateUtil = formatter.parse(dateOfShow);
        } catch (ParseException e) {
            throw new ServiceException("Date cannot be parsed");
        }
        Show show = new Show();
        show.setDateOfPerformance(dateUtil);
        return EntityToDto.convertShows(showService.getShowsByDateFrom(show), new ArrayList<Class<?>>());
    }

    /**
     * finds all shows in a given time period
     *
     * @param startingDate the start date of the time period
     * @param startingTime the starting time for the search
     * @param endingDate   the end date of the time period
     * @param endingTime   the ending time for the search
     * @return a list of all found shows during that time
     * @throws ServiceException    the service exception
     * @throws ValidationException the validation exception is thrown if the dates or time are in an invalid format
     */
    @ApiOperation(value = "Gets all shows between two dates with a given time", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByDateAndTimeRange", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getByDateAndTimeRange(@RequestParam("startingDate") String startingDate, @RequestParam("startingTime") String startingTime, @RequestParam("endingDate") String endingDate, @RequestParam("endingTime") String endingTime) throws ServiceException, ValidationException {
        LOGGER.info("getByDateAndTimeRange() called");

        if (startingDate == null || startingDate.trim().isEmpty() || endingDate == null || endingDate.trim().isEmpty()) {
            throw new ValidationException("Date range for shows is null");
        }
        if (startingTime == null || startingTime.trim().isEmpty()) {
            throw new ValidationException("Starting time for shows is null");
        }
        if (endingTime == null || endingTime.trim().isEmpty()) {
            throw new ValidationException("Ending time for shows is null");

        }
        startingDate = startingDate.trim() + " " + startingTime.trim();
        endingDate = endingDate.trim() + " " + endingTime.trim();
        inputValidation.checkDateTime(startingDate);
        inputValidation.checkDateTime(endingDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date startDateUtil = null;
        java.util.Date endDateUtil = null;
        try {
            startDateUtil = formatter.parse(startingDate);
            endDateUtil = formatter.parse(endingDate);
        } catch (ParseException e) {
            throw new ValidationException("Date cannot be parsed");
        }

        return EntityToDto.convertShows(showService.getShowsByDateRange(startDateUtil, endDateUtil), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all shows between two dates", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByDateRange", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getByDateRange(@RequestParam("startingDate") String startingDate, @RequestParam("endingDate") String endingDate) throws ServiceException, ValidationException {
        LOGGER.info("getByDateRange() called");

        if (startingDate == null || startingDate.trim().isEmpty() || endingDate == null || endingDate.trim().isEmpty()) {
            throw new ValidationException("Date range for shows is null");
        }
        String startingTime = "00:00:00";
        String endingTime = "23:59:59";

        startingDate = startingDate.trim() + " " + startingTime.trim();
        endingDate = endingDate.trim() + " " + endingTime.trim();
        inputValidation.checkDateTime(startingDate);
        inputValidation.checkDateTime(endingDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date startDateUtil = null;
        java.util.Date endDateUtil = null;
        try {
            startDateUtil = formatter.parse(startingDate);
            endDateUtil = formatter.parse(endingDate);
        } catch (ParseException e) {
            throw new ValidationException("Date cannot be parsed");
        }

        return EntityToDto.convertShows(showService.getShowsByDateRange(startDateUtil, endDateUtil), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all shows by row price", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByRowPrice", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getByRowPrice(@RequestParam("price") Double price) throws ServiceException, ValidationException {
        LOGGER.info("getByRowPrice() called");

        inputValidation.checkPrice(price);
        Row row = new Row();
        row.setPrice(price);
        return EntityToDto.convertShows(showService.getShowsByRowPrice(row), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all shows by area price", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByAreaPrice", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getByAreaPrice(@RequestParam("price") Double price) throws ServiceException, ValidationException {
        LOGGER.info("getByAreaPrice() called");

        inputValidation.checkPrice(price);

        Area area = new Area();
        area.setPrice(price);
        return EntityToDto.convertShows(showService.getShowsByAreaPrice(area), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all shows by price", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByPrice", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getByPrice(@RequestParam("price") Double price) throws ServiceException, ValidationException {
        LOGGER.info("getByPrice() called");
        inputValidation.checkPrice(price);
        Row row = new Row();
        row.setPrice(price);
        Area area = new Area();
        area.setPrice(price);
        List<ShowDto> foundByRowPrice = EntityToDto.convertShows(showService.getShowsByRowPrice(row), new ArrayList<Class<?>>());
        List<ShowDto> foundByAreaPrice = EntityToDto.convertShows(showService.getShowsByAreaPrice(area), new ArrayList<Class<?>>());
        foundByRowPrice.addAll(foundByAreaPrice);
        return foundByRowPrice;
    }


    @ApiOperation(value = "Gets all shows by performance", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByPerformance", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getByPerformance(@RequestParam("performanceID") Integer performanceID) throws ServiceException, ValidationException {
        LOGGER.info("getByPerformance() called");

        inputValidation.checkId(performanceID);
        Performance performance = new Performance();
        performance.setId(performanceID);

        return EntityToDto.convertShows(showService.getShowsByPerformance(performance), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all shows by room", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByRoom", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getByRoom(@RequestParam("roomID") Integer roomID) throws ServiceException, ValidationException {
        LOGGER.info("getByRoom() called");

        inputValidation.checkId(roomID);
        Room room = new Room();
        room.setId(roomID);

        return EntityToDto.convertShows(showService.getShowsByRoom(room), new ArrayList<Class<?>>());
    }

    @ApiOperation(value = "Gets all shows by location", response = ShowDto.class, responseContainer = "List")
    @RequestMapping(value = "/getByLocation", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowDto> getByLocation(@RequestParam("locationId") Integer locationId) throws ServiceException, ValidationException {
        LOGGER.info("getByLocation() called");

        inputValidation.checkId(locationId);
        Location location = new Location();
        location.setId(locationId);

        return EntityToDto.convertShows(showService.getShowsByLocation(location), new ArrayList<Class<?>>());
    }

    /**
     * Returns the count of available pages
     *
     * @return number of pages
     * @throws ServiceException the service exception
     */
    @Override
    @ApiOperation(value = "Returns the count of available pages", response = PageCountDto.class)
    @RequestMapping(value = "/pagecount", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public PageCountDto getPageCount() throws ServiceException {
        LOGGER.info("Show :: getPageCount() called");
        return EntityToDto.convertPageCount(showService.getPageCount());
    }

    @ApiOperation(value = "Gets top 10 shows", response = ShowTopTenDto.class, responseContainer = "List")
    @RequestMapping(value = "/getTopTen", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowTopTenDto> getTopTen() throws ServiceException {
        LOGGER.info("getTopTen() called");
        HashMap<Show, Long> topTen = showService.getTopTenShows();

        Set<Show> keySet = topTen.keySet();
        List<ShowTopTenDto> showTopTenDtos = new ArrayList<>();
        for(Show s : keySet){
            ShowTopTenDto showTopTenDto = EntityToDto.convert(s, topTen.get(s), new ArrayList<Class<?>>());
            showTopTenDtos.add(showTopTenDto);
        }
        return showTopTenDtos;
    }

    @ApiOperation(value = "Gets top 10 shows by performance type", response = ShowTopTenDto.class, responseContainer = "List")
    @RequestMapping(value = "/getTopTenByPerformanceType", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ShowTopTenDto> getTopTenByPerformanceType(@RequestParam("performanceType") PerformanceTypeDto performanceTypeDto) throws ServiceException {
        LOGGER.info("getTopTenByPerformanceType() called");

        PerformanceType performanceType = DtoToEntity.convert(performanceTypeDto);
        HashMap<Show, Long> topTen = showService.getTopTenShowsByPerformanceType(performanceType);

        Set<Show> keySet = topTen.keySet();
        List<ShowTopTenDto> showTopTenDtos = new ArrayList<>();
        for(Show s : keySet){
            ShowTopTenDto showTopTenDto = EntityToDto.convert(s, topTen.get(s), new ArrayList<Class<?>>());
            showTopTenDtos.add(showTopTenDto);
        }
        return showTopTenDtos;
    }



}
