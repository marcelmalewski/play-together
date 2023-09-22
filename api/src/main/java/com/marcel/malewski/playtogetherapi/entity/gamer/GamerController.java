package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateAuthenticationDataRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
import com.marcel.malewski.playtogetherapi.security.exception.AuthenticatedGamerNotFoundException;
import com.marcel.malewski.playtogetherapi.security.util.PrincipalExtractor;
import com.marcel.malewski.playtogetherapi.security.util.SecurityHelper;
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

@RestController
@RequestMapping(path = "v1")
@Tag(name = "Gamers v1", description = "Gamers API v1")
public class GamerController {
	private final GamerService gamerService;
	private final SecurityHelper securityHelper;
	private final PrincipalExtractor principalExtractor;

	public GamerController(GamerService gamerService, SecurityHelper securityHelper, PrincipalExtractor principalExtractor) {
		this.gamerService = gamerService;
		this.securityHelper = securityHelper;
		this.principalExtractor = principalExtractor;
	}

	//TODO endpoint not used
	@GetMapping(value = "/gamers")
	@Operation(summary = "Find all gamers public info")
	public ResponseEntity<List<GamerPublicResponseDto>> findAllGamers(Principal principal, HttpServletRequest request,
	                                                                  HttpServletResponse response) {
		gamerService.throwExceptionAndLogoutIfAuthenticatedGamerNotFound(principal, request, response);

		List<GamerPublicResponseDto> allGamers = gamerService.findAllGamersPublicInfo();
		return new ResponseEntity<>(allGamers, HttpStatus.OK);
	}

	@GetMapping(value = "/gamers/{gamerId}")
	@Operation(summary = "Get public info about a gamer by id")
	public ResponseEntity<GamerPublicResponseDto> getGamer(@PathVariable long gamerId, Principal principal, HttpServletRequest request,
	                                                       HttpServletResponse response) {
		gamerService.throwExceptionAndLogoutIfAuthenticatedGamerNotFound(principal, request, response);

		GamerPublicResponseDto gamerPublicResponse = gamerService.getGamerPublicInfo(gamerId);
		return new ResponseEntity<>(gamerPublicResponse, HttpStatus.OK);
	}

	@GetMapping(value = "/gamers/@me")
	@Operation(summary = "Get private info about the authenticated gamer")
	public ResponseEntity<GamerPrivateResponseDto> getGamer(Principal principal, HttpServletRequest request,
	                                                        HttpServletResponse response) {
		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		try {
			GamerPrivateResponseDto gamerPrivateResponse = gamerService.getGamerPrivateInfo(principalId);
			return new ResponseEntity<>(gamerPrivateResponse, HttpStatus.OK);
		} catch (GamerNotFoundException exception) {
			securityHelper.LogoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
	}

	@PutMapping(value = "/gamers/@me/profile-data")
	@Operation(summary = "Update the authenticated gamers's profile data")
	public ResponseEntity<GamerPrivateResponseDto> updateGamerProfile(@Valid @RequestBody GamerUpdateProfileRequestDto updateProfileDto, Principal principal, HttpServletRequest request,
	                                                                  HttpServletResponse response) {
		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		try {
			GamerPrivateResponseDto updatedGamer = gamerService.updateGamerProfile(updateProfileDto, principalId);
			return new ResponseEntity<>(updatedGamer, HttpStatus.OK);
		} catch (GamerNotFoundException exception) {
			securityHelper.LogoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
	}

	@PatchMapping(value = "/gamers/@me/authentication-data")
	@Operation(summary = "Update the authenticated gamers's authentication data")
	public ResponseEntity<GamerPrivateResponseDto> updatePartiallyGamerAuthenticationData(@Valid @RequestBody GamerUpdateAuthenticationDataRequestDto updateAuthDto, Principal principal, HttpServletRequest request,
	                                                                             HttpServletResponse response) {
		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		try {
			GamerPrivateResponseDto updatedGamer = gamerService.updatePartiallyGamerAuthenticationData(updateAuthDto, principalId);
			return new ResponseEntity<>(updatedGamer, HttpStatus.OK);
		} catch (GamerNotFoundException exception) {
			securityHelper.LogoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
	}

	//TODO dodać, że moderator nie może tak po prostu usuną swojego konta?
	@DeleteMapping("/gamers/@me")
	@Operation(summary = "Delete the authenticated gamer and log out")
	public ResponseEntity<Void> deleteGamer(Principal principal, HttpServletRequest request,
	                                        HttpServletResponse response) {
		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		try {
			gamerService.deleteGamer(principalId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (GamerNotFoundException exception) {
			securityHelper.LogoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}
	}
}
