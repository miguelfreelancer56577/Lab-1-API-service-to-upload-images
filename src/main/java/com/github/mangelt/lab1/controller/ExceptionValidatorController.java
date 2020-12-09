package com.github.mangelt.lab1.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.mangelt.lab1.domain.FieldError;
import com.github.mangelt.lab1.domain.ReponseBodyPayload;
import com.github.mangelt.lab1.util.ApiConstants;

@ControllerAdvice
public class ExceptionValidatorController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {ConstraintViolationException.class})
	ResponseEntity<Object> handleValidationExceptions(
      RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ReponseBodyPayload
        		.builder()
        		.status(HttpStatus.BAD_REQUEST.value())
        		.message(ApiConstants.EXP_VALIDATION_FIELDS)
        		.content(this.getFields(ex))
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
		String[] chain = inputField.split(ApiConstants.SIGN_COLON);
		return FieldError
			.builder()
			.fieldMessage(chain[1])
			.fieldName(chain[0])
			.build();
	}
	
}
