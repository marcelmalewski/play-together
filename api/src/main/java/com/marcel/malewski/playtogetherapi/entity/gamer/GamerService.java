package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.auth.exception.InvalidPasswordException;
import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.game.exception.GameService;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateAuthRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.EmailAlreadyUsedException;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
import com.marcel.malewski.playtogetherapi.entity.genre.Genre;
import com.marcel.malewski.playtogetherapi.entity.genre.GenreRepository;
import com.marcel.malewski.playtogetherapi.entity.genre.exception.GivenGenreDoesNotExistException;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformService;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamerService {
	private final GamerRepository gamerRepository;
	private final PlatformService platformService;
	private final GameService gameService;
	private final GenreRepository genreRepository;
	private final GamerMapper gamerMapper;
	private final PasswordEncoder passwordEncoder;


	public GamerService(GamerRepository gamerRepository, PlatformService platformService, GamerMapper gamerMapper, GameService gameService, GenreRepository genreRepository, PasswordEncoder passwordEncoder) {
		this.gamerRepository = gamerRepository;
		this.platformService = platformService;
		this.gamerMapper = gamerMapper;
		this.gameService = gameService;
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
			Platform platform = platformService.getReferenceOfGivenPlatform(platformId);
			gamer.getPlatforms().add(platform);
		});

		gamer.getFavouriteGames().clear();
		updateProfileDto.favouriteGamesIds().forEach(favouriteGameId -> {
			Game favouriteGame = gameService.getReferenceOfGivenGame(favouriteGameId);
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
