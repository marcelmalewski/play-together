package com.marcel.malewski.playtogetherapi.security.register;

import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.EmailAlreadyUsedException;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.LoginAlreadyUsedException;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleName;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleService;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformService;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.marcel.malewski.playtogetherapi.validation.DateTimeParser.parseToDate;
import static com.marcel.malewski.playtogetherapi.validation.DateTimeParser.parseToTime;

@Service
@Validated
public class RegisterService {
	private final GamerService gamerService;
	private final GamerRoleService gamerRoleService;
	private final PlatformService platformService;
	private final PasswordEncoder passwordEncoder;

	public RegisterService(GamerService gamerService, GamerRoleService gamerRoleService, PlatformService platformService, PasswordEncoder passwordEncoder) {
		this.gamerService = gamerService;
		this.gamerRoleService = gamerRoleService;
		this.platformService = platformService;
		this.passwordEncoder = passwordEncoder;
	}

	public Long register(@NotNull GamerRegisterRequestDto registerDto, @NotNull GamerRoleName gamerRole) {
		String login = registerDto.login();
		if (gamerService.gamerExistsByLogin(login)) {
			throw new LoginAlreadyUsedException(login);
		}

		String email = registerDto.email();
		if (gamerService.gamerExistsByEmail(email)) {
			throw new EmailAlreadyUsedException(email);
		}

		String encodedPassword = passwordEncoder.encode(registerDto.password());

		LocalDate birthdate = parseToDate(registerDto.birthdateAsString());
		LocalTime playingTimeStart = parseToTime(registerDto.playingTimeStartAsString());
		LocalTime playingTimeEnd = parseToTime(registerDto.playingTimeEndAsString());

		Gamer newGamer = Gamer.builder()
			.login(login)
			.password(encodedPassword)
			.email(email)
			.birthdate(birthdate)
			.playingTimeStart(playingTimeStart)
			.playingTimeEnd(playingTimeEnd)
			.createdAt(LocalDate.now())
			.build();

		registerDto.platformsIds().forEach(platformId -> {
			Platform platform = platformService.getReferenceOfGivenPlatform(platformId);
			newGamer.getPlatforms().add(platform);
		});

		GamerRole userRole = gamerRoleService.getGamerRoleReference(gamerRole.name());
		newGamer.getRoles().add(userRole);

		return gamerService.saveGamer(newGamer);
	}
}
