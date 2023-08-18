package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.game.GameService;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateAuthenticationDataRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.EmailAlreadyUsedException;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.LoginAlreadyUsedException;
import com.marcel.malewski.playtogetherapi.entity.genre.Genre;
import com.marcel.malewski.playtogetherapi.entity.genre.GenreService;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformService;
import com.marcel.malewski.playtogetherapi.security.exception.AuthenticatedGamerNotFoundException;
import com.marcel.malewski.playtogetherapi.security.exception.InvalidPasswordException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

import static com.marcel.malewski.playtogetherapi.util.PrincipalExtractor.extractIdFromPrincipal;
import static com.marcel.malewski.playtogetherapi.util.Security.LogoutManually;

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

	public List<GamerPublicResponseDto> findAllGamersPublicInfo() {
		return gamerRepository.findAll().stream().map(gamerMapper::toGamerPublicResponseDto).toList();
	}

	public List<GamerPrivateResponseDto> findAllGamersPrivateInfo() {
		return gamerRepository.findAll().stream().map(gamerMapper::toGamerPrivateResponseDto).toList();
	}

	public GamerPublicResponseDto getGamerPublicInfo(long gamerId) {
		Gamer gamer = gamerRepository.findById(gamerId).orElseThrow(() -> new GamerNotFoundException(gamerId));
		return gamerMapper.toGamerPublicResponseDto(gamer);
	}

	public GamerPrivateResponseDto getGamerPrivateInfo(long gamerId) {
		Gamer gamer = gamerRepository.findById(gamerId).orElseThrow(() -> new GamerNotFoundException(gamerId));
		return gamerMapper.toGamerPrivateResponseDto(gamer);
	}

	public Gamer getGamerReference(long gamerId) {
		if(!gamerRepository.existsById(gamerId)) {
			throw new GamerNotFoundException(gamerId);
		}

		return gamerRepository.getReferenceById(gamerId);
	}

	public void saveGamer(Gamer gamer) {
		gamerRepository.save(gamer);
	}

	public GamerPrivateResponseDto updateGamerProfile(@NotNull GamerUpdateProfileRequestDto updateProfileDto, long gamerId) {
		Gamer gamer = gamerRepository.findById(gamerId).orElseThrow(() -> new GamerNotFoundException(gamerId));

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
			Genre favouriteGenre = genreService.getReferenceOfGivenGenre(favouriteGenreId);
			gamer.getFavouriteGenres().add(favouriteGenre);
		});

		Gamer updatedGamer = gamerRepository.save(gamer);
		return gamerMapper.toGamerPrivateResponseDto(updatedGamer);
	}

	public GamerPrivateResponseDto updatePartiallyGamerAuthenticationData(@NotNull GamerUpdateAuthenticationDataRequestDto updateAuthDto, long gamerId) {
		Gamer gamer = gamerRepository.findById(gamerId).orElseThrow(() -> new GamerNotFoundException(gamerId));

		if (!passwordEncoder.matches(updateAuthDto.currentPassword(), gamer.getPassword())) {
			throw new InvalidPasswordException();
		}

		if(updateAuthDto.email() != null) {
			String newEmail = updateAuthDto.email();
			if (!gamer.getEmail().equals(updateAuthDto.email()) && gamerRepository.existsByEmail(newEmail)) {
				throw new EmailAlreadyUsedException(newEmail);
			}

			gamer.setEmail(newEmail);
		}

		if(updateAuthDto.newPassword() != null) {
			String newPasswordEncoded = passwordEncoder.encode(updateAuthDto.newPassword());
			gamer.setPassword(newPasswordEncoded);
		}

		Gamer updatedGamer = gamerRepository.save(gamer);
		return gamerMapper.toGamerPrivateResponseDto(updatedGamer);
	}

	public void deleteGamer(long gamerId) {
		if (!gamerRepository.existsById(gamerId)) {
			throw new GamerNotFoundException(gamerId);
		}

		gamerRepository.deleteById(gamerId);
	}

	public void throwExceptionIfLoginIsAlreadyUsed(@NotNull String login) {
		if (gamerRepository.existsByLogin(login)) {
			throw new LoginAlreadyUsedException(login);
		}
	}

	public void throwExceptionIfEmailIsAlreadyUsed(@NotNull String email) {
		if (gamerRepository.existsByEmail(email)) {
			throw new EmailAlreadyUsedException(email);
		}
	}

	public void throwExceptionAndLogoutIfAuthenticatedGamerNotFound(@NotNull Principal principal, HttpServletRequest request,
	                                                                HttpServletResponse response) {
		long principalId = extractIdFromPrincipal(principal);

		if (!gamerRepository.existsById(principalId)) {
			LogoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
	}
}
