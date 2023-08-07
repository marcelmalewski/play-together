package com.marcel.malewski.playtogetherapi.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO może jakoś lepiej nazwać ten kontroler, a może usunąć
@RestController
@Tag(
	name = "Auth"
)
public class AuthController {
	@GetMapping(value = "/accessDenied")
	@Operation(summary = "Access denied")
	public ResponseEntity<Void> findAllGamers() {
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
}
