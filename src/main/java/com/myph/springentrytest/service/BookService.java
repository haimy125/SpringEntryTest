package com.myph.springentrytest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.myph.springentrytest.payload.BookDTO;

public interface BookService {

    /**
     * Creates a new book entry.
     *
     * @param newBook The details of the new book to create.
     * @return The created book as a BookDTO.
     */
    BookDTO createBook(BookDTO newBook);

    /**
     * Retrieves all books with pagination support.
     *
     * @param pageable The pagination information.
     * @return A paginated list of books as a Page of BookDTO.
     */
    Page<BookDTO> getAllBooks(Pageable pageable);

    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The book with the specified ID as a BookDTO.
     */
    BookDTO getBookById(String id);

    /**
     * Updates an existing book with new details.
     *
     * @param id The ID of the book to update.
     * @param newBook The new details of the book.
     * @return The updated book as a BookDTO.
     */
    BookDTO updateBook(String id, BookDTO newBook);

    /**
     * Deletes a book by its ID.
     *
     * @param id The ID of the book to delete.
     */
    void deleteBook(String id);
}
