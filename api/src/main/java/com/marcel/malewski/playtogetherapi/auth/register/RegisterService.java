package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static com.marcel.malewski.playtogetherapi.consts.DateUtils.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.consts.DateUtils.TIME_FORMAT;

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

	void register(GamerRegisterRequestDto gamerRegisterRequestDto, String gamerRole) {
		String login = gamerRegisterRequestDto.login();
		//TODO dokonczyc walidacje
//		if (gamerRepository.existsByLogin(login)) {
//			//error
//		}
//
//		String email = gamerRegisterRequestDto.email();
//		if (gamerRepository.existsByEmail(email)) {
//			//error
//		}
//
		//TODO czy platforma istnieje, dodac obsluge dla paru platform
		Platform gamerPlatform = platformRepository.getReferenceById(
			gamerRegisterRequestDto.platforms().get(0)
		);

//    // TODO dodac sprawdzanie czy rola istnieje
		//TODO dodac dodwanie roli
//		GamerRole userGamerRole = gamerRoleRepository.getReferenceByName(
//			gamerRole
//		);

		String encodedPassword = passwordEncoder.encode(gamerRegisterRequestDto.password());
//co sie sanie jak dam ujemna dae
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);
		LocalDate birthdateAsDate = LocalDate.parse(gamerRegisterRequestDto.birthdate(), dateFormatter);

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT).withResolverStyle(ResolverStyle.STRICT);
		LocalTime playingTimeStartAsDate = LocalTime.parse(gamerRegisterRequestDto.playingTimeStart(), timeFormatter);
		LocalTime playingTimeEndAsDate = LocalTime.parse(gamerRegisterRequestDto.playingTimeEnd(), timeFormatter);

		Gamer newGamer = new Gamer();
		newGamer.setLogin(gamerRegisterRequestDto.login());
		newGamer.setPassword(encodedPassword);
		newGamer.setEmail(gamerRegisterRequestDto.email());
		newGamer.setBirthdate(birthdateAsDate);
		newGamer.setPlayingTimeStart(playingTimeStartAsDate);
		newGamer.setPlayingTimeEnd(playingTimeEndAsDate);
		newGamer.setCreatedAt(LocalDate.now());

		Gamer savedGamer = gamerRepository.save(newGamer);
		savedGamer.getPlatforms().add(gamerPlatform);
		gamerRepository.save(savedGamer);
//		savedGamer.getRoles().add(userGamerRole);
//		userGamerRole.getGamers().add(savedGamer);
//		gamerRoleRepository.save(userGamerRole);
//		pcPlatform.getGamers().add(savedGamer);
	}
}
