package com.example.Excercise1.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
// import static org.springframework.http.HttpStatus.BAD_REQUEST;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.Excercise1.models.ErrorDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ResponseEntity object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        
        ErrorDetails errorsDetails = new ErrorDetails();
        errorsDetails.setTimestamp(new Date());
        errorsDetails.setMessage("Data is not valid, please try again.");
        errorsDetails.setDetails(request.getDescription(false));
        List<String> error1 = ex.getBindingResult()
                                .getAllErrors()
                                .stream()
                                .map(x -> x.getDefaultMessage())
                                .collect(Collectors.toList());
        errorsDetails.setErrors(error1);
        return new ResponseEntity<>(errorsDetails, status);
    }

    /**
     * Handle ResourceNotFoundException. Triggered when an object is not found.
     *
     * @param ex      the ResourceNotFoundException that is thrown when Resource is not found
     * @param headers HttpHeaders
     * @return the ResponseEntity object
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false), null);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}
