package com.github.mangelt.lab1.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.mangelt.lab1.component.ImageValidator;
import com.github.mangelt.lab1.domain.FieldError;
import com.github.mangelt.lab1.domain.ImageDetailsPayload;
import com.github.mangelt.lab1.domain.ResponseBodyImage;
import com.github.mangelt.lab1.exception.AppException;
import com.github.mangelt.lab1.exception.AppValidationException;
import com.github.mangelt.lab1.service.StorageService;
import com.github.mangelt.lab1.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LocalStorageImpl implements StorageService {

	@Autowired
	ImageValidator imageValidator;
	
	@Value("#{'${app.config.image.protocol}'.concat('${app.config.image.directory}')}")
	String path;
	
	@Override
	public ResponseEntity<List<ResponseBodyImage>> listAvailableImages() {
		return null;
	}

	@Override
	public ResponseEntity<ResponseBodyImage> saveImage(ImageDetailsPayload image) {
		final ResponseBodyImage response = new ResponseBodyImage();
		final InputStream inputStream;
		final MultipartFile imageFile = image.getImageFile();
		final File file;
		if(imageValidator.isValid(image) && !this.exists(image)) {
			try {
				inputStream = imageFile.getInputStream();
				file = new File(this.getFullPath(image));
				FileUtils.copyInputStreamToFile(inputStream, file);
				response.setContent(image);
				response.setMessage(ApiConstants.MSG_OK_IMAGE_SAVE);
			} catch (IOException e) {
				log.error(ApiConstants.EXP_ERROR_READ_FILE, e);
				throw new AppException(ApiConstants.EXP_ERROR_READ_FILE, e);
			}
		}
		return ResponseEntity.ok(response);
	}

	@Override
	public boolean exists(ImageDetailsPayload image) {
		final List<FieldError> errors;
//		Image must not be registered with the same name
		final File file = new File(this.getFullPath(image));
		if(file.exists()) {
			errors = new ArrayList<>();
			errors.add(FieldError
					.builder()
					.fieldName(ApiConstants.REQ_PARAM_IMAGE_NAME)
					.fieldMessage(ApiConstants.IMAGE_SERVICE_FILE_IMAGE_ALREADY_REGISTERED)
					.build());
			throw new AppValidationException(errors);
		}
		return false;
	}

	@Override
	public String getFullPath(ImageDetailsPayload image) {
		return path
				.concat(image.getImageName())
				.concat(ApiConstants.SIGN_DOT)
				.concat(FilenameUtils.getExtension(image.getImageFile().getOriginalFilename()));
	}

}
