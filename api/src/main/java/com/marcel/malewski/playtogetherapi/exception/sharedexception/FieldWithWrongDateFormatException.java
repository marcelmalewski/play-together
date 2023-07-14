package com.marcel.malewski.playtogetherapi.exception.sharedexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FieldWithWrongDateFormatException extends RuntimeException {
	public FieldWithWrongDateFormatException(String field) {
		super(field + ": expected date format is yyyy-MM-dd");
	}
}
