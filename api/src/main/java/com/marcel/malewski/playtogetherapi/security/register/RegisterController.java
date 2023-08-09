package com.marcel.malewski.playtogetherapi.security.register;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleEnum;
import com.marcel.malewski.playtogetherapi.security.exception.AlreadyAuthenticatedGamerException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping(value="/registration/gamers")
	@Operation(summary = "Register gamer")
	public void registerGamer(@Valid @RequestBody GamerRegisterRequestDto registerDto, Principal principal) {
		if(principal != null) {
			throw new AlreadyAuthenticatedGamerException();
		}
		this.registerService.register(registerDto, GamerRoleEnum.ROLE_USER);
	}

	//TODO może tylko rola owner może tworzyć moderatorów?
	@PostMapping(value="/moderator-panel/registration/moderators")
	@Operation(summary = "Register gamer with role moderator")
	@Secured("ROLE_MODERATOR")
	public void registerModerator(@Valid @RequestBody GamerRegisterRequestDto registerDto) {
		this.registerService.register(registerDto, GamerRoleEnum.ROLE_MODERATOR);
	}
}
