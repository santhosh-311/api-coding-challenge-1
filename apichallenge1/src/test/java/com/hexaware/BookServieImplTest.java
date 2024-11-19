package com.hexaware;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hexaware.dto.BookDTO;
import com.hexaware.exception.BookAlreadyExistsException;
import com.hexaware.exception.BookNotFoundException;
import com.hexaware.model.Book;
import com.hexaware.repository.BookRepository;
import com.hexaware.serviceimpl.BookServiceImpl;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepo;

    @Mock
    private ModelMapper model;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBooks_Success() throws BookNotFoundException {
        List<Book> books = Arrays.asList(
                new Book(1, "Book 1", "Author 1", "1234567890123", 2020),
                new Book(2, "Book 2", "Author 2", "1234567890124", 2021)
        );

        when(bookRepo.findAll()).thenReturn(books);
        when(model.map(any(Book.class), eq(BookDTO.class)))
                .thenReturn(new BookDTO(1, "Book 1", "Author 1", "1234567890123", 2020))
                .thenReturn(new BookDTO(2, "Book 2", "Author 2", "1234567890124", 2021));

        List<BookDTO> result = bookService.getBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepo, times(1)).findAll();
    }

    @Test
    void testGetBooks_NotFound() {
        when(bookRepo.findAll()).thenReturn(List.of());
        assertThrows(BookNotFoundException.class, () -> bookService.getBooks());
    }

    @Test
    void testGetBook_Success() throws BookNotFoundException {
        Book book = new Book(1, "Book 1", "Author 1", "1234567890123", 2020);
        when(bookRepo.findByIsbn("1234567890123")).thenReturn(book);
        when(model.map(book, BookDTO.class)).thenReturn(new BookDTO(1, "Book 1", "Author 1", "1234567890123", 2020));

        BookDTO result = bookService.getBook("1234567890123");

        assertNotNull(result);
        assertEquals("Book 1", result.getTitle());
        verify(bookRepo, times(1)).findByIsbn("1234567890123");
    }

    @Test
    void testGetBook_NotFound() {
        when(bookRepo.findByIsbn("1234567890123")).thenReturn(null);
        assertThrows(BookNotFoundException.class, () -> bookService.getBook("1234567890123"));
    }

    @Test
    void testAddBook_Success() throws BookAlreadyExistsException {
        BookDTO bookDto = new BookDTO(1, "Book 1", "Author 1", "1234567890123", 2020);
        Book book = new Book(1, "Book 1", "Author 1", "1234567890123", 2020);

        when(bookRepo.findByIsbn("1234567890123")).thenReturn(null);
        when(model.map(bookDto, Book.class)).thenReturn(book);
        when(bookRepo.save(book)).thenReturn(book);
        when(model.map(book, BookDTO.class)).thenReturn(bookDto);

        BookDTO result = bookService.addBook(bookDto);

        assertNotNull(result);
        assertEquals("Book 1", result.getTitle());
        verify(bookRepo, times(1)).findByIsbn("1234567890123");
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    void testAddBook_AlreadyExists() {
        Book book = new Book(1, "Book 1", "Author 1", "1234567890123", 2020);
        when(bookRepo.findByIsbn("1234567890123")).thenReturn(book);

        BookDTO bookDto = new BookDTO(1, "Book 1", "Author 1", "1234567890123", 2020);

        assertThrows(BookAlreadyExistsException.class, () -> bookService.addBook(bookDto));
        verify(bookRepo, times(1)).findByIsbn("1234567890123");
    }

    @Test
    void testAddBook_InvalidYear() {
        BookDTO bookDto = new BookDTO(1, "Book 1", "Author 1", "1234567890123", LocalDate.now().getYear() + 1);

        assertThrows(ValidationException.class, () -> bookService.addBook(bookDto));
    }

    @Test
    void testUpdateBook_Success() throws BookNotFoundException, BookAlreadyExistsException {
        Book book = new Book(1, "Old Book", "Old Author", "1234567890123", 2019);
        BookDTO bookDto = new BookDTO(1, "Updated Book", "Updated Author", "1234567890123", 2020);

        when(bookRepo.findById(1)).thenReturn(Optional.of(book));
        when(bookRepo.findByIsbn("1234567890123")).thenReturn(null);
        when(bookRepo.save(book)).thenReturn(book);
        when(model.map(book, BookDTO.class)).thenReturn(bookDto);

        BookDTO result = bookService.updateBook(bookDto, 1);

        assertNotNull(result);
        assertEquals("Updated Book", result.getTitle());
        verify(bookRepo, times(1)).findById(1);
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    void testDeleteBook_Success() throws BookNotFoundException {
        Book book = new Book(1, "Book 1", "Author 1", "1234567890123", 2020);
        when(bookRepo.findByIsbn("1234567890123")).thenReturn(book);

        String result = bookService.deleteBook("1234567890123");

        assertEquals("Book Deleted", result);
        verify(bookRepo, times(1)).findByIsbn("1234567890123");
        verify(bookRepo, times(1)).delete(book);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepo.findByIsbn("1234567890123")).thenReturn(null);
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook("1234567890123"));
    }
}
