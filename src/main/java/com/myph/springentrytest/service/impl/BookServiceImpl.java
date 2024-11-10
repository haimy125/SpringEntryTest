package com.myph.springentrytest.service.impl;

import com.myph.springentrytest.entity.Book;
import com.myph.springentrytest.exception.BookNotFoundException;
import com.myph.springentrytest.helper.IsbnGeneratorService;
import com.myph.springentrytest.payload.BookDTO;
import com.myph.springentrytest.repository.BookRepository;
import com.myph.springentrytest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private IsbnGeneratorService isbnGeneratorService;

    /**
     * Converts a Book entity to a BookDTO.
     */
    private BookDTO convertToDTO(Book book) {
        return new BookDTO (
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublishedDate(),
                book.getIsbn(),
                book.getPrice()
        );
    }

    /**
     * Converts a BookDTO a Book entity.
     */
    private Book convertToEntity(BookDTO bookDTO) {
        return new Book(
                bookDTO.getId(),
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getPublishedDate(),
                bookDTO.getIsbn(),
                bookDTO.getPrice()
        );
    }

    /**
     * Creates a new book.
     *
     * @param newBook the BookDTO object to create
     * @return the created BookDTO
     */
    @Override
    public BookDTO createBook(BookDTO newBook) {

        if (newBook == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        Optional<Book> existingBook = bookRepository.findByIsbn(newBook.getIsbn());
        if (existingBook.isPresent()) {
            throw new IllegalArgumentException("A book with the same ISBN already exists");
        }

        newBook.setIsbn(isbnGeneratorService.generateIsbn13());

        Book book = convertToEntity(newBook);
        Book savedBook = bookRepository.save(book);

        return convertToDTO(savedBook);
    }

    /**
     * Retrieves all books with pagination.
     *
     * @param pageable the Pageable object for pagination
     * @return a list of BookDTOs
     */
    @Override
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10);
        }
        return bookRepository.findAll(pageable).map(this::convertToDTO);
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book
     * @return the BookDTO of the found book
     */
    @Override
    public BookDTO getBookById(String id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        return convertToDTO(book);
    }

    /**
     * Updates a book's details by ID.
     *
     * @param id      the ID of the book
     * @param newBook the new BookDTO data
     * @return the updated BookDTO
     */
    @Override
    public BookDTO updateBook(String id, BookDTO newBook) {

        if (newBook == null) {
            throw new IllegalArgumentException("Updated book data cannot be null");
        }

        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        book.setTitle(newBook.getTitle());
        book.setAuthor(newBook.getAuthor());
        book.setPublishedDate(newBook.getPublishedDate());
        book.setIsbn(newBook.getIsbn());
        book.setPrice(newBook.getPrice());

        Book updatedBook = bookRepository.save(book);
        return convertToDTO(updatedBook);
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to delete
     */
    @Override
    public void deleteBook(String id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}
