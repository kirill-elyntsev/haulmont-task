package com.elyntsev.app.service.genre;

import java.util.List;

import com.elyntsev.app.entity.Genre;

public interface GenreService {

	public List<Genre> findAll();

	public Genre findById(Long id);

	public void save(Genre genre);

	public void deleteById(Long id);

}
