package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//TODO czy w linku powinno być coś ala "moderator" hm? po co?
@RestController
@Tag(name = "Moderator Gamers", description = "Gamers API for Moderators")
public class ModeratorGamerController {
	private final GamerService gamerService;

	public ModeratorGamerController(GamerService gamerService) {
		this.gamerService = gamerService;
	}

	@GetMapping("/moderator/gamers")
	@Operation(summary = "Find all gamers private info")
	@Secured("ROLE_MODERATOR")
	public ResponseEntity<List<GamerPrivateResponseDto>> findAllGamers() {
		List<GamerPrivateResponseDto> allGamers = this.gamerService.findAllGamersPrivateInfo();
		return new ResponseEntity<>(allGamers, HttpStatus.OK);
	}

//	@DeleteMapping("/moderator/users/{id}")
//	@Operation(summary = "Force-delete a user by id")
//	@Secured("ROLE_MODERATOR")
//	fun deleteUser(@PathVariable id: UUID) {
//		userService.deleteUserUnconditionally(id)
//	}

	@DeleteMapping("/gamers/:gamerId")
	@Operation(summary = "Delete gamer by id")
	@Secured("ROLE_MODERATOR")
	public ResponseEntity<Void> deleteGamer(long gamerId) {
		gamerService.deleteGamer(gamerId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
