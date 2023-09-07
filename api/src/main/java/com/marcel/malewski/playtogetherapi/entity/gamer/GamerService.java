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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;

import static com.marcel.malewski.playtogetherapi.constant.DateConstants.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.constant.DateConstants.TIME_FORMAT;

@Service
@Validated
public class GamerService {
	private final GamerRepository gamerRepository;
	private final PlatformService platformService;
	private final GameService gameService;
	private final GenreService genreService;
	private final GamerMapper gamerMapper;
	private final PasswordEncoder passwordEncoder;
	private final SecurityHelper securityHelper;
	private final PrincipalExtractor principalExtractor;


	public GamerService(GamerRepository gamerRepository, PlatformService platformService, GenreService genreService, GamerMapper gamerMapper, GameService gameService, PasswordEncoder passwordEncoder, SecurityHelper securityHelper, PrincipalExtractor principalExtractor) {
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

	public boolean gamerExistsByLogin(@NotNull String login) {
		return gamerRepository.existsByLogin(login);
	}

	public boolean gamerExistsByEmail(@NotNull String email) {
		return gamerRepository.existsByEmail(email);
	}

	public void saveGamer(@NotNull Gamer gamer) {
		gamerRepository.save(gamer);
	}

	public GamerPrivateResponseDto updateGamerProfile(@NotNull GamerUpdateProfileRequestDto updateProfileDto, long gamerId) {
		Gamer gamer = gamerRepository.findById(gamerId).orElseThrow(() -> new GamerNotFoundException(gamerId));

		String newLogin = updateProfileDto.login();
		if (!gamer.getLogin().equals(newLogin) && gamerRepository.existsByLogin(newLogin)) {
			throw new LoginAlreadyUsedException(newLogin);
		}

		//TODO duplicate
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);
		LocalDate birthdate = LocalDate.parse(updateProfileDto.birthdateAsString(), dateFormatter);

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT).withResolverStyle(ResolverStyle.STRICT);
		LocalTime playingTimeStart = LocalTime.parse(updateProfileDto.playingTimeStartAsString(), timeFormatter);
		LocalTime playingTimeEnd = LocalTime.parse(updateProfileDto.playingTimeEndAsString(), timeFormatter);

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

	public void throwExceptionAndLogoutIfAuthenticatedGamerNotFound(@NotNull Principal principal, @NotNull HttpServletRequest request,
	                                                                @NotNull HttpServletResponse response) {
		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		if (!gamerRepository.existsById(principalId)) {
			securityHelper.LogoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
	}
}
