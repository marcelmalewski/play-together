package com.marcel.malewski.playtogetherapi.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(ConstraintViolationException.class)
	public ExceptionResponse handleConstraintViolation(ConstraintViolationException constraintViolationException) {
		ExceptionResponse exceptionResponse = new ExceptionResponse("yes");
		return exceptionResponse;
	}
}
