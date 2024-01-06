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
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.NewPasswordSameAsPrevious;
import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.GameSessionSortOption;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.marcel.malewski.playtogetherapi.constant.PageableConstants.*;
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

	public static final GamerSortOption DEFAULT_GAMER_SORT_OPTION = GamerSortOption.LOGIN_ASC;

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

	public Page<GamerPublicResponseDto> findAllGamersPublicInfo(@Min(MIN_PAGE_NUMBER) Integer optionalPageNumber,
																															@Min(MIN_PAGE_SIZE) @Max(MAX_PAGE_SIZE) Integer optionalPageSize,
																															GamerSortOption optionalSort, String optionalGamerLogin
	) {
		Pageable pageable = createPageable(optionalPageNumber, optionalPageSize, optionalSort);
		Page<Gamer> gamerPage;

		if (StringUtils.hasText(optionalGamerLogin)) {
			gamerPage = listGamersByLogin(optionalGamerLogin, pageable);
		} else {
			gamerPage = gamerRepository.findAll(pageable);
		}

		return gamerPage.map(gamerMapper::toGamerPublicResponseDto);
	}

	private Pageable createPageable(Integer optionalPageNumber, Integer optionalPageSize, GamerSortOption optionalSort) {
		int pageNumber = optionalPageNumber != null ? optionalPageNumber : DEFAULT_PAGE_NUMBER;
		int pageSize = optionalPageSize != null ? optionalPageSize : DEFAULT_PAGE_SIZE;
		GamerSortOption sort = optionalSort != null ? optionalSort : DEFAULT_GAMER_SORT_OPTION;

		return PageRequest.of(pageNumber, pageSize, sort.getSort());
	}

	private Page<Gamer> listGamersByLogin(String gamerLogin, Pageable pageable) {
		return gamerRepository.findAllByLoginIsLikeIgnoreCase("%" + gamerLogin + "%", pageable);
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
		if (!gamerRepository.existsById(gamerId)) {
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

	public Gamer saveGamer(@NotNull Gamer gamer) {
		return gamerRepository.save(gamer);
	}

	public Optional<GamerPrivateResponseDto> tryUpdateGamerProfile(@Valid GamerUpdateProfileRequestDto updateProfileDto, long gamerId) {
		Optional<Gamer> optionalGamer = gamerRepository.findById(gamerId);
		if (optionalGamer.isEmpty()) {
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

	public Optional<GamerPrivateResponseDto> tryUpdatePartiallyGamerAuthenticationData(@Valid GamerUpdateAuthenticationDataRequestDto updateAuthDto, long gamerId) {
		Optional<Gamer> optionalGamer = gamerRepository.findById(gamerId);
		if (optionalGamer.isEmpty()) {
			return Optional.ofNullable(gamerMapper.toGamerPrivateResponseDto(null));
		}
		Gamer gamer = optionalGamer.get();

		if (!passwordEncoder.matches(updateAuthDto.currentPassword(), gamer.getPassword())) {
			throw new InvalidPasswordException();
		}

		if (updateAuthDto.newPassword() != null) {
			throwExceptionIfNewPasswordIsSameAsPreviousOne(updateAuthDto.newPassword(), gamer.getPassword(), passwordEncoder);

			String newPasswordEncoded = passwordEncoder.encode(updateAuthDto.newPassword());
			gamer.setPassword(newPasswordEncoded);
		}

		if (updateAuthDto.email() != null) {
			String newEmail = updateAuthDto.email();
			if (!gamer.getEmail().equals(updateAuthDto.email()) && gamerRepository.existsByEmail(newEmail)) {
				throw new EmailAlreadyUsedException(newEmail);
			}

			gamer.setEmail(newEmail);
		}

		Gamer updatedGamer = gamerRepository.save(gamer);
		return Optional.ofNullable(
			gamerMapper.toGamerPrivateResponseDto(updatedGamer)
		);
	}

	private void throwExceptionIfNewPasswordIsSameAsPreviousOne(String newPassword, String previousPassword, BCryptPasswordEncoder passwordEncoder) {
		if (passwordEncoder.matches(newPassword, previousPassword)) {
			throw new NewPasswordSameAsPrevious();
		}
	}

	public void deleteGamers(long gamerId) {
		//TODO
	}

	;

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
