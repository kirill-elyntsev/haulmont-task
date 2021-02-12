package com.elyntsev.app.service.author;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elyntsev.app.entity.Author;
import com.elyntsev.app.repository.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService{

	private AuthorRepository authorRepository;

	@Autowired
	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	public List<Author> findAll() {
		return authorRepository.findAll();

	}

	@Override
	public Author findById(Long id) {
		
		Optional<Author> result = authorRepository.findById(id);
		
		Author author = null;
		
		if (result.isPresent()) {
			author = result.get();
		} else {
			throw new RuntimeException("Did not find Author id - " + id);
		}
		return author;
	}

	@Override
	public void save(Author author) {
		authorRepository.save(author);
	}

	@Override
	public void deleteById(Long id) {
		authorRepository.deleteById(id);
	}

}
