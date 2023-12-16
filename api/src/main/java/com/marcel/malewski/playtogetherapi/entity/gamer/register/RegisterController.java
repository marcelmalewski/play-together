package com.marcel.malewski.playtogetherapi.entity.gamer.register;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleName;
import com.marcel.malewski.playtogetherapi.security.exception.AlreadyAuthenticatedGamerException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeName.GAMER_PRIVATE_DATA_VIEW_PRIVILEGE;
import static com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeName.MODERATOR_CREATE_PRIVILEGE;

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
	@Operation(summary = "Register gamer with role basic gamer")
	public ResponseEntity<Void> registerGamer(@Valid @RequestBody GamerRegisterRequestDto registerDto, Principal principal) {
		if(principal != null) {
			throw new AlreadyAuthenticatedGamerException();
		}

		Long registeredGamerId = registerService.register(registerDto, GamerRoleName.BASIC_GAMER_ROLE);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/gamers/" + registeredGamerId.toString());

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	//TODO endpoint not used
	@PostMapping(value="/moderator-panel/registration/moderators")
	@Operation(summary = "Register gamer with role moderator")
	@PreAuthorize("hasRole('" + MODERATOR_CREATE_PRIVILEGE + "')")
	public ResponseEntity<Void> registerModerator(@Valid @RequestBody GamerRegisterRequestDto registerDto) {
		Long registeredModeratorId = registerService.register(registerDto, GamerRoleName.MODERATOR_ROLE);
		HttpHeaders headers = new HttpHeaders();
		//TODO czy napewno takie location?
		headers.add("Location", "/api/v1/gamers/" + registeredModeratorId.toString());

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
}
