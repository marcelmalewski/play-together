package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.game.GameRepository;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateAuthRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
import com.marcel.malewski.playtogetherapi.entity.genre.Genre;
import com.marcel.malewski.playtogetherapi.entity.genre.GenreRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformRepository;
import com.marcel.malewski.playtogetherapi.exception.sharedexception.GivenPlatformDoesNotExistException;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamerService {
	private final GamerRepository gamerRepository;
	private final PlatformRepository platformRepository;
	private final GameRepository gameRepository;
	private final GenreRepository genreRepository;
	private final GamerMapper gamerMapper;

	public GamerService(GamerRepository gamerRepository, GamerMapper gamerMapper, PlatformRepository platformRepository, GameRepository gameRepository, GenreRepository genreRepository) {
		this.gamerRepository = gamerRepository;
		this.gamerMapper = gamerMapper;
		this.platformRepository = platformRepository;
		this.gameRepository = gameRepository;
		this.genreRepository = genreRepository;
	}

	public List<GamerPublicResponseDto> findAllGamers() {
		return gamerRepository.findAll().stream().map(gamerMapper::toGamerPublicResponseDto).toList();
	}

	public GamerPublicResponseDto getGamerPublicInfo(long id) {
		Gamer gamer = gamerRepository.findById(id).orElseThrow(() -> new GamerNotFoundException(id));
		return gamerMapper.toGamerPublicResponseDto(gamer);
	}

	public GamerPrivateResponseDto getGamerPrivateInfo(long id) {
		Gamer gamer = gamerRepository.findById(id).orElseThrow(() -> new GamerNotFoundException(id));
		return gamerMapper.toGamerPrivateResponseDto(gamer);
	}

	public GamerPrivateResponseDto updateGamerProfile(@NotNull GamerUpdateProfileRequestDto updateProfileDto, long id) {
		Gamer gamer = gamerRepository.findById(id).orElseThrow(() -> new GamerNotFoundException(id));

		List<Platform> platforms = updateProfileDto.platforms().stream().map(platformId -> {
			if (!platformRepository.existsById(platformId)) {
				throw new GivenPlatformDoesNotExistException(platformId);
			}

			return platformRepository.getReferenceById(platformId);
		}).toList();

		List<Game> favouriteGames = updateProfileDto.favouriteGames().stream().map(favouriteGameId -> {
			if (!gameRepository.existsById(favouriteGameId)) {
				throw new GivenPlatformDoesNotExistException(favouriteGameId);
			}

			return gameRepository.getReferenceById(favouriteGameId);
		}).toList();

		List<Genre> favouriteGenres = updateProfileDto.favouriteGenres().stream().map(favouriteGenreId -> {
			if (!genreRepository.existsById(favouriteGenreId)) {
				throw new GivenPlatformDoesNotExistException(favouriteGenreId);
			}

			return genreRepository.getReferenceById(favouriteGenreId);
		}).toList();

		gamer.setLogin(updateProfileDto.login());
		gamer.setBirthdate(updateProfileDto.birthdate());
		gamer.setBio(updateProfileDto.bio());
		gamer.setAvatarUrl(updateProfileDto.avatarUrl());
		gamer.setPlayingTimeStart(updateProfileDto.playingTimeStart());
		gamer.setPlayingTimeEnd(updateProfileDto.playingTimeEnd());
		gamer.setPlatforms(platforms);
		gamer.setFavouriteGames(favouriteGames);
		gamer.setFavouriteGenres(favouriteGenres);
		Gamer updatedGamer = gamerRepository.save(gamer);

		return gamerMapper.toGamerPrivateResponseDto(updatedGamer);
	}

	public GamerPrivateResponseDto updateGamerAuth(@NotNull GamerUpdateAuthRequestDto updateAuthDto, long id) {
		Gamer gamer = gamerRepository.findById(id).orElseThrow(() -> new GamerNotFoundException(id));

		return null;
	}

}
