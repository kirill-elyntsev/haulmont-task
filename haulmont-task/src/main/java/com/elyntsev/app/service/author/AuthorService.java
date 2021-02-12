package com.elyntsev.app.service.author;

import java.util.List;

import com.elyntsev.app.entity.Author;

public interface AuthorService {
	
	public List<Author> findAll();

	public Author findById(Long id);

	public void save(Author author);

	public void deleteById(Long id);

}
