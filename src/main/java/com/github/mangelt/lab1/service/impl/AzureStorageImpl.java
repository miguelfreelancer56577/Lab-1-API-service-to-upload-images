package com.github.mangelt.lab1.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobProperties;
import com.github.mangelt.lab1.component.ImageValidator;
import com.github.mangelt.lab1.domain.FieldError;
import com.github.mangelt.lab1.domain.ImageDetailsPayload;
import com.github.mangelt.lab1.domain.ReponseBodyPayload;
import com.github.mangelt.lab1.domain.ResponseBodyImage;
import com.github.mangelt.lab1.exception.AppException;
import com.github.mangelt.lab1.exception.AppValidationException;
import com.github.mangelt.lab1.service.StorageService;
import com.github.mangelt.lab1.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Profile(ApiConstants.PROFILE_AZURE)
@ConditionalOnClass(BlobContainerClient.class)
public class AzureStorageImpl implements StorageService {

	@Autowired
	ImageValidator imageValidator;
	
	@Autowired
	BlobContainerClient blobContainer;
	
	@Value("${azure.storage.container.name}")
	protected String containerName;
	
	@Override
	public ResponseEntity<ReponseBodyPayload<List<ImageDetailsPayload>>> listAvailableImages() {
		final ReponseBodyPayload<List<ImageDetailsPayload>> response = new ReponseBodyPayload<>(HttpStatus.OK.value(), ApiConstants.MSG_OK_IMAGE_LIST);
		final List<ImageDetailsPayload> lstImages;
		final List<BlobItem> lstItems;
		
		imageValidator.checkStorage();
		lstItems = StreamSupport
				.stream(blobContainer.listBlobs().spliterator(), false)
				.collect(Collectors.toList());
		lstImages = lstItems
			.stream()
			.map(blobItem->blobContainer.getBlobClient(blobItem.getName()).getProperties())
			.map(BlobProperties::getMetadata)
			.map(objMeta->ImageDetailsPayload
					.builder()
					.imageName(objMeta.get(ApiConstants.REQ_PARAM_IMAGE_NAME))
					.format(objMeta.get(ApiConstants.REQ_PARAM_IMAGE_FORMAT))
					.size(Long.parseLong(objMeta.get(ApiConstants.REQ_PARAM_IMAGE_SIZE)))
					.uploadedDate(Long.parseLong(objMeta.get(ApiConstants.REQ_PARAM_UPLOADED_DATE)))
					.build())
			.collect(Collectors.toList());
		if(lstImages.isEmpty()) {
			response.setMessage(ApiConstants.MSG_OK_IMAGE_UNAVAILABLE);
		}
		response.setContent(lstImages);
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<ResponseBodyImage> saveImage(ImageDetailsPayload image) {
		final Instant instant = Instant.now();
		final ResponseBodyImage response = new ResponseBodyImage();
		final InputStream inputStream;
		final MultipartFile imageFile = image.getImageFile();
		final Map<String, String> metadata = new HashMap<>();
		final String format;
		final BlobClient blobClient;
//		check if it's a valid image 
		imageValidator.checkImage(image);
		try {
			if(!this.doesImageExist(image)) {
				format = FilenameUtils.getExtension(imageFile.getOriginalFilename());
//				get stream of data 
				inputStream = imageFile.getInputStream();
////				set data type of object to be uploaded 
				metadata.put(ApiConstants.REQ_PARAM_IMAGE_NAME, image.getImageName());
				metadata.put(ApiConstants.REQ_PARAM_UPLOADED_DATE, Long.toString(instant.toEpochMilli()));
				metadata.put(ApiConstants.REQ_PARAM_IMAGE_FORMAT, format);
				metadata.put(ApiConstants.REQ_PARAM_IMAGE_SIZE, Long.toString(imageFile.getSize()));
				blobClient = blobContainer.getBlobClient(this.pathToImage(image));
//				upload image to bucket
				blobClient.upload(inputStream, imageFile.getSize());
				blobClient.setMetadata(metadata);
////				sets available information of the stored image
				image.setFormat(format);
				image.setSize(imageFile.getSize());
				image.setUploadedDate(instant.toEpochMilli());
				response.setContent(image);
				response.setMessage(ApiConstants.MSG_OK_IMAGE_SAVE);
			}
		} catch (IOException e) {
			log.error(ApiConstants.EXP_ERROR_READ_FILE, e);
			throw new AppException(ApiConstants.EXP_ERROR_READ_FILE, e);
		}
		return ResponseEntity.ok(response);
	}

	@Override
	public boolean doesImageExist(ImageDetailsPayload image) {
		final List<FieldError> errors;
		final BlobClient blobClient;
			blobClient = blobContainer.getBlobClient(this.pathToImage(image));
			if(blobClient.exists().booleanValue()) {
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
	public String pathToImage(ImageDetailsPayload image) {
		return image.getImageName()
				.concat(ApiConstants.SIGN_DOT)
				.concat(FilenameUtils.getExtension(image.getImageFile().getOriginalFilename()));
	}
	
}
