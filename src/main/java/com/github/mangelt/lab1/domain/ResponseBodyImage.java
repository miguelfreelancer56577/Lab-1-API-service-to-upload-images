package com.github.mangelt.lab1.domain;

public class ResponseBodyImage extends ReponseBodyPayload<ImageDetailsPayload>{

	public ResponseBodyImage(Integer status, String message, ImageDetailsPayload content) {
		super(status, message, content);
	}

}
