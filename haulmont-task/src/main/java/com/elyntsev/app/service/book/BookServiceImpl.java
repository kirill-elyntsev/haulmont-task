package com.elyntsev.app.service.book;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elyntsev.app.entity.Book;
import com.elyntsev.app.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{
	
	private BookRepository bookRepository;
	
	@Autowired
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@Override
	public List<Book> findAll() {
		return bookRepository.findAll();

	}

	@Override
	public Book findById(Long id) {
		
		Optional<Book> result = bookRepository.findById(id);
		
		Book Book = null;
		
		if (result.isPresent()) {
			Book = result.get();
		} else {
			throw new RuntimeException("Did not find Book id - " + id);
		}
		return Book;
	}

	@Override
	public void save(Book book) {
		bookRepository.save(book);
	}

	@Override
	public void deleteById(Long id) {
		bookRepository.deleteById(id);
	}

	@Override
	public List<Book> findByName(String name) {
		return bookRepository.findByName(name);
		
	}

	@Override
	public List<Book> findByPublisher(String publisher) {
		return bookRepository.findByPublisher(publisher);
	}


}
