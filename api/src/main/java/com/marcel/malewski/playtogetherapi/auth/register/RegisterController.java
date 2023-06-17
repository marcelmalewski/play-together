package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.gamer.dto.GamerRegisterRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//TODO co robi to validated

@RestController
@Tag(
	name = "Registration",
	description = "Gamer registration. Login, and logout are handled by Spring Security"
)
//@Validated
public class RegisterController {
	private final RegisterService registerService;

	public RegisterController(RegisterService registerService) {
		this.registerService = registerService;
	}

	@PostMapping(value="/auth/register")
	public void register(@RequestBody GamerRegisterRequestDto gamerRegisterRequestDto) {
		//TODO dodac jezeli principal nie jest nullem to nie moze dokonac rejestracji
		this.registerService.register(gamerRegisterRequestDto);
	}
}
