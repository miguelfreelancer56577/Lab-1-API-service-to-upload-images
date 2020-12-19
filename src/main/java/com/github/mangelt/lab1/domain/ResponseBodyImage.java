package com.github.mangelt.lab1.domain;

import org.springframework.http.HttpStatus;

public class ResponseBodyImage extends ReponseBodyPayload<ImageDetailsPayload>{
	public ResponseBodyImage() {
		super(HttpStatus.OK.value());
	}
	public ResponseBodyImage(Integer status, String message) {
		super(status, message);
	}
}
