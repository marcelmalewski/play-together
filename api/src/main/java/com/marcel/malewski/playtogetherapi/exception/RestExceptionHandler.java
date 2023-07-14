package com.marcel.malewski.playtogetherapi.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

//TODO dodać tutaj logowanie tych exceptionow jako warningi
//TODO dodać logowanie jak string.formatowanie sie wywali
@RestControllerAdvice
public class RestExceptionHandler {

	//TODO dlaczego trzeba to obsłużyć to constraintViolationException i co wywołyje taki wyjątek
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(ConstraintViolationException.class)
	public ExceptionResponse handleConstraintViolation(ConstraintViolationException exception) {
		List<String> constraintViolations = exception.getConstraintViolations().stream().map(constraintViolation ->
			constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage()
		).toList();

		return new ExceptionResponse(String.join(",", constraintViolations));
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		List<String> globalErrorMessages = exception.getBindingResult().getGlobalErrors().stream().map(globalError ->
			globalError.getObjectName() + ": " + globalError.getDefaultMessage()
		).toList();
		List<String> fieldErrorMessages = exception.getBindingResult().getFieldErrors().stream().map(fieldError ->
			fieldError.getField() + ": " + fieldError.getDefaultMessage()
		).toList();

		//TODO co jak globalErrorMessages jest puste wtedy jest ";" na początku zdania niezbyt dobre
		String allErrorMessages = String.join("; ", globalErrorMessages) + "; " + String.join("; ", fieldErrorMessages);
		return new ExceptionResponse(allErrorMessages);
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ExceptionResponse handleParseException(HttpMessageNotReadableException exception) {

		System.out.println("yes");
		return new ExceptionResponse("allErrorMessages");
	}
}
