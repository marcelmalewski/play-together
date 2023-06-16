package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.gamer.dto.GamerRegisterRequestDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

		if (gamerRepository.existsByLogin(login)) {
			//error
		}

		if (gamerRepository.existsByEmail(email)) {
			//error
		}

		Gamer admin = new Gamer();
		admin.setLogin(gamerRegisterRequestDto.login());
		admin.setPassword(passwordEncoder.encode(gamerRegisterRequestDto.password()));
		admin.setEmail(gamerRegisterRequestDto.email());
		admin.setBirthDate(gamerRegisterRequestDto.birthDate());
		admin.setPlayingTimeStart(gamerRegisterRequestDto.playingTimeStart());
		admin.setPlayingTimeEnd(gamerRegisterRequestDto.playingTimeEnd());
		admin.setPlatformEnums(gamerRegisterRequestDto.platformEnums());
//		admin.setRole(gamerRegisterRequestDto);//TODO poprawic na roles
		admin.setCreatedAt(LocalDate.now());
	}
}
