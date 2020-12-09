package com.github.mangelt.lab1.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldError {
	String fieldName;
	String fieldMessage;
}
