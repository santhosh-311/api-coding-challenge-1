package com.hexaware.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.dto.BookDTO;
import com.hexaware.exception.BookAlreadyExistsException;
import com.hexaware.exception.BookNotFoundException;
import com.hexaware.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/getbooks")
	public ResponseEntity<List<BookDTO>> getBooks() throws BookNotFoundException{
		List<BookDTO> bookList=bookService.getBooks();
		return new ResponseEntity<>(bookList,HttpStatus.OK);
	}
	
	@GetMapping("/getbook/{isbn}")
	public ResponseEntity<BookDTO> getBook(@PathVariable String isbn) throws BookNotFoundException{
		BookDTO book = bookService.getBook(isbn);
		return new ResponseEntity<>(book,HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDto) throws BookAlreadyExistsException{
		BookDTO book = bookService.addBook(bookDto);
		return new ResponseEntity<>(book,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{bookId}")
	public ResponseEntity<BookDTO> updateBook(@Valid @RequestBody BookDTO bookDto,@PathVariable int bookId ) throws BookNotFoundException, BookAlreadyExistsException{
		BookDTO book = bookService.updateBook(bookDto, bookId);
		return new ResponseEntity<>(book,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{isbn}")
	public ResponseEntity<String> deleteBook(@PathVariable String isbn) throws BookNotFoundException{
		String s = bookService.deleteBook(isbn);
		return new ResponseEntity<>(s,HttpStatus.OK);

	}
	
}
