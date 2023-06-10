package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.gamer.dto.GamerPrivateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/gamers")
@Tag(name = "Gamers", description = "Gamers API v1")
public class GamerController {
	private final GamerService gamerService;

	public GamerController(GamerService gamerService) {
		this.gamerService = gamerService;
	}

	@GetMapping(value="/yes")
	@Operation(summary = "Find all gamers public info")
	public ResponseEntity<List<GamerPrivateResponseDto>> findAllGamers() {
		List<GamerPrivateResponseDto> result = this.gamerService.findAllGamers();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value="/yes2")
	public ResponseEntity<GamerPrivateResponseDto> getGamer(Long id) {
		GamerPrivateResponseDto result = this.gamerService.getGamer(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
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
