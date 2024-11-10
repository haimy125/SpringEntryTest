package com.myph.springentrytest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.myph.springentrytest.payload.ResponseData;


@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle BookNotFoundException globally and return a ResponseEntity.
     *
     * @param ex the exception
     * @return ResponseEntity with custom error message and status
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ResponseData<Void>> handleBookNotFoundException(BookNotFoundException ex) {
        ResponseData<Void> response = new ResponseData<>("error", ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 status for not found
    }

    /**
     * Handle generic exception and return a ResponseEntity.
     *
     * @param ex the exception
     * @return ResponseEntity with custom error message and status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData<Void>> handleGenericException(Exception ex) {
        ResponseData<Void> response = new ResponseData<>("error", "Internal Server Error", null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500 status for general errors
    }
}
