package com.elyntsev.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elyntsev.app.entity.Author;
import com.elyntsev.app.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

	List<Book> findByName(String name);
	
	List<Book> findByPublisher(String publisher);
	
	List<Book> findByAuthor(Author author);
}
