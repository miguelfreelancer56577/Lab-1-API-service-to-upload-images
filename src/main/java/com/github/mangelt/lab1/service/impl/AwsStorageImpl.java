package com.github.mangelt.lab1.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.github.mangelt.lab1.annotation.PerformanceStorageLogger;
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
@Profile(ApiConstants.PROFILE_AWS)
@ConditionalOnBean(name = {ApiConstants.BEAN_S3_CLIENT})
public class AwsStorageImpl implements StorageService {

	@Autowired
	ImageValidator imageValidator;
	
	@Value(ApiConstants.APP_CONFIG_IMAGE_DIRECTORY)
	String tmpDirectory;
	
	@Value(ApiConstants.APP_CONFIG_IMAGE_AWS_BUCKET_NAME)
	String bucketName;
	
	@Autowired
	@Qualifier(ApiConstants.BEAN_S3_CLIENT)
	AmazonS3 client;
	
	@Override
	@PerformanceStorageLogger
	public ResponseEntity<ReponseBodyPayload<List<ImageDetailsPayload>>> listAvailableImages() {
		final ReponseBodyPayload<List<ImageDetailsPayload>> response = new ReponseBodyPayload<>(HttpStatus.OK.value(), ApiConstants.MSG_OK_IMAGE_LIST);
		final List<ImageDetailsPayload> lstImages;
		imageValidator.checkStorage();
		final ObjectListing objects = client.listObjects(bucketName);
		log.debug("ObjectListing: {}", objects);
		lstImages = objects
			.getObjectSummaries()
			.stream()
			.map(objSumm->client.getObject(bucketName, objSumm.getKey()))
			.map(S3Object::getObjectMetadata)
			.map(objMeta->ImageDetailsPayload
					.builder()
					.imageName(objMeta.getUserMetaDataOf(ApiConstants.REQ_PARAM_IMAGE_NAME))
					.format(objMeta.getUserMetaDataOf(ApiConstants.REQ_PARAM_IMAGE_FORMAT))
					.size(Long.parseLong(objMeta.getUserMetaDataOf(ApiConstants.REQ_PARAM_IMAGE_SIZE)))
					.uploadedDate(Long.parseLong(objMeta.getUserMetaDataOf(ApiConstants.REQ_PARAM_UPLOADED_DATE)))
					.build())
			.collect(Collectors.toList());
		log.debug("lstImages: {}", lstImages);
		if(lstImages.isEmpty()) {
			log.debug(ApiConstants.MSG_OK_IMAGE_UNAVAILABLE);
			response.setMessage(ApiConstants.MSG_OK_IMAGE_UNAVAILABLE);
		}
		response.setContent(lstImages);
		log.debug("response: {}", response);
		return ResponseEntity.ok(response);
	}

	@Override
	@PerformanceStorageLogger
	public ResponseEntity<ResponseBodyImage> saveImage(ImageDetailsPayload image) {
		final Instant instant = Instant.now();
		final ResponseBodyImage response = new ResponseBodyImage();
		final InputStream inputStream;
		final MultipartFile imageFile = image.getImageFile();
		final ObjectMetadata metadata = new ObjectMetadata();
		final String format;
//		check if it's a valid image
		imageValidator.checkImage(image);
		try {
			if(!this.doesImageExist(image)) {
				format = FilenameUtils.getExtension(imageFile.getOriginalFilename());
//				get stream of data 
				inputStream = imageFile.getInputStream();
//				set data type of object to be uploaded 
				metadata.setContentType(imageFile.getContentType());
				metadata.addUserMetadata(ApiConstants.REQ_PARAM_IMAGE_NAME, image.getImageName());
				metadata.addUserMetadata(ApiConstants.REQ_PARAM_UPLOADED_DATE, Long.toString(instant.toEpochMilli()));
				metadata.addUserMetadata(ApiConstants.REQ_PARAM_IMAGE_FORMAT, format);
				metadata.addUserMetadata(ApiConstants.REQ_PARAM_IMAGE_SIZE, Long.toString(imageFile.getSize()));
//				upload image to bucket
				client.putObject(bucketName, this.pathToImage(image), inputStream, metadata);
//				sets available information of the stored image
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
	@PerformanceStorageLogger
	public boolean doesImageExist(ImageDetailsPayload image) {
		final List<FieldError> errors;
		try {
//			check if the image is already registered in the bucket  
			client.getObject(bucketName, this.pathToImage(image));
			errors = new ArrayList<>();
			errors.add(FieldError
					.builder()
					.fieldName(ApiConstants.REQ_PARAM_IMAGE_NAME)
					.fieldMessage(ApiConstants.IMAGE_SERVICE_FILE_IMAGE_ALREADY_REGISTERED)
					.build());
			throw new AppValidationException(errors);
		} catch (SdkClientException e) {
			log.info(ApiConstants.MSG_FORMAT_IMAGE_NOT_STORED, image.getImageName());
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
