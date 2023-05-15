package com.marcel.malewski.playtogetherapi.gamer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/gamers")
public class GamerController {

	@GetMapping
	public ResponseEntity<String> getGamer() {
		return ResponseEntity.ok("Hello World");
	}

	@GetMapping("/secured")
	public ResponseEntity<String> getTest() {
		return ResponseEntity.ok("Hello World test");
	}
}
