package com.github.mangelt.lab1.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.github.mangelt.lab1.domain.ImageDetailsPayload;
import com.github.mangelt.lab1.domain.ResponseBodyImage;
import com.github.mangelt.lab1.service.StorageService;

@Service
public class LocalStorageImpl implements StorageService {

	@Override
	public ResponseEntity<List<ResponseBodyImage>> listAvailableImages() {
		return null;
	}

	@Override
	public ResponseEntity<ResponseBodyImage> saveImage(ImageDetailsPayload image) {
		return null;
	}

}
