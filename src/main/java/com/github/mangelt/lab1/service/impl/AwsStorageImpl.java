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

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
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
	public ResponseEntity<ReponseBodyPayload<List<ImageDetailsPayload>>> listAvailableImages() {
		final ReponseBodyPayload<List<ImageDetailsPayload>> response = new ReponseBodyPayload<>(HttpStatus.OK.value(), ApiConstants.MSG_OK_IMAGE_LIST);
		final List<ImageDetailsPayload> lstImages;
		final ObjectListing objects = client.listObjects(bucketName);
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
		response.setContent(lstImages);
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<ResponseBodyImage> saveImage(ImageDetailsPayload image) {
		final Instant instant = Instant.now();
		final ResponseBodyImage response = new ResponseBodyImage();
		final InputStream inputStream;
		final MultipartFile imageFile = image.getImageFile();
		final ObjectMetadata metadata = new ObjectMetadata();
		final String format;
		if(imageValidator.isValid(image) && !this.exists(image)) {
			try {
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
				client.putObject(bucketName, this.getFullPath(image), inputStream, metadata);
//				sets available information of the stored image
				image.setFormat(format);
				image.setSize(imageFile.getSize());
				image.setUploadedDate(instant.toEpochMilli());
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
		S3Object s3object = client.getObject(bucketName, this.getFullPath(image));
		String key = s3object.getKey();
			if(!Objects.isNull(key)) {
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
		return image.getImageName()
				.concat(ApiConstants.SIGN_DOT)
				.concat(FilenameUtils.getExtension(image.getImageFile().getOriginalFilename()));
	}
	
	void doesBucketExist() {
		if(!client.doesBucketExist(bucketName)) {
			log.error(ApiConstants.EXP_ERROR_NOT_EXIST_BUCKET.concat(ApiConstants.MSG_FORMAT_ADDING_INFO), bucketName);
			throw new AppException(ApiConstants.EXP_ERROR_NOT_EXIST_BUCKET, null);
		}
	}

}
