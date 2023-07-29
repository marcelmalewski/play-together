package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.auth.exception.EmailAlreadyUsedException;
import com.marcel.malewski.playtogetherapi.auth.exception.GivenPlatformDoesNotExistException;
import com.marcel.malewski.playtogetherapi.auth.exception.LoginAlreadyUsedException;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleEnum;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static com.marcel.malewski.playtogetherapi.constants.DateUtils.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.constants.DateUtils.TIME_FORMAT;

//TODO repository zmienic na serwisy?
@Service
public class RegisterService {
	private final GamerRepository gamerRepository;
	private final GamerRoleRepository gamerRoleRepository;
	private final PlatformRepository platformRepository;
	private final PasswordEncoder passwordEncoder;

	public RegisterService(GamerRepository gamerRepository, GamerRoleRepository gamerRoleRepository, PlatformRepository platformRepository, PasswordEncoder passwordEncoder) {
		this.gamerRepository = gamerRepository;
		this.gamerRoleRepository = gamerRoleRepository;
		this.platformRepository = platformRepository;
		this.passwordEncoder = passwordEncoder;
	}

	void register(GamerRegisterRequestDto gamerRegisterRequestDto, GamerRoleEnum gamerRole) {
		String login = gamerRegisterRequestDto.login();
		if (gamerRepository.existsByLogin(login)) {
			throw new LoginAlreadyUsedException(login);
		}

		String email = gamerRegisterRequestDto.email();
		if (gamerRepository.existsByEmail(email)) {
			throw new EmailAlreadyUsedException(email);
		}

		gamerRegisterRequestDto.platformsIds().forEach(platformId -> {
			if (!platformRepository.existsById(platformId)) {
				throw new GivenPlatformDoesNotExistException(platformId);
			}
		});

		String encodedPassword = passwordEncoder.encode(gamerRegisterRequestDto.password());

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);
		LocalDate birthdateAsDate = LocalDate.parse(gamerRegisterRequestDto.birthdate(), dateFormatter);

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT).withResolverStyle(ResolverStyle.STRICT);
		LocalTime playingTimeStartAsDate = LocalTime.parse(gamerRegisterRequestDto.playingTimeStart(), timeFormatter);
		LocalTime playingTimeEndAsDate = LocalTime.parse(gamerRegisterRequestDto.playingTimeEnd(), timeFormatter);

		Gamer newGamer = new Gamer();
		newGamer.setLogin(login);
		newGamer.setPassword(encodedPassword);
		newGamer.setEmail(email);
		newGamer.setBirthdate(birthdateAsDate);
		newGamer.setPlayingTimeStart(playingTimeStartAsDate);
		newGamer.setPlayingTimeEnd(playingTimeEndAsDate);
		newGamer.setCreatedAt(LocalDate.now());
		Gamer savedGamer = gamerRepository.save(newGamer);

		gamerRegisterRequestDto.platformsIds().forEach(platformId -> {
			Platform platform = platformRepository.getReferenceById(platformId);

			savedGamer.getPlatforms().add(platform);
			platform.getGamers().add(savedGamer);
			platformRepository.save(platform);
		});

		GamerRole userGamerRole = gamerRoleRepository.getReferenceByName(gamerRole.name());
		savedGamer.getRoles().add(userGamerRole);
		userGamerRole.getGamers().add(savedGamer);
		gamerRoleRepository.save(userGamerRole);

		gamerRepository.save(newGamer);
	}
}
