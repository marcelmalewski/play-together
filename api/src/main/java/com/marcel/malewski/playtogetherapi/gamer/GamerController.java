package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.gamer.dto.GamerPublicResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

//TODO dodac v1? po co ?
@RestController
@RequestMapping(value = "")
@Tag(name = "Gamers", description = "Gamers API")
public class GamerController {
	private final GamerService gamerService;

	public GamerController(GamerService gamerService) {
		this.gamerService = gamerService;
	}

	//TODO private zmienic na public
	@GetMapping(value = "/gamers")
	@Operation(summary = "Find all gamers public info")
	public ResponseEntity<List<GamerPublicResponseDto>> findAllGamers() {
		List<GamerPublicResponseDto> allGamers = this.gamerService.findAllGamers();
		return new ResponseEntity<>(allGamers, HttpStatus.OK);
	}

	//TODO private zmienic na public
	@GetMapping(value = "/gamers/:gamerId")
	@Operation(summary = "Get public info about a gamer by id")
	public ResponseEntity<GamerPublicResponseDto> getGamerPublic(Long id) {
		GamerPublicResponseDto gamerPublic = this.gamerService.getGamerPublic(id);
		return new ResponseEntity<>(gamerPublic, HttpStatus.OK);
	}

	@GetMapping(value = "/gamers/@me")
	@Operation(summary = "Get private info about an authenticated gamer")
	public ResponseEntity<String> getGamer(Principal principal) {
		if (principal != null) {
			String personLogin = principal.getName();
			System.out.println(personLogin);
			System.out.println(principal);

			return new ResponseEntity<>("person", HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
