package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.auth.exception.EmailAlreadyUsedException;
import com.marcel.malewski.playtogetherapi.auth.exception.LoginAlreadyUsedException;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleEnum;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.exception.GivenPlatformDoesNotExistException;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static com.marcel.malewski.playtogetherapi.utils.DateUtils.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.utils.DateUtils.TIME_FORMAT;

//TODO use service instead of repository
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

	void register(@NotNull GamerRegisterRequestDto registerDto, @NotNull GamerRoleEnum gamerRole) {
		String login = registerDto.login();
		if (gamerRepository.existsByLogin(login)) {
			throw new LoginAlreadyUsedException(login);
		}

		String email = registerDto.email();
		if (gamerRepository.existsByEmail(email)) {
			throw new EmailAlreadyUsedException(email);
		}

		registerDto.platformsIds().forEach(platformId -> {
			if (!platformRepository.existsById(platformId)) {
				throw new GivenPlatformDoesNotExistException(platformId);
			}
		});

		String encodedPassword = passwordEncoder.encode(registerDto.password());

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);
		LocalDate birthdateAsDate = LocalDate.parse(registerDto.birthdate(), dateFormatter);

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT).withResolverStyle(ResolverStyle.STRICT);
		LocalTime playingTimeStartAsDate = LocalTime.parse(registerDto.playingTimeStart(), timeFormatter);
		LocalTime playingTimeEndAsDate = LocalTime.parse(registerDto.playingTimeEnd(), timeFormatter);

		Gamer newGamer = new Gamer();
		newGamer.setLogin(login);
		newGamer.setPassword(encodedPassword);
		newGamer.setEmail(email);
		newGamer.setBirthdate(birthdateAsDate);
		newGamer.setPlayingTimeStart(playingTimeStartAsDate);
		newGamer.setPlayingTimeEnd(playingTimeEndAsDate);
		newGamer.setCreatedAt(LocalDate.now());
		Gamer savedGamer = gamerRepository.save(newGamer);

		registerDto.platformsIds().forEach(platformId -> {
			Platform platform = platformRepository.getReferenceById(platformId);
			savedGamer.getPlatforms().add(platform);
		});

		GamerRole userRole = gamerRoleRepository.getReferenceByName(gamerRole.name());
		savedGamer.getRoles().add(userRole);

		gamerRepository.save(newGamer);
	}
}
