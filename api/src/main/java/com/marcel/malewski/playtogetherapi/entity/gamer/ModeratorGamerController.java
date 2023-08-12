package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "v1")
@Tag(name = "Moderator Gamers v1", description = "Gamers API v1 for Moderators")
public class ModeratorGamerController {
	private final GamerService gamerService;

	public ModeratorGamerController(GamerService gamerService) {
		this.gamerService = gamerService;
	}

	//TODO jakiś filtr, żeby pobrać tylko moderatorów
	@GetMapping("/moderator-panel/gamers")
	@Operation(summary = "Find all gamers private info")
	@Secured("ROLE_MODERATOR")
	public ResponseEntity<List<GamerPrivateResponseDto>> findAllGamers() {
		List<GamerPrivateResponseDto> allGamers = this.gamerService.findAllGamersPrivateInfo();
		return new ResponseEntity<>(allGamers, HttpStatus.OK);
	}

	//TODO moderator nie może usunąć moderatora? moderatora może usunąć owner?
	@DeleteMapping("/moderator-panel/gamers/:gamerId")
	@Operation(summary = "Delete gamer by id")
	@Secured("ROLE_MODERATOR")
	public ResponseEntity<Void> deleteGamer(long gamerId) {
		this.gamerService.deleteGamer(gamerId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/moderator-panel/moderators/:moderatorId")
	@Operation(summary = "Delete gamer with role moderator by id")
	@Secured("ROLE_OWNER")
	public ResponseEntity<Void> deleteModerator(long moderatorId) {
		this.gamerService.deleteGamer(moderatorId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
