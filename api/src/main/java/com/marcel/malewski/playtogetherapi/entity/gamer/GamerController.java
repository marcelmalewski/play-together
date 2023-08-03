package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.auth.exception.AuthenticatedGamerNotFoundException;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateAuthRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.marcel.malewski.playtogetherapi.utils.AuthUtils.LogoutManually;

//TODO czy dodać v1?
@RestController
@RequestMapping(value = "")
@Tag(name = "Gamers", description = "Gamers API")
public class GamerController {
	private final GamerService gamerService;

	public GamerController(GamerService gamerService) {
		this.gamerService = gamerService;
	}

	@GetMapping(value = "/gamers")
	@Operation(summary = "Find all gamers public info")
	public ResponseEntity<List<GamerPublicResponseDto>> findAllGamers() {
		List<GamerPublicResponseDto> allGamers = this.gamerService.findAllGamers();
		return new ResponseEntity<>(allGamers, HttpStatus.OK);
	}

	@GetMapping(value = "/gamers/:gamerId")
	@Operation(summary = "Get public info about a gamer by id")
	public ResponseEntity<GamerPublicResponseDto> getGamerPublic(long gamerId) {
		GamerPublicResponseDto gamerPublic = this.gamerService.getGamerPublicInfo(gamerId);
		return new ResponseEntity<>(gamerPublic, HttpStatus.OK);
	}

	//TODO może dać ten endpoint dostępny tylko dla zalogowanych w SecurityConfiguration i usunąć sprawdzanie principal i zobaczyć czy frontend dobrze na to zareaguje
	@GetMapping(value = "/gamers/@me")
	@Operation(summary = "Get private info about the authenticated gamer")
	public ResponseEntity<GamerPrivateResponseDto> getGamer(Principal principal, HttpServletRequest request,
	                                                        HttpServletResponse response) {
		if (principal == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		String gamerIdAsString = principal.getName();
		long gamerId = Long.parseLong(gamerIdAsString);

		try {
			GamerPrivateResponseDto gamerPrivateInfo = this.gamerService.getGamerPrivateInfo(gamerId);
			return new ResponseEntity<>(gamerPrivateInfo, HttpStatus.OK);
		} catch (GamerNotFoundException exception) {
			LogoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
	}

	@PutMapping(value = "/gamers/@me/profile")
	@Operation(summary = "Update the authenticated gamers's public profile data")
	public ResponseEntity<GamerPrivateResponseDto> updateGamerProfile(@Valid @RequestBody GamerUpdateProfileRequestDto updateProfileDto, Principal principal, HttpServletRequest request,
	                                                                  HttpServletResponse response) {
		String gamerIdAsString = principal.getName();
		long gamerId = Long.parseLong(gamerIdAsString);

		try {
			GamerPrivateResponseDto updatedGamer = this.gamerService.updateGamerProfile(updateProfileDto, gamerId);
			return new ResponseEntity<>(updatedGamer, HttpStatus.OK);
		} catch (GamerNotFoundException exception) {
			LogoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
	}

	@PatchMapping(value = "/gamers/@me/private")
	@Operation(summary = "Update the authenticated gamers's private data")
	public ResponseEntity<GamerPrivateResponseDto> updateGamerPrivateData(@Valid @RequestBody GamerUpdateAuthRequestDto updateAuthDto, Principal principal, HttpServletRequest request,
	                                                                      HttpServletResponse response) {
		String gamerIdAsString = principal.getName();
		long gamerId = Long.parseLong(gamerIdAsString);

		try {
			GamerPrivateResponseDto updatedGamer = this.gamerService.updateGamerAuth(updateAuthDto, gamerId);
			return new ResponseEntity<>(updatedGamer, HttpStatus.OK);
		} catch (GamerNotFoundException exception) {
			LogoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
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

//	@DeleteMapping("/{id}")
//	@Operation(summary = "Delete gamer by id")
//	public ResponseEntity<Void> deleteGamer(@PathVariable Long id) {
//		gamerService.deleteGamer(id);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
}
