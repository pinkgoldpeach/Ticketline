package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.EmployeeDto;
import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.MessageType;
import at.ac.tuwien.inso.ticketline.dto.PageCountDto;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import at.ac.tuwien.inso.ticketline.server.service.AdminService;
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
 *
 * @author Alexander Poschenreithner (1328924)
 */
@Api(value = "admin", description = "Admin REST service")
@RestController
@RequestMapping(value = "/admin")
public class AdminController extends AbstractPaginationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    private InputValidation inputValidation = new InputValidation();

    @Autowired
    AdminService adminService;


    /**
     * Gets the all employees.
     *
     * @return list of all employees
     * @throws ServiceException the service exception
     */
    @ApiOperation(value = "Gets all employees", response = EmployeeDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> getAll() throws ServiceException {
        LOGGER.info("getAll() called");
        return EntityToDto.convertEmployees(adminService.getAllEmployees(), new ArrayList<Class<?>>());
    }

    /**
     * Create a new employee.
     *
     * @param employee the employee
     * @return the message dto
     * @throws ServiceException    the service exception
     * @throws ValidationException on validation failure
     */
    @ApiOperation(value = "Create or update an employee", response = EmployeeDto.class)
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto createEmployee(@ApiParam(name = "employee", value = "Create or update employee") @Valid @RequestBody EmployeeDto employee) throws ServiceException, ValidationException {
        LOGGER.info("createEmployee() called");
        inputValidation.checkUsername(employee.getUsername());
        inputValidation.checkLettersNoSpecial(employee.getInsuranceNumber());
        if (employee.getPasswordHash() != null)
            inputValidation.checkLettersWithSpecialsNoUmlauts(employee.getPasswordHash());
        inputValidation.checkPerson(employee);
        inputValidation.checkEmployeedSinceDate(employee.getEmployedSince(), employee.getDateOfBirth());
        Integer id = this.adminService.save(DtoToEntity.convert(employee)).getId();
        MessageDto msg = new MessageDto();
        msg.setType(MessageType.SUCCESS);
        msg.setText(id.toString());
        return msg;
    }

    /**
     * Gets all employees in a certain page.
     *
     * @param pageNumber Page Number
     * @return page of all employees
     * @throws ServiceException    the service exception
     * @throws ValidationException on validation failure
     */
    @ApiOperation(value = "Gets all employees in a page", response = EmployeeDto.class, responseContainer = "List")
    @RequestMapping(value = "/{pagenumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> getEmployeePage(@ApiParam(name = "pagenumber", value = "Number of employee page") @PathVariable("pagenumber") Integer pageNumber) throws ServiceException, ValidationException {
        LOGGER.info("getEmployeePage() called");
        pageNumber--;
        inputValidation.checkPageNumber(pageNumber);
        return EntityToDto.convertEmployees(adminService.getPageOfEmployees(pageNumber, ENTRIES_PER_PAGE), new ArrayList<Class<?>>());
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
        LOGGER.info("Admin :: getPageCount() called");
        return EntityToDto.convertPageCount(adminService.getPageCount());
    }

    //--------- FOR TESTING
    public AdminController() {
    }

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

}
