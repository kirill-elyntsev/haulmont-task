package com.elyntsev.app.service.book;

import java.util.List;

import com.elyntsev.app.entity.Book;

public interface BookService {

	public List<Book> findAll();

	public Book findById(Long id);

	public void save(Book book);

	public void deleteById(Long id);

	public List<Book> findByName(String name);

	public List<Book> findByPublisher(String publisher);


}
