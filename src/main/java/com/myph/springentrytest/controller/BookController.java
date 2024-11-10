package com.myph.springentrytest.controller;

import com.myph.springentrytest.payload.BookDTO;
import com.myph.springentrytest.payload.ResponseData;
import com.myph.springentrytest.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * Creates a new book.
     *
     * @param book BookDTO object containing book details
     * @return ResponseEntity with a ResponseData object containing the created book
     */
    @PostMapping("")
    public ResponseEntity<ResponseData<BookDTO>> createBook(@Valid @RequestBody BookDTO book) {
        BookDTO savedBook = bookService.createBook(book);
        ResponseData<BookDTO> response = new ResponseData<>("success", "Book created successfully", savedBook);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves all books with pagination.
     *
     * @param page page number (default: 0)
     * @param size number of items per page (default: 10)
     * @return ResponseEntity with a ResponseData object containing the list of books
     */
    @GetMapping("")
    public ResponseEntity<ResponseData<List<BookDTO>>> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookDTO> books = bookService.getAllBooks(pageable);

        if (books.isEmpty()) {
            ResponseData<List<BookDTO>> response = new ResponseData<>("success", "No books found", List.of());
            return ResponseEntity.ok(response);
        }
        List<BookDTO> lstBookDTOS =books.getContent();
        ResponseData<List<BookDTO>> response = new ResponseData<>("success", "Books fetched successfully", lstBookDTOS);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book
     * @return ResponseEntity with a ResponseData object containing the book
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<BookDTO>> getBookById(@PathVariable String id) {
        BookDTO book = bookService.getBookById(id);
        ResponseData<BookDTO> responseData = new ResponseData<>("success", "Found book with id = " + id, book);
        return ResponseEntity.ok(responseData);
    }

    /**
     * Updates an existing book.
     *
     * @param id   the ID of the book to update
     * @param book BookDTO object with updated book details
     * @return ResponseEntity with a ResponseData object containing the updated book
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<BookDTO>> updateBook(@PathVariable String id, @Valid @RequestBody BookDTO book) {
        BookDTO updatedBook = bookService.updateBook(id, book);
        ResponseData<BookDTO> response = new ResponseData<>("success", "Book updated successfully", updatedBook);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to delete
     * @return ResponseEntity with a ResponseData object indicating successful deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Void>> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        ResponseData<Void> response = new ResponseData<>("success", "Book deleted successfully", null);
        return ResponseEntity.noContent().build();
    }
}
