package com.hexaware.model;

import jakarta.persistence.*;

@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int bookId;
	private String title;
	private String author;
	private String isbn;
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
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Book(int bookId, String title, String author, String isbn, int year) {
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
	
	
	
//	Title
//	• Author
//	• ISBN (International Standard Book Number)
//	• Publication Year
}
