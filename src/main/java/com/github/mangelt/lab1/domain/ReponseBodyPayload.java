package com.github.mangelt.lab1.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@Builder
@AllArgsConstructor
public class ReponseBodyPayload<T> {
	Integer status;
	String message;
	T content;
}
