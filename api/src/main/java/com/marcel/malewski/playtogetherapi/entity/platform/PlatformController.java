package com.marcel.malewski.playtogetherapi.entity.platform;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "v1")
@Tag(name = "Platforms v1", description = "Platforms API v1")
public class PlatformController {
	private final PlatformService platformService;

	public PlatformController(PlatformService platformService) {
		this.platformService = platformService;
	}

	@GetMapping(value = "/platforms")
	@Operation(summary = "Find all platformsIds")
	public ResponseEntity<List<Platform>> findAllPlatforms() {
		List<Platform> result = platformService.findAllPlatforms();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
