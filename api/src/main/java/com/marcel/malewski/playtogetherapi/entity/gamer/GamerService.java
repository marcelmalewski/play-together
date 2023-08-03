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
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.LoginAlreadyUsedException;
import com.marcel.malewski.playtogetherapi.entity.genre.Genre;
import com.marcel.malewski.playtogetherapi.entity.genre.GenreService;
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
	private final GenreService genreService;
	private final GamerMapper gamerMapper;
	private final PasswordEncoder passwordEncoder;


	public GamerService(GamerRepository gamerRepository, PlatformService platformService, GenreService genreService, GamerMapper gamerMapper, GameService gameService, PasswordEncoder passwordEncoder) {
		this.gamerRepository = gamerRepository;
		this.platformService = platformService;
		this.genreService = genreService;
		this.gamerMapper = gamerMapper;
		this.gameService = gameService;
		this.passwordEncoder = passwordEncoder;
	}

	public void throwExceptionIfLoginIsAlreadyUsed(String login) {
		if (gamerRepository.existsByLogin(login)) {
			throw new LoginAlreadyUsedException(login);
		}
	}

	public void throwExceptionIfEmailIsAlreadyUsed(String email) {
		if (gamerRepository.existsByEmail(email)) {
			throw new EmailAlreadyUsedException(email);
		}
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

		String newLogin = updateProfileDto.login();
		if (!gamer.getLogin().equals(newLogin) && gamerRepository.existsByLogin(newLogin)) {
			throw new LoginAlreadyUsedException(newLogin);
		}

		gamer.setLogin(newLogin);
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
			Genre favouriteGenre = genreService.getReferenceOfGivenGame(favouriteGenreId);
			gamer.getFavouriteGenres().add(favouriteGenre);
		});

		Gamer updatedGamer = gamerRepository.save(gamer);
		return gamerMapper.toGamerPrivateResponseDto(updatedGamer);
	}

	public GamerPrivateResponseDto updateGamerAuth(@NotNull GamerUpdateAuthRequestDto updateAuthDto, long id) {
		Gamer gamer = gamerRepository.findById(id).orElseThrow(() -> new GamerNotFoundException(id));

		String email = updateAuthDto.email();
		if (!gamer.getEmail().equals(updateAuthDto.email()) && gamerRepository.existsByEmail(email)) {
			throw new EmailAlreadyUsedException(email);
		}

		if (!passwordEncoder.matches(updateAuthDto.currentPassword(), gamer.getPassword())) {
			throw new InvalidPasswordException();
		}
		String updatedPassword = passwordEncoder.encode(updateAuthDto.newPassword());

		gamer.setLogin(email);
		gamer.setPassword(updatedPassword);

		Gamer updatedGamer = gamerRepository.save(gamer);
		return gamerMapper.toGamerPrivateResponseDto(updatedGamer);
	}

	public Gamer saveGamer(Gamer gamer) {
		return gamerRepository.save(gamer);
	}
}
