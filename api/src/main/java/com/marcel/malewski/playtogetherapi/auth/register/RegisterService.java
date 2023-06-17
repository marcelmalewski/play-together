package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.gamer.dto.GamerRegisterRequestDto;
import com.marcel.malewski.playtogetherapi.gamerrole.GamerRoleService;
import com.marcel.malewski.playtogetherapi.platform.Platform;
import com.marcel.malewski.playtogetherapi.platform.PlatformRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RegisterService {
	private final GamerRepository gamerRepository;
	private final GamerRoleService gamerRoleService;
	private final PlatformRepository platformRepository;
	private final PasswordEncoder passwordEncoder;

	public RegisterService(GamerRepository gamerRepository, GamerRoleService gamerRoleService, PlatformRepository platformRepository, PasswordEncoder passwordEncoder) {
		this.gamerRepository = gamerRepository;
		this.gamerRoleService = gamerRoleService;
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

		//TODO czy platforma istnieje
		Platform platform = platformRepository.getReferenceById(
			gamerRegisterRequestDto.platforms().get(0)
		);


		Gamer newGamer = new Gamer();
		newGamer.setLogin(gamerRegisterRequestDto.login());
		newGamer.setPassword(passwordEncoder.encode(gamerRegisterRequestDto.password()));
		newGamer.setEmail(gamerRegisterRequestDto.email());
		newGamer.setBirthDate(gamerRegisterRequestDto.birthDate());
		newGamer.setPlayingTimeStart(gamerRegisterRequestDto.playingTimeStart());
		newGamer.setPlayingTimeEnd(gamerRegisterRequestDto.playingTimeEnd());
		newGamer.setCreatedAt(LocalDate.now());

		Gamer savedGamer = gamerRepository.save(newGamer);
		savedGamer.getPlatforms().add(platform);
		platform.getGamers().add(savedGamer);

//		newGamer.setPlatformEnums(gamerRegisterRequestDto.platformEnums());
//		newGamer.setRole(gamerRegisterRequestDto);
	}
}
