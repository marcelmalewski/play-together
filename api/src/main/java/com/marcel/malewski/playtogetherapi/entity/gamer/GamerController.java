package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateAuthenticationDataRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
import com.marcel.malewski.playtogetherapi.security.exception.AuthenticatedGamerNotFoundException;
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
@Tag(name = "Gamers", description = "Gamers API")
public class GamerController {
	private final GamerService gamerService;

	public GamerController(GamerService gamerService) {
		this.gamerService = gamerService;
	}

	@GetMapping(value = "/gamers")
	@Operation(summary = "Find all gamers public info")
	public ResponseEntity<List<GamerPublicResponseDto>> findAllGamers() {
		List<GamerPublicResponseDto> allGamers = this.gamerService.findAllGamersPublicInfo();
		return new ResponseEntity<>(allGamers, HttpStatus.OK);
	}

	@GetMapping(value = "/gamers/:gamerId")
	@Operation(summary = "Get public info about a gamer by id")
	public ResponseEntity<GamerPublicResponseDto> getGamer(long gamerId) {
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

	@PutMapping(value = "/gamers/@me/profile-data")
	@Operation(summary = "Update the authenticated gamers's profile data")
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

	@PatchMapping(value = "/gamers/@me/authentication-data")
	@Operation(summary = "Update the authenticated gamers's authentication data")
	public ResponseEntity<GamerPrivateResponseDto> updateGamerAuthenticationData(@Valid @RequestBody GamerUpdateAuthenticationDataRequestDto updateAuthDto, Principal principal, HttpServletRequest request,
	                                                                             HttpServletResponse response) {
		String gamerIdAsString = principal.getName();
		long gamerId = Long.parseLong(gamerIdAsString);

		try {
			GamerPrivateResponseDto updatedGamer = this.gamerService.updateGamerAuthenticationData(updateAuthDto, gamerId);
			return new ResponseEntity<>(updatedGamer, HttpStatus.OK);
		} catch (GamerNotFoundException exception) {
			LogoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
	}

	//TODO dodać, że admin nie może tak po prostu usuną swojego konta
	@DeleteMapping("/gamers/@me")
	@Operation(summary = "Delete the authenticated gamer and log out")
	public ResponseEntity<Void> deleteGamer(Principal principal, HttpServletRequest request,
	                                        HttpServletResponse response) {
		String gamerIdAsString = principal.getName();
		long gamerId = Long.parseLong(gamerIdAsString);

		try {
			gamerService.deleteGamer(gamerId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (GamerNotFoundException exception) {
			LogoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
	}
}
