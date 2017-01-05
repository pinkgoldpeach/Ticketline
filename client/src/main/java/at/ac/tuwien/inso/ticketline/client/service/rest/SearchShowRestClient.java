package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.client.gui.controller.SearchEventsOverviewController;
import at.ac.tuwien.inso.ticketline.client.service.PerformanceService;
import at.ac.tuwien.inso.ticketline.client.service.RoomService;
import at.ac.tuwien.inso.ticketline.client.service.ShowService;
import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.client.util.UIShow;
import at.ac.tuwien.inso.ticketline.client.util.UIShowWithPrice;
import at.ac.tuwien.inso.ticketline.dto.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *  Implementation of {@link at.ac.tuwien.inso.ticketline.client.service.ShowService}
 */
@Component
public class SearchShowRestClient implements ShowService {


    private static final Logger LOGGER = LoggerFactory.getLogger(SearchShowRestClient.class);

    private static final String GET_ALL_SHOWS_URL = "/service/show/";
    private static final String GET_ALL_SHOWS_FROM_TO_DATE_URL = "/service/show/getByDateRange";
    private static final String GET_ALL_SHOWS_FROM_DATE_URL = "/service/show/getByDateFrom";
    private static final String GET_ALL_SHOWS_BY_LOCATION_URL = "/service/show/getByLocation";
    private static final String GET_ALL_SHOWS_BY_PERFORMANCE_URL = "/service/show/getByPerformance";
    private static final String GET_ALL_SHOWS_BY_ROOM_URL = "/service/show/getByRoom";
    private static final String GET_ALL_SHOWS_BY_DATE_URL = "/service/show/getBySpecificDate";
    private static final String GET_ALL_SHOWS_BY_PRICE_URL = "/service/show/getByPrice";
    private static final String GET_ALL_TOP_TEN = "/service/show/getTopTen";
    private static final String GET_ALL_TOP_TEN_CATEGORIES = "/service/show/getTopTenByPerformanceType?performanceType=";
    private static final String GET_PAGE_COUNT = "/service/show/pagecount";


    @Autowired
    private RestClient restClient;

    @Autowired
    PerformanceService performanceService;

    @Autowired
    RoomService roomService;

