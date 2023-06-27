package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.gamer.dto.GamerRegisterRequestDto;
import com.marcel.malewski.playtogetherapi.gamerrole.GamerRoleEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


//TODO ewentualnie pojsc w auth controller
@RestController
@Tag(
	name = "Registration",
	description = "Gamer registration. Login, and logout are handled by Spring Security"
)
public class RegisterController {
	private final RegisterService registerService;

	public RegisterController(RegisterService registerService) {
		this.registerService = registerService;
	}

	@PostMapping(value="/auth/gamers/register")
	public void registerUser(@Valid @RequestBody GamerRegisterRequestDto gamerRegisterRequestDto) {
		//TODO dodac jezeli principal nie jest nullem to nie moze dokonac rejestracji
		String gamerRole = GamerRoleEnum.USER.name();
		this.registerService.register(gamerRegisterRequestDto, gamerRole);
	}

	//TODO dostep ma tylko inny moderator
	@PostMapping(value="/auth/moderators/register")
	public void registerModerator(@Valid @RequestBody GamerRegisterRequestDto gamerRegisterRequestDto) {
		//TODO dodac jezeli principal nie jest nullem to nie moze dokonac rejestracji
		String gamerRole = GamerRoleEnum.USER.name();
		this.registerService.register(gamerRegisterRequestDto, gamerRole);
	}
}
