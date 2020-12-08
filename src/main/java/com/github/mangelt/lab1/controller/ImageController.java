package com.github.mangelt.lab1.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mangelt.lab1.domain.ImageDetailsPayload;
import com.github.mangelt.lab1.domain.ResponseBodyImage;
import com.github.mangelt.lab1.util.ApiConstants;

@RestController
@RequestMapping(ApiConstants.BASE_API_URL)
public class ImageController {
	
	@PostMapping(ApiConstants.MAPPING_IMAGE)
	public ResponseEntity<ResponseBodyImage> storeImage(@Valid @RequestBody ImageDetailsPayload image){
		ResponseBodyImage responseBodyImage = new ResponseBodyImage(HttpStatus.OK.value(), "", image);
		return ResponseEntity.ok(responseBodyImage);
	}

}