    private  HashMap<Integer, ObservableList<UIShowWithPrice>> cashedShowMap;
    private  List<UIShowWithPrice> cashedAllShows;

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData() throws ServiceException {
        LOGGER.info("Cache Data in service layer");
        cashedShowMap = new HashMap<>();
        cashedAllShows = new ArrayList<>();
        int pages = getPageCount();
        if (pages>0)
        {
            for (int i =1;i<=pages;i++){
                    List<ShowDto> tempList = getSpecificPage(i);
                    ObservableList<UIShowWithPrice> tmpShowList = FXCollections.observableArrayList();
                    for (ShowDto show: tempList) {
                        if (show.getDateOfPerformance().after(new Date())) {
                            UIShowWithPrice uiShow = new UIShowWithPrice(show, roomService);
                            tmpShowList.add(uiShow);
                            cashedAllShows.add(uiShow);
                        }
                    }
                    cashedShowMap.put(i,tmpShowList);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HashMap<Integer, ObservableList<UIShowWithPrice>> getCachedMap() {
        return cashedShowMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UIShowWithPrice> cachedServiceGetShowByPrice(double price) {
        List<UIShowWithPrice> tmp = new ArrayList<>();
        Double bottom = price-20;
        Double top = price +20;

        if (bottom >0) {
            for (UIShowWithPrice show : cashedAllShows) {
                if (bottom <= show.getMinPrice()&& top >=show.getMaxPrice()) {
                    tmp.add(show);
                } else if (bottom <= show.getMinPrice()&& top >=show.getMinPrice()) {
                    tmp.add(show);
                } else if (bottom >= show.getMinPrice()&& top <=show.getMaxPrice()) {
                    tmp.add(show);
                }
            }
        } else {
            LOGGER.info("all shows between 0 and 20");
            for (UIShowWithPrice show : cashedAllShows) {
                if (show.getMinPrice()>=1 && show.getMaxPrice()<=40) {
                    tmp.add(show);
                }
            }
        }

        return tmp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UIShowWithPrice> cachedServiceGetShowFromDate(LocalDate fromDate) {
        List<UIShowWithPrice> tmp = new ArrayList<>();

        for (UIShowWithPrice show: cashedAllShows ){
            if (show.getDateOrigin().isAfter(fromDate)) {
                tmp.add(show);
            }
        }

        return tmp;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<UIShowWithPrice> cachedServiceGetShowFromTo(LocalDate fromDate,LocalDate toDate) {
        List<UIShowWithPrice> tmp = new ArrayList<>();

        for (UIShowWithPrice show: cashedAllShows ){
            if (show.getDateOrigin().isAfter(fromDate) && show.getDateOrigin().isBefore(toDate.plusDays(1))) {
                tmp.add(show);
            }
        }

        return tmp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowDto> getShows() throws ServiceException {
        return doRequest(GET_ALL_SHOWS_URL, "shows");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowDto> getSpecificPage(int page) throws ServiceException {
        return doRequest(GET_ALL_SHOWS_URL + page, "shows by page: " + page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowDto> getShowByPerformance(Integer performanceID) throws ServiceException {
        return doRequest(GET_ALL_SHOWS_BY_PERFORMANCE_URL+"?performanceID="+performanceID, "shows by performanceID "+performanceID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowDto> getShowFromToDate(String fromDate, String toDate) throws ServiceException {
        return doRequest(GET_ALL_SHOWS_FROM_TO_DATE_URL + "?startingDate=" + fromDate + "&endingDate=" + toDate, "shows by from date:" + fromDate + " to date: " + toDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowDto> getShowByLocation(Integer locationID) throws ServiceException {
        return doRequest(GET_ALL_SHOWS_BY_LOCATION_URL+"?locationId="+locationID, "shows by location ID "+locationID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowDto> getShowByArtist(Integer artistID) throws ServiceException {
        List<PerformanceDto> list = performanceService.getPerformancesByArtistID(artistID);
        List<ShowDto> showList = new ArrayList<>();
        List<ShowDto> tmpList;

        for (PerformanceDto p : list) {
            tmpList = getShowByPerformance(p.getId());
            for (ShowDto s : tmpList) {
                showList.add(s);
            }
            tmpList.clear();
        }

        return showList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowDto> getShowByDateAndTime(String date, String time) throws ServiceException {
        return doRequest(GET_ALL_SHOWS_BY_DATE_URL+"?dateOfShow="+date+"&timeOfShow="+time, "shows by date and time: "+date+" "+ time);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowDto> getShowByDate(String date) throws ServiceException {
        return doRequest(GET_ALL_SHOWS_BY_DATE_URL+"?dateOfShow="+date+"&timeOfShow=00:00:00", "shows by date: "+date);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowDto> getShowFromDate(String date) throws ServiceException {
        return doRequest(GET_ALL_SHOWS_FROM_DATE_URL+"?dateOfShow="+date+"&timeOfShow=00:00:00", "shows from date: "+date);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowDto> getShowByRoom(Integer roomID) throws ServiceException {
        return doRequest(GET_ALL_SHOWS_BY_ROOM_URL+"?roomID="+roomID, "shows by room ID "+roomID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowDto> getShowByPrice(Double price) throws ServiceException {
        return doRequest(GET_ALL_SHOWS_BY_PRICE_URL + "?price=" + price, "shows by room ID " + price);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShowTopTenDto> getTop10SoldShows() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_TOP_TEN);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving topten from {}", url);
        List<ShowTopTenDto> showsTopTen;
        try {
            ParameterizedTypeReference<ArrayList<ShowTopTenDto>> ref = new ParameterizedTypeReference<ArrayList<ShowTopTenDto>>() {};
            ResponseEntity<ArrayList<ShowTopTenDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            showsTopTen = response.getBody();

        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else if (e.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
                throw new ValidationException(BundleManager.getBundle().getString("inputValidation.notValid"));
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            LOGGER.error("RestClientException when retrieving artists: " + e.getMessage());
            throw new ServiceException("Could not retrieve top 10 shows " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(BundleManager.getBundle().getString("inputValidation.notValid"));
        }
        return showsTopTen;
    }

    @Override
    public List<ShowTopTenDto> getTop10SoldShowsByCategory(PerformanceTypeDto type) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_ALL_TOP_TEN_CATEGORIES+type.name());
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving topten from {}", url);
        List<ShowTopTenDto> showsTopTen;
        try {
            ParameterizedTypeReference<ArrayList<ShowTopTenDto>> ref = new ParameterizedTypeReference<ArrayList<ShowTopTenDto>>() {};
            ResponseEntity<ArrayList<ShowTopTenDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            showsTopTen = response.getBody();

        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else if (e.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
                throw new ValidationException(BundleManager.getBundle().getString("inputValidation.notValid"));
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            LOGGER.error("RestClientException when retrieving artists: " + e.getMessage());
            throw new ServiceException("Could not retrieve top 10 shows " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(BundleManager.getBundle().getString("inputValidation.notValid"));
        }
        return showsTopTen;
    }


    private List<ShowDto> doRequest(String urlType, String type) throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(urlType);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving "+type+" from {}", url);
        List<ShowDto> shows;
        try {
            ParameterizedTypeReference<ArrayList<ShowDto>> ref = new ParameterizedTypeReference<ArrayList<ShowDto>>() {};
            ResponseEntity<ArrayList<ShowDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            shows = response.getBody();

        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else if (e.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
                throw new ValidationException(BundleManager.getBundle().getString("inputValidation.notValid"));
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            LOGGER.error("RestClientException when retrieving artists: " + e.getMessage());
            throw new ServiceException("Could not retrieve "+type+ " " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(BundleManager.getBundle().getString("inputValidation.notValid"));
        }
        return shows;
    }

    @Override
    public Integer getPageCount() throws ServiceException {
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_PAGE_COUNT);
        HttpEntity<String> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        LOGGER.info("Retrieving pagecount from {}", url);
        PageCountDto pageCount;
        try {
            ParameterizedTypeReference<PageCountDto> ref = new ParameterizedTypeReference<PageCountDto>() {
            };
            ResponseEntity<PageCountDto> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
            pageCount = response.getBody();
        } catch (HttpStatusCodeException e) {
            LOGGER.error("Got error code " + e.getStatusCode().toString());
            MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
            if (errorMsg.hasFieldErrors()) {
                throw new ValidationException(errorMsg.getFieldErrors());
            } else {
                throw new ServiceException(errorMsg.getText());
            }
        } catch (RestClientException e) {
            LOGGER.error("Got an RestClientException when retrieving pageCount");
            throw new ServiceException(BundleManager.getBundle().getString("exception.admin.getPageCount") + e.getMessage(), e);
        }
        return pageCount.getPageCount();
    }
}
