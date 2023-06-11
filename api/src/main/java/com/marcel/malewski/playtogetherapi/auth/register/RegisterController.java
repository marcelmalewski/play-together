package com.marcel.malewski.playtogetherapi.auth.register;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
	name = "Registration",
	description = "Account registration. Login, and logout are handled by Spring Security"
)
public class RegisterController {
	private final RegisterService registerService;

	public RegisterController(RegisterService registerService) {
		this.registerService = registerService;
	}

	@GetMapping(value="/auth/register")
	@Operation(summary = "Find all gamers public info")
	public void findAllGamers() {
		this.registerService.registerPerson();
	}
}
