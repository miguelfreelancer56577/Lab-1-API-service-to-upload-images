package com.github.mangelt.lab1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mangelt.lab1.domain.ImageDetailsPayload;
import com.github.mangelt.lab1.domain.ResponseBodyImage;
import com.github.mangelt.lab1.service.StorageService;
import com.github.mangelt.lab1.util.ApiConstants;

@RestController
@RequestMapping(ApiConstants.BASE_API_URL)
public class ImageController {

	@Autowired
	StorageService storageService;
	
	@PostMapping(ApiConstants.MAPPING_IMAGE)
	public ResponseEntity<ResponseBodyImage> storeImage(ImageDetailsPayload image){
		return storageService.saveImage(image);
	}

}
