package com.marcel.malewski.playtogetherapi.exception.sharedexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.marcel.malewski.playtogetherapi.consts.DateUtils.DATE_FORMAT;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FieldWithWrongDateFormatException extends RuntimeException {
	public FieldWithWrongDateFormatException(String field) {
		super(field + ": expected date format is " + DATE_FORMAT);
	}
}
