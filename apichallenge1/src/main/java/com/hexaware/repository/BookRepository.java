package com.hexaware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.hexaware.model.Book;

@Component
@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
	
	Book findByIsbn(String isbn);

}
