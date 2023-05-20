package com.marcel.malewski.playtogetherapi.gamer;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/gamers")
@Tag(name = "Sessions", description = "Sessions API")
public class GamerController {
	private final GamerService gamerService;

	public GamerController(GamerService gamerService) {
		this.gamerService = gamerService;
	}

	@GetMapping
	public String findAllGamers() {
		return this.gamerService.findAllGamers().toString();
	}

//	@GetMapping("/{id}")
//	@Operation(summary = "Get gamer by id")
//	public ResponseEntity<GamerResponseDto> getGamer(@PathVariable Long id) {
//		GamerResponseDto result = gamerService.getGamer(id);
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}

//	@PostMapping
//	@Operation(summary = "Create new gamer")
//	public ResponseEntity<GamerResponseDto> createGamer() {
//		GamerResponseDto result = gamerService.createGamer();
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
//
//	@PutMapping
//	@Operation(summary = "Update gamer by id")
//	public ResponseEntity<GamerResponseDto> updateGamer() {
//		GamerResponseDto result = gamerService.updateGamer();
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}

//   @PatchMapping
//   @Operation(summary = "Partially update gamer")
//   public ResponseEntity<String> partiallyUpdateGamer() {
//      String result = "gamer2";
//      return new ResponseEntity<>(result, HttpStatus.OK);
//   }

//	@DeleteMapping("/{id}")
//	@Operation(summary = "Delete gamer by id")
//	public ResponseEntity<Void> deleteGamer(@PathVariable Long id) {
//		gamerService.deleteGamer(id);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
}
