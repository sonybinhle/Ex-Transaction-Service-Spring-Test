package xeasony.configs;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import xeasony.exceptions.DataValidationException;
import xeasony.exceptions.ErrorResource;
import xeasony.exceptions.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobarExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResource handleValidationException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        DataValidationException dve = new DataValidationException(result);

        return new ErrorResource(req.getRequestURL().toString(), dve.getMessage(), dve.getFieldErrorResources());
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorResource handleNotFound(HttpServletRequest req, Exception e) {
        return new ErrorResource(req.getRequestURL().toString(), e.getMessage(), null);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResource handleBadRequest(HttpServletRequest req, Exception e) {
        return new ErrorResource(req.getRequestURL().toString(), e.getMessage(), null);
    }
}
