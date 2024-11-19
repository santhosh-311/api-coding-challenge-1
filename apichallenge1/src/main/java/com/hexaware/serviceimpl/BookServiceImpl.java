package com.hexaware.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.dto.BookDTO;
import com.hexaware.exception.BookAlreadyExistsException;
import com.hexaware.exception.BookNotFoundException;
import com.hexaware.model.Book;
import com.hexaware.repository.BookRepository;
import com.hexaware.service.BookService;

import jakarta.validation.ValidationException;


@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private ModelMapper model;

	@Override
	public List<BookDTO> getBooks() throws BookNotFoundException {
		List<Book> bookList= bookRepo.findAll();
		if(bookList.isEmpty()) {
			throw new BookNotFoundException("No Book Available");
		}
		return bookList.stream()
				.map(book-> model.map(book, BookDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public BookDTO getBook(String isbn) throws BookNotFoundException {
		Book book = bookRepo.findByIsbn(isbn);
		if(book==null) {
			throw new BookNotFoundException("Book Not Found | ISBN : "+isbn);
		}
		return model.map(book, BookDTO.class);
	}

	@Override
	public BookDTO addBook(BookDTO bookDto) throws BookAlreadyExistsException {
		Book book = bookRepo.findByIsbn(bookDto.getIsbn());
		if (bookDto.getYear() > LocalDate.now().getYear()) {
		    throw new ValidationException("Year must be in the past or present");
		}
		if(!bookDto.getIsbn().matches("\\d+")) {
			throw new ValidationException("ISBN should only contain numbers");
		}
		if(book!=null) {
			throw new BookAlreadyExistsException("Book Already Exists | ISBN : "+bookDto.getIsbn());
		}
		book=model.map(bookDto, Book.class);
		book = bookRepo.save(book);
		return model.map(book, BookDTO.class);
	}

	@Override
	public BookDTO updateBook(BookDTO bookDto, int bookId) throws BookNotFoundException, BookAlreadyExistsException {
		Book book = bookRepo.findById(bookId).orElseThrow(()->new BookNotFoundException("Book Not Found : "+bookId));
		if (book.getYear() > LocalDate.now().getYear()) {
		    throw new ValidationException("Year must be in the past or present");
		}
		
		Book book1=bookRepo.findByIsbn(bookDto.getIsbn());
		if(book1!=null && book.getBookId()!=book1.getBookId()) {
			throw new BookAlreadyExistsException("Book Already Exists | ISBN : "+bookDto.getIsbn());
		}
		book.setAuthor(bookDto.getAuthor());
		book.setIsbn(bookDto.getIsbn());
		book.setTitle(bookDto.getTitle());
		book.setYear(bookDto.getYear());
		
		book = bookRepo.save(book);
		return model.map(book, BookDTO.class);
	}

	@Override
	public String deleteBook(String isbn) throws BookNotFoundException {
		Book book = bookRepo.findByIsbn(isbn);
		if(book==null) {
			throw new BookNotFoundException("Book Not Found | ISBN : "+isbn);
		}
		bookRepo.delete(book);
		return "Book Deleted";
	}

}
