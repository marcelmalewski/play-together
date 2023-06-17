package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.gamer.dto.GamerRegisterRequestDto;
import com.marcel.malewski.playtogetherapi.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.gamerrole.GamerRoleEnum;
import com.marcel.malewski.playtogetherapi.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.platform.PlatformRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

	void register(GamerRegisterRequestDto gamerRegisterRequestDto) {
		String login = gamerRegisterRequestDto.login();
		String email = gamerRegisterRequestDto.email();
		String encodedPassword = passwordEncoder.encode(gamerRegisterRequestDto.password());

		if (gamerRepository.existsByLogin(login)) {
			//error
		}

		if (gamerRepository.existsByEmail(email)) {
			//error
		}

		//TODO pobieranie roli
		GamerRole userGamerRole = gamerRoleRepository.getReferenceByName(
			GamerRoleEnum.USER.name()
		);

		//TODO czy platforma istnieje
//		Platform pcPlatform = platformRepository.getReferenceById(
//			gamerRegisterRequestDto.platforms().get(0)
//		);


		Gamer newGamer = new Gamer();
		newGamer.setLogin(gamerRegisterRequestDto.login());
		newGamer.setPassword(encodedPassword);
		newGamer.setEmail(gamerRegisterRequestDto.email());
		newGamer.setBirthDate(gamerRegisterRequestDto.birthDate());
		newGamer.setPlayingTimeStart(gamerRegisterRequestDto.playingTimeStart());
		newGamer.setPlayingTimeEnd(gamerRegisterRequestDto.playingTimeEnd());
		newGamer.setCreatedAt(LocalDate.now());

		System.out.println(userGamerRole);

//		Gamer savedGamer = gamerRepository.save(newGamer);
//		savedGamer.getRoles().add(userGamerRole);
//		userGamerRole.getGamers().add(savedGamer);
//		savedGamer.getPlatforms().add(pcPlatform);
//		pcPlatform.getGamers().add(savedGamer);
	}
}
