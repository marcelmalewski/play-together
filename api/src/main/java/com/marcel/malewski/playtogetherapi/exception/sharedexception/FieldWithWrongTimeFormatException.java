package com.marcel.malewski.playtogetherapi.exception.sharedexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FieldWithWrongTimeFormatException extends RuntimeException {
	public FieldWithWrongTimeFormatException(String field) {
		super(field + ": expected time format is HH:mm");
	}
}
