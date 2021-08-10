package com.github.mangelt.lab1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mangelt.lab1.domain.ImageDetailsPayload;
import com.github.mangelt.lab1.domain.ReponseBodyPayload;
import com.github.mangelt.lab1.domain.ResponseBodyImage;
import com.github.mangelt.lab1.service.StorageService;
import com.github.mangelt.lab1.util.ApiConstants;

@RestController
@RequestMapping(ApiConstants.BASE_API_URL)
public class ImageController {

	@Autowired
	StorageService storageService;
	
	@PostMapping(ApiConstants.MAPPING_IMAGE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ResponseBodyImage> storeImage(ImageDetailsPayload image){
		return storageService.saveImage(image);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(path = ApiConstants.MAPPING_IMAGE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReponseBodyPayload<List<ImageDetailsPayload>>> listImages(){
		return storageService.listAvailableImages();
	}

}
