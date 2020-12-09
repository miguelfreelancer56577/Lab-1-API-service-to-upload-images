package com.github.mangelt.lab1.util;

public class ApiConstants {
	private ApiConstants() {}
	public static final String BASE_API_URL = "/api/v1";
	public static final String MAPPING_IMAGE = "/images";
	
//	message for validation
	public static final String IMAGE_SERVICE_NAME_MANDATORY = "Name of the image is mandatory.";
	public static final String IMAGE_SERVICE_FILE_IMAGE_MANDATORY = "File image is mandatory.";
	public static final String IMAGE_SERVICE_FILE_IMAGE_TYPE = "Only JPG images are permitted.";
	public static final String IMAGE_SERVICE_FILE_IMAGE_SIZE = "Only JPG images equal or less than 1M are permitted.";
	public static final String IMAGE_SERVICE_FILE_IMAGE_ALREADY_REGISTERED = "There is an image already registered with that name.";
	
//	request params
	public static final String REQ_PARAM_IMAGE_FILE = "imageFile";
	public static final String REQ_PARAM_IMAGE_NAME = "imageName";
	
//	signs and cords used along with the application 
	public static final String SIGN_COMMA = ",";
	public static final String SIGN_COLON = ":";
	public static final String SIGN_DOT = ".";
	public static final String CARD_JPG = "jpeg";
	
//	exception messages 
	public static final String EXP_VALIDATION_FIELDS = "Please verify the correctness of required fields.";
	public static final String EXP_ERROR_READ_FILE = "There was an error to get content's file.";
	
//	success messages 
	public static final String MSG_OK_IMAGE_SAVE = "The image was stored successfully.";
	
//	configuration values 
	public static final Long RULE_MAX_IMAGE_SIZE = 1000000L;
	
	
}
