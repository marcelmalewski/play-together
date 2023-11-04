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
import com.marcel.malewski.playtogetherapi.security.util.PrincipalExtractor;
import com.marcel.malewski.playtogetherapi.security.util.SecurityHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.marcel.malewski.playtogetherapi.validation.DateTimeParser.parseToDate;
import static com.marcel.malewski.playtogetherapi.validation.DateTimeParser.parseToTime;

@Service
@Validated
public class GamerService {
	private final GamerRepository gamerRepository;
	private final PlatformService platformService;
	private final GameService gameService;
	private final GenreService genreService;
	private final GamerMapper gamerMapper;
	private final BCryptPasswordEncoder passwordEncoder;
	private final SecurityHelper securityHelper;
	private final PrincipalExtractor principalExtractor;


	public GamerService(GamerRepository gamerRepository, PlatformService platformService, GenreService genreService, GamerMapper gamerMapper, GameService gameService, BCryptPasswordEncoder passwordEncoder, SecurityHelper securityHelper, PrincipalExtractor principalExtractor) {
		this.gamerRepository = gamerRepository;
		this.platformService = platformService;
		this.genreService = genreService;
		this.gamerMapper = gamerMapper;
		this.gameService = gameService;
		this.passwordEncoder = passwordEncoder;
		this.securityHelper = securityHelper;
		this.principalExtractor = principalExtractor;
	}

	public List<GamerPublicResponseDto> findAllGamersPublicInfo() {
		return gamerRepository.findAll().stream().map(gamerMapper::toGamerPublicResponseDto).toList();
	}

	public List<GamerPrivateResponseDto> findAllGamersPrivateInfo() {
		return gamerRepository.findAll().stream().map(gamerMapper::toGamerPrivateResponseDto).toList();
	}

	public Optional<GamerPublicResponseDto> findGamerPublicInfo(long gamerId) {
		Optional<Gamer> optionalGamer = gamerRepository.findById(gamerId);
		return Optional.ofNullable(
			gamerMapper.toGamerPublicResponseDto(optionalGamer.orElse(null))
		);
	}


	public Optional<GamerPrivateResponseDto> findGamerPrivateInfo(long gamerId) {
		Optional<Gamer> optionalGamer = gamerRepository.findById(gamerId);
		return Optional.ofNullable(
			gamerMapper.toGamerPrivateResponseDto(optionalGamer.orElse(null))
		);
	}

	//TODO poprawa na optional
	public Gamer getGamerReference(long gamerId) {
		if(!gamerRepository.existsById(gamerId)) {
			throw new GamerNotFoundException(gamerId);
		}

		return gamerRepository.getReferenceById(gamerId);
	}

	public boolean gamerExistsByLogin(@NotNull String login) {
		return gamerRepository.existsByLogin(login);
	}

	public boolean gamerExistsByEmail(@NotNull String email) {
		return gamerRepository.existsByEmail(email);
	}

	public Long saveGamer(@NotNull Gamer gamer) {
		Gamer savedGamer = gamerRepository.save(gamer);
		return savedGamer.getId();
	}

	public Optional<GamerPrivateResponseDto> tryUpdateGamerProfile(@NotNull GamerUpdateProfileRequestDto updateProfileDto, long gamerId) {
		Optional<Gamer> optionalGamer = gamerRepository.findById(gamerId);
		if(optionalGamer.isEmpty()) {
			return Optional.ofNullable(gamerMapper.toGamerPrivateResponseDto(null));
		}
		Gamer gamer = optionalGamer.get();

		String newLogin = updateProfileDto.login();
		if (!gamer.getLogin().equals(newLogin) && gamerRepository.existsByLogin(newLogin)) {
			throw new LoginAlreadyUsedException(newLogin);
		}

		LocalDate birthdate = parseToDate(updateProfileDto.birthdateAsString());
		LocalTime playingTimeStart = parseToTime(updateProfileDto.playingTimeStartAsString());
		LocalTime playingTimeEnd = parseToTime(updateProfileDto.playingTimeEndAsString());

		gamer.setLogin(newLogin);
		gamer.setBirthdate(birthdate);
		gamer.setBio(updateProfileDto.bio());
		gamer.setAvatarUrl(updateProfileDto.avatarUrl());
		gamer.setPlayingTimeStart(playingTimeStart);
		gamer.setPlayingTimeEnd(playingTimeEnd);

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
		return Optional.ofNullable(
			gamerMapper.toGamerPrivateResponseDto(updatedGamer)
		);
	}

	public Optional<GamerPrivateResponseDto> tryUpdatePartiallyGamerAuthenticationData(@NotNull GamerUpdateAuthenticationDataRequestDto updateAuthDto, long gamerId) {
		Optional<Gamer> optionalGamer = gamerRepository.findById(gamerId);
		if(optionalGamer.isEmpty()) {
			return Optional.ofNullable(gamerMapper.toGamerPrivateResponseDto(null));
		}
		Gamer gamer = optionalGamer.get();

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

		//TODO nie można zmienić na to samo hasło
		if(updateAuthDto.newPassword() != null) {
			String newPasswordEncoded = passwordEncoder.encode(updateAuthDto.newPassword());
			gamer.setPassword(newPasswordEncoded);
		}

		Gamer updatedGamer = gamerRepository.save(gamer);
		return Optional.ofNullable(
			gamerMapper.toGamerPrivateResponseDto(updatedGamer)
		);
	}

	public boolean tryDeleteGamer(long gamerId) {
		if (!gamerRepository.existsById(gamerId)) {
			return false;
		}

		gamerRepository.deleteById(gamerId);
		return true;
	}

	public void throwExceptionAndLogoutIfAuthenticatedGamerNotFound(@NotNull Principal principal, @NotNull HttpServletRequest request,
	                                                                @NotNull HttpServletResponse response) {
		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		if (!gamerRepository.existsById(principalId)) {
			securityHelper.logoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
	}
}
