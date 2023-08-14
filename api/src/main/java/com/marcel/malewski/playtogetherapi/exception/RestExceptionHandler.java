package com.marcel.malewski.playtogetherapi.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
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

		String separator = globalErrorMessages.isEmpty() ? "" : ";";
		String allErrorMessages = String.join("; ", globalErrorMessages) + separator + String.join("; ", fieldErrorMessages);
		return new ExceptionResponse(allErrorMessages);
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConversionFailedException.class)
	public ExceptionResponse handleMethodArgumentNotValidException(ConversionFailedException exception) {
		System.out.println("yers");
		System.out.println(exception.getMessage());
		return new ExceptionResponse("yes");
	}
}
