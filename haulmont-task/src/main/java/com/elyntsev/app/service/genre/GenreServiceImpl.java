package com.elyntsev.app.service.genre;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elyntsev.app.entity.Genre;
import com.elyntsev.app.repository.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService {

	private GenreRepository genreRepository;

	@Autowired
	public GenreServiceImpl(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	@Override
	public List<Genre> findAll() {
		return genreRepository.findAll();

	}

	@Override
	public Genre findById(Long id) {
		
		Optional<Genre> result = genreRepository.findById(id);
		
		Genre genre = null;
		
		if (result.isPresent()) {
			genre = result.get();
		} else {
			throw new RuntimeException("Did not find genre id - " + id);
		}
		return genre;
	}

	@Override
	public void save(Genre genre) {
		genreRepository.save(genre);
	}

	@Override
	public void deleteById(Long id) {
		genreRepository.deleteById(id);
	}

}
