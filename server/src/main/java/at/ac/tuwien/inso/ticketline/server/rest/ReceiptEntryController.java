package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.dto.ReceiptEntryDto;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.ReceiptEntryService;
import at.ac.tuwien.inso.ticketline.server.util.EntityToDto;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carla on 17/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 */
@Api(value = "receiptEntry", description = "ReceiptEntry REST service")
@RestController
@RequestMapping(value = "/receiptEntry")
public class ReceiptEntryController extends AbstractPaginationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptEntryController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    private ReceiptEntryService receiptEntryService;

    @ApiOperation(value = "Gets all receipt entries of a page", response = ReceiptEntryDto.class, responseContainer = "List")
    @RequestMapping(value = "/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ReceiptEntryDto> getReceiptEntriesPage(@ApiParam(name = "pagenumber", value = "Number of receipt entries page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getReceiptEntriesPage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertReceiptEntries(receiptEntryService.getPageOfReceiptEntries(pageNumber, ENTRIES_PER_PAGE), new ArrayList<Class<?>>());
    }

    /**
     * Gets all receipt entry items.
     *
     * @return list of all receipt entry items
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all receipt entries", response = ReceiptEntryDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ReceiptEntryDto> getAll() throws ServiceException {
        LOGGER.info("getAll() called");
        return EntityToDto.convertReceiptEntries(receiptEntryService.getAllReceiptEntries(), new ArrayList<Class<?>>());
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
        LOGGER.info("RecipientEntry :: getPageCount() called");
        return EntityToDto.convertPageCount(receiptEntryService.getPageCount());
    }
}
