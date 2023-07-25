package com.marcel.malewski.playtogetherapi.exception.sharedexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.marcel.malewski.playtogetherapi.consts.DateUtils.TIME_FORMAT;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FieldWithWrongTimeFormatException extends RuntimeException {
	public FieldWithWrongTimeFormatException(String field) {
		super(field + ": expected time format is " + TIME_FORMAT);
	}
}
