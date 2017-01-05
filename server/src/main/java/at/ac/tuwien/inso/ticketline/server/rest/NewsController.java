package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.MessageType;
import at.ac.tuwien.inso.ticketline.dto.NewsDto;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.NewsService;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the news REST service
 */
@Api(value = "news", description = "News REST service")
@RestController
@RequestMapping(value = "/news")
public class NewsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    private NewsService newsService;

    /**
     * Gets the news by id.
     *
     * @param id the id
     * @return the news by id
     * @throws ServiceException the service exception
     * @throws ValidationException on validation failure
     */
    @ApiOperation(value = "Gets the news by id", response = NewsDto.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public NewsDto getNewsById(@ApiParam(name = "id", value = "ID of the news") @PathVariable("id") Integer id) throws ServiceException, ValidationException {
        LOGGER.info("getNewsById() called");
        inputValidation.checkId(id);
        return EntityToDto.convert(newsService.getNews(id), new ArrayList<Class<?>>());
    }

    /**
     * Gets the all news items.
     *
     * @return list of all news items
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all news", response = NewsDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<NewsDto> getAll() throws ServiceException {
        LOGGER.info("getAll() called");
        return EntityToDto.convertNews(newsService.getAllNews(), new ArrayList<Class<?>>());
    }

    /**
     * Gets the specific news items.
     *
     * @return list of specific news items
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets specific news", response = NewsDto.class, responseContainer = "List")
    @RequestMapping(value = "/specific", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<NewsDto> getSpecificNews() throws ServiceException {
        LOGGER.info("getSpecificNews() called");
        return EntityToDto.convertNews(newsService.getSpecificNews(), new ArrayList<Class<?>>());
    }

    /**
     * Publishes a new news.
     *
     * @param news the news
     * @return the message dto
     * @throws ServiceException the service exception
     * @throws ValidationException on validation failure
     */
    @ApiOperation(value = "Publishes a new news", response = NewsDto.class)
    @RequestMapping(value = "/publish", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto publishNews(@ApiParam(name = "news", value = "News to publish") @Valid @RequestBody NewsDto news) throws ServiceException, ValidationException {
        LOGGER.info("publishNews() called");
        inputValidation.checkLettersWithSpecials(news.getTitle());
        inputValidation.checkLettersWithSpecials(news.getNewsText());
        Integer id = this.newsService.save(DtoToEntity.convert(news)).getId();
        MessageDto msg = new MessageDto();
        msg.setType(MessageType.SUCCESS);
        msg.setText(id.toString());
        return msg;
    }

}
