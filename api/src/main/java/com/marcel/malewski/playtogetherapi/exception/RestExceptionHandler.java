package com.marcel.malewski.playtogetherapi.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

//TODO dodać tutaj logowanie tych exceptionow jako warningi
//TODO dodać logowanie jak string.formatowanie sie wywali
@RestControllerAdvice
public class RestExceptionHandler {

	//TODO dlaczego trzeba to obsłużyć to constraintViolationException
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(ConstraintViolationException.class)
	public ExceptionResponse handleConstraintViolation(ConstraintViolationException constraintViolationException) {
		ExceptionResponse exceptionResponse = new ExceptionResponse("yes");
		return exceptionResponse;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
		List<String> globalErrorMessages = methodArgumentNotValidException.getBindingResult().getGlobalErrors().stream().map(globalError ->
			globalError.getObjectName() + ": " + globalError.getDefaultMessage()
		).toList();
		List<String> fieldErrorMessages = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream().map(globalError ->
			globalError.getObjectName() + ": " + globalError.getDefaultMessage()
		).toList();

		String allErrorMessages = String.join(";", globalErrorMessages) + String.join(";", fieldErrorMessages);
		return new ExceptionResponse(allErrorMessages);
	}
}
