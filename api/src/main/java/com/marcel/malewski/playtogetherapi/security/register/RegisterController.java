package com.marcel.malewski.playtogetherapi.security.register;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleEnum;
import com.marcel.malewski.playtogetherapi.security.exception.AlreadyAuthenticatedGamerException;
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

	@PostMapping(value="/auth/gamers/register")
	public void registerGamer(@Valid @RequestBody GamerRegisterRequestDto registerDto, Principal principal) {
		if(principal != null) {
			throw new AlreadyAuthenticatedGamerException();
		}
		this.registerService.register(registerDto,  GamerRoleEnum.ROLE_USER);
	}

	//TODO dodać jeszcze, że może to zrobić tylko zalogowany moderator, jak tu użyć role on spring security hm
	@PostMapping(value="/auth/moderators/register")
	public void registerModerator(@Valid @RequestBody GamerRegisterRequestDto registerDto, Principal principal) {
		if(principal == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		this.registerService.register(registerDto, GamerRoleEnum.ROLE_MODERATOR);
	}
}
