package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.CustomerDto;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.MessageType;
import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.CustomerService;
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
 * Controller for the customer REST service
 *
 * @author Patrick Weiszkirchner
 * @author Alexander Poschenreithner (1328924)
 */
@Api(value = "customer", description = "Customer REST service")
@RestController
@RequestMapping(value = "/customer")
public class CustomerController extends AbstractPaginationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    CustomerService customerService;


    /**
     * Gets the all customers.
     *
     * @return list of all customers
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all customers", response = CustomerDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<CustomerDto> getAll() throws ServiceException {
        LOGGER.info("getAll() called");
        return EntityToDto.convertCustomers(customerService.getAllCustomers(), new ArrayList<Class<?>>());
    }

    /**
     * Create a new customer.
     *
     * @param customer the customer
     * @return the message dto
     * @throws ServiceException    the service exception
     * @throws ValidationException on validation failure
     */
    @ApiOperation(value = "Create or update a customer", response = CustomerDto.class)
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto createCustomer(@ApiParam(name = "customer", value = "Create or update customer") @Valid @RequestBody CustomerDto customer) throws ServiceException, ValidationException {
        LOGGER.info("createCustomer() called");
        InputValidation inputValidation = new InputValidation();
        inputValidation.checkPerson(customer);
        Integer id = this.customerService.save(DtoToEntity.convert(customer)).getId();
        MessageDto msg = new MessageDto();
        msg.setType(MessageType.SUCCESS);
        msg.setText(id.toString());
        return msg;
    }

    /**
     * Gets all customers in a certain page.
     *
     * @param pageNumber Page Number
     * @return page of all customers
     * @throws ServiceException    the service exception
     * @throws ValidationException on validation failure
     */
    @ApiOperation(value = "Gets all customers in a page", response = CustomerDto.class, responseContainer = "List")
    @RequestMapping(value = "/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<CustomerDto> getCustomerPage(@ApiParam(name = "pagenumber", value = "Number of customer page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getCustomerPage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertCustomers(customerService.getPageOfCustomers(pageNumber, ENTRIES_PER_PAGE), new ArrayList<Class<?>>());
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
        LOGGER.info("Customer :: getPageCount() called");
        return EntityToDto.convertPageCount(customerService.getPageCount());
    }

    //--------- FOR TESTING
    public CustomerController() {
    }

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
}