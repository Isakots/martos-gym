package hu.isakots.martosgym;

import hu.isakots.martosgym.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity handleDatabaseException(DatabaseException exception) {
        logException(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handleUserNameNotFoundException(UsernameNotFoundException exception) {
        logException(exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity handleUnsupportedOperationException(UnsupportedOperationException exception) {
        logException(exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException exception) {
        logException(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFoundException(ResourceNotFoundException exception) {
        logException(exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity handleFileUploadException(FileUploadException exception) {
        logException(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity handleInvalidPasswordException(InvalidPasswordException exception) {
        logException(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(ReservationValidationException.class)
    public ResponseEntity handleReservationValidationException(ReservationValidationException exception) {
        logException(exception);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleBadCredentialsExceptionException(BadCredentialsException exception) {
        logException(exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity handleUnexpectedException(Exception exception) {
        logException(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    private void logException(Exception exception) {
        if(exception instanceof ReservationValidationException) {
            LOGGER.warn("Validation exception occured with message: {}", exception.getMessage());
        }
        LOGGER.error("Exception occured with cause: ", exception);
    }

}
