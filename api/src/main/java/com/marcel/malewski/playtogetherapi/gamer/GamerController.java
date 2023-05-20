package com.marcel.malewski.playtogetherapi.gamer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/gamers")
public class GamerController {
	private final GamerService gamerService;

	public GamerController(GamerService gamerService) {
		this.gamerService = gamerService;
	}

	@RequestMapping
	public String getGamers() {
		return this.gamerService.findAllGamers().toString();
	}
}
