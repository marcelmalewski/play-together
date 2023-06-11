package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.gamer.dto.GamerRegisterRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
	name = "Registration",
	description = "Account registration. Login, and logout are handled by Spring Security"
)
@Validated//TODO co robi to validated
public class RegisterController {
	private final RegisterService registerService;

	public RegisterController(RegisterService registerService) {
		this.registerService = registerService;
	}

	//TODO przetestowac walidacje i ewentualnie zakomponetowac @Valid zeby latwiej sie testowalo
	@GetMapping(value="/auth/register")
	public void register(@Valid @RequestBody GamerRegisterRequestDto gamerRegisterRequestDto) {
		//TODO dodac jezeli principal nie jest nullem to nie moze dokonac rejestracji
		this.registerService.register(gamerRegisterRequestDto);
	}
}
