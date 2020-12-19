package com.github.mangelt.lab1.exception;

import java.util.List;

import com.github.mangelt.lab1.domain.FieldError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class AppValidationException extends RuntimeException{
	private static final long serialVersionUID = 8059702607599630536L;
	private final List<FieldError> fields;
}
