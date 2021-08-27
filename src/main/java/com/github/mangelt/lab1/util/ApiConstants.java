package com.github.mangelt.lab1.util;

public class ApiConstants {
	private ApiConstants() {}
	public static final String BASE_API_URL = "/api/v1";
	public static final String MAPPING_IMAGE = "/images";
	public static final String MAPPING_USER = "/users";
	public static final String MAPPING_AUTHENTICATION = "/authentication";
	public static final String MAPPING_SIGNIN = "/signin";
	public static final String MAPPING_SIGNUP = "/signup";
	public static final String MAPPING_REFRESHTOKEN = "/refreshtoken";
	
//	message for validation
	public static final String IMAGE_SERVICE_NAME_MANDATORY = "Name of the image is mandatory.";
	public static final String IMAGE_SERVICE_FILE_IMAGE_MANDATORY = "File image is mandatory.";
	public static final String IMAGE_SERVICE_FILE_IMAGE_TYPE = "Only JPG images are permitted.";
	public static final String IMAGE_SERVICE_FILE_IMAGE_SIZE = "Only JPG images equal or less than 1M are permitted.";
	public static final String IMAGE_SERVICE_FILE_IMAGE_ALREADY_REGISTERED = "There is an image already registered with that name.";
	public static final String USER_SERVICE_USERID_MANDATORY = "User Id is mandatory.";
	public static final String USER_SERVICE_NAME_MANDATORY = "Name is mandatory.";
	public static final String USER_SERVICE_LASTNAME_MANDATORY = "Last Name is mandatory.";
	public static final String USER_SERVICE_SECONDNAME_MANDATORY = "Second Name is mandatory.";
	public static final String USER_SERVICE_PASSWORD_MANDATORY = "Password Name is mandatory.";
	public static final String USER_SERVICE_EDGE_MANDATORY = "Edge Name is mandatory.";
	public static final String USER_SERVICE_AUTHGROUPS_MANDATORY = "Auth Group is mandatory.";
	public static final String USER_SERVICE_SIGNIN_OK = "Token generated properly.";
	
//	request params
	public static final String REQ_PARAM_IMAGE_FILE = "imageFile";
	public static final String REQ_PARAM_IMAGE_NAME = "imageName";
	public static final String REQ_PARAM_IMAGE_FORMAT = "format";
	public static final String REQ_PARAM_IMAGE_SIZE = "size";
	public static final String REQ_PARAM_UPLOADED_DATE = "uploaded_date";
	
//	signs and cords used along with the application 
	public static final String SIGN_COMMA = ",";
	public static final String SIGN_COLON = ":";
	public static final String SIGN_DOT = ".";
	public static final String SIGN_SACAPED_DOT = "\\.";
	public static final String CARD_JPG = "jpg";
	public static final String DEFAULT_ROLE = "ROLE_USER";
	public static final String CARD_AUTHORITIES = "authorities";
	public static final String CARD_AUTHORIZATION = "Authorization";
	public static final String CARD_BEARER = "Bearer";
	public static final String CARD_BLANK = " ";
	public static final String CARD_EMPTY = "";
	public static final String CARD_MATCH_ALL_FRAGMENTS = "/**";
	public static final int DEFAULTS_SECURITY_STRENGTH = 11;
	
//	exception messages 
	public static final String EXP_VALIDATION_FIELDS = "Please verify the correctness of required fields.";
	public static final String EXP_ERROR_READ_FILE = "There was an error to get content's file.";
	public static final String EXP_ERROR_READ_AVAILABLE_IMAGES = "There was an error to list available images from storage.";
	public static final String EXP_ERROR_READ_METADATA_IMAGES = "There was an error getting metadata from the image.";
	public static final String EXP_ERROR_NOT_EXIST_BUCKET = "The bucket you're trying to access does not exist.";
	public static final String EXP_ERROR_STORAGE_UNAVAILABLE = "The storage service is unavailable.";
	public static final String EXP_ERROR_CREATE_DIR = "There was an error to create directory in your file system.";
	public static final String EXP_ERROR_INSERT_USER = "There was an error to insert the user.";
	public static final String EXP_ERROR_FIND_USER = "There was an error to find the user.";
	public static final String EXP_ERROR_USER_ALREADY_REGISTERED = "The user is already registered.";
	public static final String EXP_ERROR_USER_NOT_REGISTERED = "User is not registered to be updated.";
	public static final String EXP_ERROR_DELETE_USER = "There was an error to delete the user.";
	public static final String EXP_ERROR_NOT_FOUND_USER = "cannot find username: ";
	public static final String EXP_ERROR_INVALID_CREDENCIALS = "Invalid credencials.";
	public static final String EXP_ERROR_EXPIRED_TOKEN = "Your token has expired.";
	public static final String EXP_ERROR_INVALID_TOKEN = "Invalid Token.";
	public static final String EXP_ERROR_INVALID_USER = "Invalid user.";
	public static final String EXP_ERROR_DISABLED_USER = "User disabled.";
	public static final String EXP_ERROR_INVALID_ROLES = "You do not have roles assigned.";
	public static final String EXP_ERROR_REQUEST_WITHOUT_TOKEN = "This request doesn't have a valid token.";
	
//	message format
	public static final String MSG_FORMAT_ADDING_INFO = ": {}";
	public static final String MSG_FORMAT_IMAGE_PATH = " IMAGE PATH {}.";
	public static final String MSG_FORMAT_IMAGE_NOT_STORED = "Image isn't stored in the repository under this name: {}";
	public static final String MSG_FORMAT_IMAGE_UNEXPECTED_ERROR = "There was an unexpected error";
	
	
//	success messages 
	public static final String MSG_OK_IMAGE_SAVE = "The image was stored successfully.";
	public static final String MSG_OK_IMAGE_LIST = "The images were retrieved successfully from the storage.";
	public static final String MSG_OK_IMAGE_UNAVAILABLE = "there are no available images stored.";
	public static final String MSG_CREATED_USER = "User was created successfully.";
	public static final String MSG_OK_USER = "User was updated successfully.";
	public static final String MSG_DELETED_USER = "User was deleted successfully.";
	
//	configuration values 
	public static final Long RULE_MAX_IMAGE_SIZE = 1000000L;
	
//	spring profiles
	public static final String PROFILE_AWS = "aws";
	public static final String PROFILE_AZURE = "azure";
	public static final String PROFILE_LOCAL = "local";
	
//	bean names
	public static final String BEAN_S3_CLIENT = "s3_client";
	
//	spring properties
	public static final String APP_CONFIG_IMAGE_DIRECTORY = "${app.config.image.directory}";
	public static final String APP_CONFIG_IMAGE_AWS_BUCKET_NAME = "${app.config.image.aws.bucket-name}";
	public static final String APP_CONFIG_IMAGE_AWS_ACCESSKEY = "${app.config.image.aws.accesskey}";
	public static final String APP_CONFIG_IMAGE_AWS_SECRETKEY = "${app.config.image.aws.secretkey}";
	public static final String APP_CONFIG_IMAGE_AWS_REGION = "${app.config.image.aws.region}";
	public static final String SPRING_PROFILES_ACTIVE = "${spring.profiles.active}";
	
}
