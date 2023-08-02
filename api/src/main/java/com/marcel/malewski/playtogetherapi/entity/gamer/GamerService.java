package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.auth.exception.EmailAlreadyUsedException;
import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.game.GameRepository;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateAuthRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.InvalidPasswordException;
import com.marcel.malewski.playtogetherapi.entity.genre.Genre;
import com.marcel.malewski.playtogetherapi.entity.genre.GenreRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformRepository;
import com.marcel.malewski.playtogetherapi.exception.sharedexception.GivenGameDoesNotExistException;
import com.marcel.malewski.playtogetherapi.exception.sharedexception.GivenGenreDoesNotExistException;
import com.marcel.malewski.playtogetherapi.exception.sharedexception.GivenPlatformDoesNotExistException;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamerService {
	private final GamerRepository gamerRepository;
	private final PlatformRepository platformRepository;
	private final GameRepository gameRepository;
	private final GenreRepository genreRepository;
	private final GamerMapper gamerMapper;
	private final PasswordEncoder passwordEncoder;


	public GamerService(GamerRepository gamerRepository, GamerMapper gamerMapper, PlatformRepository platformRepository, GameRepository gameRepository, GenreRepository genreRepository, PasswordEncoder passwordEncoder) {
		this.gamerRepository = gamerRepository;
		this.gamerMapper = gamerMapper;
		this.platformRepository = platformRepository;
		this.gameRepository = gameRepository;
		this.genreRepository = genreRepository;
		this.passwordEncoder = passwordEncoder;
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

		gamer.setLogin(updateProfileDto.login());
		gamer.setBirthdate(updateProfileDto.birthdate());
		gamer.setBio(updateProfileDto.bio());
		gamer.setAvatarUrl(updateProfileDto.avatarUrl());
		gamer.setPlayingTimeStart(updateProfileDto.playingTimeStart());
		gamer.setPlayingTimeEnd(updateProfileDto.playingTimeEnd());

		gamer.getPlatforms().clear();
		updateProfileDto.platformsIds().forEach(platformId -> {
			if (!platformRepository.existsById(platformId)) {
				throw new GivenPlatformDoesNotExistException(platformId);
			}

			Platform platform = platformRepository.getReferenceById(platformId);
			gamer.getPlatforms().add(platform);
		});

		gamer.getFavouriteGames().clear();
		updateProfileDto.favouriteGamesIds().forEach(favouriteGameId -> {
			if (!gameRepository.existsById(favouriteGameId)) {
				throw new GivenGameDoesNotExistException(favouriteGameId);
			}

			Game favouriteGame = gameRepository.getReferenceById(favouriteGameId);
			gamer.getFavouriteGames().add(favouriteGame);
		});

		gamer.getFavouriteGenres().clear();
		updateProfileDto.favouriteGenresIds().forEach(favouriteGenreId -> {
			if (!genreRepository.existsById(favouriteGenreId)) {
				throw new GivenGenreDoesNotExistException(favouriteGenreId);
			}

			Genre favouriteGenre = genreRepository.getReferenceById(favouriteGenreId);
			gamer.getFavouriteGenres().add(favouriteGenre);
		});

		Gamer updatedGamer = gamerRepository.save(gamer);
		return gamerMapper.toGamerPrivateResponseDto(updatedGamer);
	}

	public GamerPrivateResponseDto updateGamerAuth(@NotNull GamerUpdateAuthRequestDto updateAuthDto, long id) {
		Gamer gamer = gamerRepository.findById(id).orElseThrow(() -> new GamerNotFoundException(id));

		String email = updateAuthDto.email();
		if (gamerRepository.existsByEmail(email)) {
			throw new EmailAlreadyUsedException(email);
		}

		if (!passwordEncoder.matches(updateAuthDto.currentPassword(), gamer.getPassword())) {
			throw new InvalidPasswordException();
		}

		return null;
	}

}
