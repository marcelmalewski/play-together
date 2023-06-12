package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.gamer.dto.GamerRegisterRequestDto;
import com.marcel.malewski.playtogetherapi.shared.GamerRole;
import com.marcel.malewski.playtogetherapi.shared.Platform;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RegisterService {
	private final GamerRepository gamerRepository;
	private final PasswordEncoder passwordEncoder;

	public RegisterService(GamerRepository gamerRepository, PasswordEncoder passwordEncoder) {
		this.gamerRepository = gamerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	void register(GamerRegisterRequestDto gamerRegisterRequestDto) {
		String login = gamerRegisterRequestDto.login();
		String email = gamerRegisterRequestDto.email();
		String encodedPassword = passwordEncoder.encode(gamerRegisterRequestDto.password());

		if(gamerRepository.existsByLogin(login)){

		}

		if(gamerRepository.existsByEmail(email)){

		}

		Gamer admin = new Gamer();
		admin.setLogin(gamerRegisterRequestDto.login());
		admin.setPassword(passwordEncoder.encode(gamerRegisterRequestDto.password()));
		admin.setEmail(gamerRegisterRequestDto.email());
		admin.setBirthDate(gamerRegisterRequestDto.birthDate());
		admin.setPlayingTimeStart(gamerRegisterRequestDto.playingTimeStart());
		admin.setPlayingTimeEnd(gamerRegisterRequestDto.playingTimeEnd());

		admin.setPlatforms(List.of(Platform.PC));
		admin.setRole(GamerRole.MODERATOR);
		admin.setCreatedAt(LocalDate.now());
	}

}
