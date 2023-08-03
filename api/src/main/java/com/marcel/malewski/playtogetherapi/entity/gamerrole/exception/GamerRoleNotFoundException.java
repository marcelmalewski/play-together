package com.marcel.malewski.playtogetherapi.entity.gamerrole.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GamerRoleNotFoundException extends RuntimeException {
	public GamerRoleNotFoundException(Long gamerRoleId) {
		super("GamerRole not found, gamerRoleId: " + gamerRoleId);

	}
}
