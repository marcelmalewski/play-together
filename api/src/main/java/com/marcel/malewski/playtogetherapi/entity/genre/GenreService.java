package com.marcel.malewski.playtogetherapi.entity.genre;

import com.marcel.malewski.playtogetherapi.entity.genre.exception.GivenGenreDoesNotExistException;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
	private final GenreRepository genreRepository;

	public GenreService(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	public Genre getReferenceOfGivenGenre(long genreId) {
		if (!genreRepository.existsById(genreId)) {
			throw new GivenGenreDoesNotExistException(genreId);
		}

		return genreRepository.getReferenceById(genreId);
	}
}
