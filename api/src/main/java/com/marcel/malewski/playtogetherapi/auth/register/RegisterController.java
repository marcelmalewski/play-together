package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.auth.exception.AlreadyAuthenticatedException;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

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

	//TODO temporary @Valid deleted
	@PostMapping(value="/auth/gamers/register")
	public void registerUser(@Valid @RequestBody GamerRegisterRequestDto gamerRegisterRequestDto, Principal principal) {
		if(principal != null) {
			throw new AlreadyAuthenticatedException();
		}
		String gamerRole = GamerRoleEnum.USER.name();
		this.registerService.register(gamerRegisterRequestDto, gamerRole);
	}

	//TODO dodać jeszcze, że może to zrobić tylko zalogowany moderator
	@PostMapping(value="/auth/moderators/register")
	public void registerModerator(@Valid @RequestBody GamerRegisterRequestDto gamerRegisterRequestDto, Principal principal) {
		if(principal == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		String gamerRole = GamerRoleEnum.USER.name();
		this.registerService.register(gamerRegisterRequestDto, gamerRole);
	}
}
