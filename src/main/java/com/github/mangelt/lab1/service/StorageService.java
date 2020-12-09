package com.github.mangelt.lab1.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.github.mangelt.lab1.domain.ImageDetailsPayload;
import com.github.mangelt.lab1.domain.ResponseBodyImage;

@Validated
public interface StorageService {
	ResponseEntity<List<ResponseBodyImage>> listAvailableImages();
	ResponseEntity<ResponseBodyImage> saveImage(@Valid ImageDetailsPayload image);
}