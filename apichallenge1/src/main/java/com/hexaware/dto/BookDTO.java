package com.hexaware.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class BookDTO {
	private int bookId;
	@NotBlank(message="Title cannot be empty")
    @Pattern(regexp = "^[A-Za-z ]{5,30}$", message = "Can contain alphabets,spaces, length should be between 5 and 30")
	private String title;
	
	@NotBlank(message="Author Name cannot be empty")
	@Pattern(regexp="^[a-zA-Z .]{5,20}$", message="Can contain only Alphabets,spaces and '.', length should be between 5 and 20")
	private String author;
	
	@NotBlank(message="ISBN Should not be empty")
	@Size(min = 13, max = 13, message = "ISBN must be exactly 13 characters long")
	private String isbn;
	
	
	@PositiveOrZero(message = "Year cannot be negative")
	@Min(value = 1000, message = "Year cannot be empty or below 1000")
	private int year;
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public BookDTO() {
	}
	public BookDTO(int bookId, String title, String author, String isbn, int year) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.year = year;
	}
	@Override
	public String toString() {
		return String.format("Book [bookId=%s, title=%s, author=%s, isbn=%s, year=%s]", bookId, title, author, isbn,
				year);
	}
}
