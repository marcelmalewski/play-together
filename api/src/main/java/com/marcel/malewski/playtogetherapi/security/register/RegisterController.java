package com.marcel.malewski.playtogetherapi.security.register;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleName;
import com.marcel.malewski.playtogetherapi.security.exception.AlreadyAuthenticatedGamerException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "v1")
@Tag(
	name = "Registration v1",
	description = "Gamer registration v1. Login, and logout are handled by Spring SecurityHelper"
)
public class RegisterController {
	private final RegisterService registerService;

	public RegisterController(RegisterService registerService) {
		this.registerService = registerService;
	}

	@PostMapping(value="/registration/gamers")
	@Operation(summary = "Register gamer")
	public ResponseEntity<Void> registerGamer(@Valid @RequestBody GamerRegisterRequestDto registerDto, Principal principal) {
		if(principal != null) {
			throw new AlreadyAuthenticatedGamerException();
		}

		registerService.register(registerDto, GamerRoleName.ROLE_USER);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	//TODO moderatora tworzy inny moderator albo owner, owner może usunąć moderatora
	@PostMapping(value="/moderator-panel/registration/moderators")
	@Operation(summary = "Register gamer with role moderator")
	@Secured("ROLE_MODERATOR")
	public ResponseEntity<Void> registerModerator(@Valid @RequestBody GamerRegisterRequestDto registerDto) {
		registerService.register(registerDto, GamerRoleName.ROLE_MODERATOR);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
