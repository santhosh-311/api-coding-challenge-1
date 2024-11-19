package com.hexaware.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hexaware.dto.BookDTO;
import com.hexaware.exception.BookAlreadyExistsException;
import com.hexaware.exception.BookNotFoundException;

@Service
public interface BookService {
	
	List<BookDTO> getBooks() throws BookNotFoundException;
	BookDTO getBook(String isbn) throws BookNotFoundException;
	BookDTO addBook(BookDTO bookDto) throws BookAlreadyExistsException;
	String deleteBook(String isbn) throws BookNotFoundException;
	BookDTO updateBook(BookDTO bookDto, int bookId) throws BookNotFoundException, BookAlreadyExistsException;
}
