package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.model.Receipt;
import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.AdminService;
import at.ac.tuwien.inso.ticketline.server.service.ReceiptService;
import at.ac.tuwien.inso.ticketline.server.service.TicketService;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by Carla on 17/05/2016.
 *
 * @author Alexander Poschenreithner (1328924)
 * @author Patrick Weiszkirchner
 */
@Api(value = "receipt", description = "Receipt REST service")
@RestController
@RequestMapping(value = "/receipt")
public class ReceiptController extends AbstractPaginationController {


    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private TicketService ticketService;

    @ApiOperation(value = "Gets all receipts of a page", response = ReceiptDto.class, responseContainer = "List")
    @RequestMapping(value = "/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ReceiptDto> getReceiptsPage(@ApiParam(name = "pagenumber", value = "Number of receipt page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getReceiptsPage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertReceipts(receiptService.getPageOfReceipts(pageNumber, ENTRIES_PER_PAGE), new ArrayList<Class<?>>());
    }

    /**
     * Gets all receipt items.
     *
     * @return list of all receipt items
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all receipts", response = ReceiptDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ReceiptDto> getAll() throws ServiceException {
        LOGGER.info("getAll() called");
        return EntityToDto.convertReceipts(receiptService.getAllReceipts(), new ArrayList<Class<?>>());
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
        LOGGER.info("Recipient :: getPageCount() called");
        return EntityToDto.convertPageCount(receiptService.getPageCount());
    }


    @ApiOperation(value = "Save an invoice", response = ReceiptDto.class)
    @RequestMapping(value = "/saveReceipt", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto saveReceipt(@ApiParam(name = "receiptDto", value = "receipt to be saved") @Valid @RequestBody ReceiptDto receiptDto) throws ServiceException, ValidationException {
        LOGGER.info("saveReceipt() called");

        HashMap<Integer, Ticket> tickets = new HashMap<>();
        for(ReceiptEntryDto receiptEntryDto : receiptDto.getReceiptEntryDtos()){
            tickets.put(receiptEntryDto.getTicketId(), ticketService.getTicketByID(receiptEntryDto.getTicketId()));
        }
        Receipt receipt = DtoToEntity.convert(receiptDto, tickets);
        receipt.setEmployee(adminService.getCurrentlyLoggedInEmployee());
        receiptService.save(receipt);

        MessageDto msg = new MessageDto();
        msg.setType(MessageType.SUCCESS);
        return msg;
    }

    /**
     * Find all receipts with a specific customer lastname
     *
     * @param lastname of the customer
     * @return list of receipts
     * @throws ServiceException on error
     */
    @ApiOperation(value = "Find receips by a certain customer lastname", response = ReceiptDto.class, responseContainer = "List")
    @RequestMapping(value = "/findReceiptsByLastname/{lastname}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ReceiptDto> findReceiptsByLastname(@ApiParam(name = "lastname", value = "Lastname of customer to look up") @PathVariable("lastname") String lastname) throws ServiceException{
        LOGGER.info("findReservationByLastname() called");

        List<Receipt> list = receiptService.findReceiptByLastName(lastname);

        return EntityToDto.convertReceipts(list, new ArrayList<Class<?>>());
    }

}
