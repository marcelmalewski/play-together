package com.marcel.malewski.playtogetherapi.entity.genre;

import com.marcel.malewski.playtogetherapi.entity.genre.exception.GivenGenreDoesNotExistException;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
	private final GenreRepository genreRepository;

	public GenreService(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	public Genre getReferenceOfGivenGame(long id) {
		if (!genreRepository.existsById(id)) {
			throw new GivenGenreDoesNotExistException(id);
		}

		return genreRepository.getReferenceById(id);
	}
}
