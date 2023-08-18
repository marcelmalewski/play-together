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

import static com.marcel.malewski.playtogetherapi.util.PrincipalExtractor.extractGamerIdFromPrincipal;
import static com.marcel.malewski.playtogetherapi.util.Security.LogoutManually;

@RestController
@RequestMapping(path = "v1")
@Tag(name = "Gamers v1", description = "Gamers API v1")
public class GamerController {
	private final GamerService gamerService;

	public GamerController(GamerService gamerService) {
		this.gamerService = gamerService;
	}

	//TODO endpoint not used
	@GetMapping(value = "/gamers")
	@Operation(summary = "Find all gamers public info")
	public ResponseEntity<List<GamerPublicResponseDto>> findAllGamers(Principal principal, HttpServletRequest request,
	                                                                  HttpServletResponse response) {
		this.gamerService.throwExceptionAndLogoutIfAuthenticatedGamerNotFound(principal, request, response);

		List<GamerPublicResponseDto> allGamers = this.gamerService.findAllGamersPublicInfo();
		return new ResponseEntity<>(allGamers, HttpStatus.OK);
	}

	@GetMapping(value = "/gamers/{gamerId}")
	@Operation(summary = "Get public info about a gamer by id")
	public ResponseEntity<GamerPublicResponseDto> getGamer(@PathVariable long gamerId, Principal principal, HttpServletRequest request,
	                                                       HttpServletResponse response) {
		this.gamerService.throwExceptionAndLogoutIfAuthenticatedGamerNotFound(principal, request, response);

		GamerPublicResponseDto gamerPublic = this.gamerService.getGamerPublicInfo(gamerId);
		return new ResponseEntity<>(gamerPublic, HttpStatus.OK);
	}

	@GetMapping(value = "/gamers/@me")
	@Operation(summary = "Get private info about the authenticated gamer")
	public ResponseEntity<GamerPrivateResponseDto> getGamer(Principal principal, HttpServletRequest request,
	                                                        HttpServletResponse response) {
		long gamerId = extractGamerIdFromPrincipal(principal);

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
		long gamerId = extractGamerIdFromPrincipal(principal);

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
	public ResponseEntity<GamerPrivateResponseDto> updatePartiallyGamerAuthenticationData(@Valid @RequestBody GamerUpdateAuthenticationDataRequestDto updateAuthDto, Principal principal, HttpServletRequest request,
	                                                                             HttpServletResponse response) {
		long gamerId = extractGamerIdFromPrincipal(principal);

		try {
			GamerPrivateResponseDto updatedGamer = this.gamerService.updatePartiallyGamerAuthenticationData(updateAuthDto, gamerId);
			return new ResponseEntity<>(updatedGamer, HttpStatus.OK);
		} catch (GamerNotFoundException exception) {
			LogoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
	}

	//TODO dodać, że moderator nie może tak po prostu usuną swojego konta
	@DeleteMapping("/gamers/@me")
	@Operation(summary = "Delete the authenticated gamer and log out")
	public ResponseEntity<Void> deleteGamer(Principal principal, HttpServletRequest request,
	                                        HttpServletResponse response) {
		long gamerId = extractGamerIdFromPrincipal(principal);

		try {
			gamerService.deleteGamer(gamerId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (GamerNotFoundException exception) {
			LogoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
	}
}
