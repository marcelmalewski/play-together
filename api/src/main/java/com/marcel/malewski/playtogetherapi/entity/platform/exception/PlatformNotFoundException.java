package com.marcel.malewski.playtogetherapi.entity.platform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PlatformNotFoundException extends RuntimeException {
	public PlatformNotFoundException(Long platformId) {
		super("Given platform does not exist, platformId: " + platformId);
	}
}
