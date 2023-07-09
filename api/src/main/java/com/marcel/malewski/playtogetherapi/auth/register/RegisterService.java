package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.gamer.dto.GamerRegisterRequestDto;
import com.marcel.malewski.playtogetherapi.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.platform.PlatformRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
//		if (gamerRepository.existsByLogin(login)) {
//			//error
//		}
//
//		String email = gamerRegisterRequestDto.email();
//		if (gamerRepository.existsByEmail(email)) {
//			//error
//		}
//
//		//TODO czy platforma istnieje, dodac obsluge dla paru platform
//		Platform pcPlatform = platformRepository.getReferenceById(
//			gamerRegisterRequestDto.platforms().get(0)
//		);
//
//		String encodedPassword = passwordEncoder.encode(gamerRegisterRequestDto.password());
//		GamerRole userGamerRole = gamerRoleRepository.getReferenceByName(
//			gamerRole
//		);
//
//		Gamer newGamer = new Gamer();
//		newGamer.setLogin(gamerRegisterRequestDto.login());
//		newGamer.setPassword(encodedPassword);
//		newGamer.setEmail(gamerRegisterRequestDto.email());
//		newGamer.setBirthDate(gamerRegisterRequestDto.birthDate());
//		newGamer.setPlayingTimeStart(gamerRegisterRequestDto.playingTimeStart());
//		newGamer.setPlayingTimeEnd(gamerRegisterRequestDto.playingTimeEnd());
//		newGamer.setCreatedAt(LocalDate.now());
//
//		Gamer savedGamer = gamerRepository.save(newGamer);
//		savedGamer.getRoles().add(userGamerRole);
//		userGamerRole.getGamers().add(savedGamer);
//		savedGamer.getPlatforms().add(pcPlatform);
//		pcPlatform.getGamers().add(savedGamer);
//
//		gamerRepository.save(savedGamer);
//		gamerRoleRepository.save(userGamerRole);
//		platformRepository.save(pcPlatform);
	}
}
