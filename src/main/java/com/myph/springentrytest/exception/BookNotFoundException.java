package com.myph.springentrytest.exception;

/**
 * Custom exception thrown when a book is not found in the database.
 */
public class BookNotFoundException extends RuntimeException{

    /**
     * Constructs a new BookNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public BookNotFoundException(String message) {
        super(message);
    }
}
