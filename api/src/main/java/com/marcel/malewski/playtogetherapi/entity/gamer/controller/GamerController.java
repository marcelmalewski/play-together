package com.marcel.malewski.playtogetherapi.entity.gamer.controller;

import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerSortOption;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

import static com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeName.GAMER_VIEW_PRIVILEGE;
import static com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeName.PRINCIPLE_PRIVILEGE;

@RestController
@Tag(name = "Gamers v1", description = "Gamers API v1")
public class GamerController {
	public static final String GAMER_PATH_V1 = "/v1/gamers";
	public static final String GAMER_PATH_V1_ID = GAMER_PATH_V1 + "/{gamerId}";
	public static final String GAMER_PATH_V1_ME = GAMER_PATH_V1 + "/@me";
	public static final String GAMER_PATH_V1_PROFILE_DATA = GAMER_PATH_V1_ME + "/profile-data";
	public static final String GAMER_PATH_V1_AUTHENTICATION_DATA = GAMER_PATH_V1_ME + "/authentication-data";

	private final GamerService gamerService;
	private final SecurityHelper securityHelper;
	private final PrincipalExtractor principalExtractor;

	public GamerController(GamerService gamerService, SecurityHelper securityHelper, PrincipalExtractor principalExtractor) {
		this.gamerService = gamerService;
		this.securityHelper = securityHelper;
		this.principalExtractor = principalExtractor;
	}

	//TODO endpoint not used
	@GetMapping(value = GAMER_PATH_V1)
	@Operation(summary = "Find all gamers public info")
	@PreAuthorize("hasRole('" + GAMER_VIEW_PRIVILEGE + "')")
	public ResponseEntity<Page<GamerPublicResponseDto>> findAllGamers(@RequestParam(required = false) Integer pageNumber,
																																		@RequestParam(required = false) Integer pageSize,
																																		@RequestParam(required = false) GamerSortOption sort,
																																		Principal principal, HttpServletRequest request,
																																		HttpServletResponse response,
																																		@RequestParam(required = false) String gamerLogin
	) {
		gamerService.throwExceptionAndLogoutIfAuthenticatedGamerNotFound(principal, request, response);

		Page<GamerPublicResponseDto> allGamers = gamerService.findAllGamersPublicInfo(pageNumber, pageSize, sort, gamerLogin);
		return new ResponseEntity<>(allGamers, HttpStatus.OK);
	}

	@GetMapping(value = GAMER_PATH_V1_ID)
	@Operation(summary = "Get public info about a gamer by id")
	@PreAuthorize("hasRole('" + GAMER_VIEW_PRIVILEGE + "')")
	public ResponseEntity<GamerPublicResponseDto> getGamer(Principal principal, HttpServletRequest request,
																												 HttpServletResponse response, @PathVariable long gamerId) {
		gamerService.throwExceptionAndLogoutIfAuthenticatedGamerNotFound(principal, request, response);

		Optional<GamerPublicResponseDto> optionalGamerPublicResponse = gamerService.findGamerPublicInfo(gamerId);
		if (optionalGamerPublicResponse.isEmpty()) {
			throw new GamerNotFoundException(gamerId);
		}

		return new ResponseEntity<>(optionalGamerPublicResponse.get(), HttpStatus.OK);
	}

	@GetMapping(value = GAMER_PATH_V1_ME)
	@Operation(summary = "Get private info about the authenticated gamer")
	@PreAuthorize("hasRole('" + PRINCIPLE_PRIVILEGE + "')")
	public ResponseEntity<GamerPrivateResponseDto> getGamer(Principal principal, HttpServletRequest request,
																													HttpServletResponse response) {
		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		Optional<GamerPrivateResponseDto> optionalGamerPrivateResponse = gamerService.findGamerPrivateInfo(principalId);
		if (optionalGamerPrivateResponse.isEmpty()) {
			securityHelper.logoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}

		return new ResponseEntity<>(optionalGamerPrivateResponse.get(), HttpStatus.OK);
	}

	@PutMapping(value = GAMER_PATH_V1_PROFILE_DATA)
	@Operation(summary = "Update the authenticated gamers's profile data")
	@PreAuthorize("hasRole('" + PRINCIPLE_PRIVILEGE + "')")
	public ResponseEntity<GamerPrivateResponseDto> updateGamerProfile(Principal principal, HttpServletRequest request,
																																		HttpServletResponse response,
																																		@RequestBody GamerUpdateProfileRequestDto updateProfileDto) {
		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		Optional<GamerPrivateResponseDto> optionalUpdatedGamer = gamerService.tryUpdateGamerProfile(updateProfileDto, principalId);
		if (optionalUpdatedGamer.isEmpty()) {
			securityHelper.logoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}

		return new ResponseEntity<>(optionalUpdatedGamer.get(), HttpStatus.OK);
	}

	@PatchMapping(value = GAMER_PATH_V1_AUTHENTICATION_DATA)
	@Operation(summary = "Update the authenticated gamers's authentication data")
	@PreAuthorize("hasRole('" + PRINCIPLE_PRIVILEGE + "')")
	public ResponseEntity<GamerPrivateResponseDto> updatePartiallyGamerAuthenticationData(Principal principal, HttpServletRequest request,
																																												HttpServletResponse response,
																																												@RequestBody GamerUpdateAuthenticationDataRequestDto updateAuthDto) {
		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		Optional<GamerPrivateResponseDto> optionalUpdatedGamer = gamerService.tryUpdatePartiallyGamerAuthenticationData(updateAuthDto, principalId);
		if (optionalUpdatedGamer.isEmpty()) {
			securityHelper.logoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}

		return new ResponseEntity<>(optionalUpdatedGamer.get(), HttpStatus.OK);
	}

	//TODO jakas blokada zeby ostatni moderator nie usunal sam siebie?
	@DeleteMapping(GAMER_PATH_V1_ME)
	@Operation(summary = "Delete the authenticated gamer and log out")
	@PreAuthorize("hasRole('" + PRINCIPLE_PRIVILEGE + "')")
	public ResponseEntity<Void> deleteGamer(Principal principal, HttpServletRequest request,
																					HttpServletResponse response) {
		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		if (!gamerService.tryDeleteGamer(principalId)) {
			securityHelper.logoutManually(request, response);
			throw new AuthenticatedGamerNotFoundException();
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
