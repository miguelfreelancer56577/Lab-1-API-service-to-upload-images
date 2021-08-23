package com.github.mangelt.lab1.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.mangelt.lab1.domain.FieldError;
import com.github.mangelt.lab1.domain.ReponseBodyPayload;
import com.github.mangelt.lab1.exception.AppValidationException;
import com.github.mangelt.lab1.util.ApiConstants;

@ControllerAdvice
public class ExceptionValidatorController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {ConstraintViolationException.class})
	ResponseEntity<Object> handleValidationPayloadExceptions(
			ConstraintViolationException ex, WebRequest request) {
        return handleExceptionInternal(ex, ReponseBodyPayload
        		.builder()
        		.status(HttpStatus.BAD_REQUEST.value())
        		.message(ApiConstants.EXP_VALIDATION_FIELDS)
        		.content(this.getFields(ex))
        		.build(), 
          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
	
	@ExceptionHandler(value = {AppValidationException.class})
	ResponseEntity<Object> handleValidationImageExceptions(
			AppValidationException ex, WebRequest request) {
		return handleExceptionInternal(ex, ReponseBodyPayload
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(ApiConstants.EXP_VALIDATION_FIELDS)
				.content(ex.getFields())
				.build(), 
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        List<String> validationList = ex.getBindingResult().getFieldErrors().stream().map(fieldError->fieldError.getDefaultMessage()).collect(Collectors.toList());
        return handleExceptionInternal(ex, ReponseBodyPayload
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(errorMessage)
				.content(validationList)
				.build(), 
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	List<FieldError> getFields(RuntimeException ex){
		final List<FieldError> fields = new ArrayList<>();
		final String[] elements = ex.getMessage().split(ApiConstants.SIGN_COMMA);
//		if there is no more than one element then extract the unique record.
		if(elements.length == 0) {
			fields.add(getField(ex.getMessage()));
		}else {
			fields.addAll(Arrays
							.stream(elements)
							.map(this::getField)
							.collect(Collectors.toList()));
		}
		return fields;
	}
	
	FieldError getField(String inputField) {
		final String[] arrFragPro;
		final String[] chain = inputField.split(ApiConstants.SIGN_COLON);
		String fieldName = chain[0];
		if(fieldName.contains(ApiConstants.SIGN_DOT)) {
			arrFragPro = fieldName.split(ApiConstants.SIGN_SACAPED_DOT);
			fieldName = arrFragPro[arrFragPro.length-1];
		}
		final String fieldMessage = chain[1];
		return FieldError
			.builder()
			.fieldMessage(fieldMessage)
			.fieldName(fieldName)
			.build();
	}
	
}
