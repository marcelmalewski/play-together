package com.marcel.malewski.playtogetherapi.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//TODO dodać tutaj logowanie tych exceptionow jako warningi
@RestControllerAdvice
public class RestExceptionHandler {

	//TODO dokonczyc, pytanie kiedy taki wyjatek jest rzucany
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(ConstraintViolationException.class)
	public ExceptionResponse handleConstraintViolation(ConstraintViolationException constraintViolationException) {
		ExceptionResponse exceptionResponse = new ExceptionResponse("yes");
		return exceptionResponse;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
		System.out.println(methodArgumentNotValidException);
		ExceptionResponse exceptionResponse = new ExceptionResponse("method validation");
		return exceptionResponse;
	}
}
