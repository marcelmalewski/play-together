package com.marcel.malewski.playtogetherapi.person;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@Tag(name = "Persons", description = "Persons API v1")
public class PersonController {
	private final PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping(value="/users")
	@Operation(summary = "Find all gamers public info")
	public ResponseEntity<List<Person>> findAllGamers() {
		List<Person> result = this.personService.findAllUsers();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value="/users/@me")
	public ResponseEntity<String> getGamer(Principal principal) {
		if(principal != null) {
			return new ResponseEntity<>(principal.getName(), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
