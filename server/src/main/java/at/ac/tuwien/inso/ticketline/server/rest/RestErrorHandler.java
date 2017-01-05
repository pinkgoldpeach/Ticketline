package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.MessageDto;
import at.ac.tuwien.inso.ticketline.dto.MessageType;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

/**
 * Handler class for exceptions of the REST services
 */
@ControllerAdvice
public class RestErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestErrorHandler.class);

    private MessageSource messageSource;

    /**
     * Instantiates a new rest error handler.
     *
     * @param messageSource the message source
     */
    @Autowired
    public RestErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Processes the service exception and creates a message DTO for it
     *
     * @param se the service exception
     * @return the message DTO
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public
    @ResponseBody
    MessageDto processServiceError(ServiceException se) {
        LOGGER.error(se.getMessage(), se);
        MessageDto msg = new MessageDto();
        msg.setType(MessageType.ERROR);
        msg.setText(se.getMessage());
        return msg;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public
    @ResponseBody
    MessageDto processInputError(ValidationException ve){
        LOGGER.error(ve.getMessage(), ve);
        MessageDto msg = new MessageDto();
        msg.setType(MessageType.ERROR);
        msg.setText(ve.getMessage());
        return msg;
    }

    /**
     * Processes the validation error and creates a message DTO for it
     *
     * @param ex the validation exception
     * @return the message DTO
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public
    @ResponseBody
    MessageDto processValidationError(MethodArgumentNotValidException ex) {
        LOGGER.error("processValidationError: " + ex.getMessage());
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        LOGGER.error("processValidationError - field errors: " + fieldErrors);
        return processFieldErrors(fieldErrors);
    }

    /**
     * Processes field errors and creates a message DTO for it
     *
     * @param fieldErrors the field errors
     * @return the message DTO
     */
    private MessageDto processFieldErrors(List<FieldError> fieldErrors) {
        MessageDto msg = new MessageDto();
        msg.setType(MessageType.ERROR);
        for (FieldError fieldError : fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            msg.addFieldError(fieldError.getField(), localizedErrorMessage);
        }
        return msg;
    }

    /**
     * Resolve localized error message.
     *
     * @param fieldError the field error
     * @return the localized error message
     */
    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);
        //If the message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }
        return localizedErrorMessage;
    }

}
