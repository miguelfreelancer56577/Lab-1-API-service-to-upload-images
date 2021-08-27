package com.github.mangelt.lab1.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.mangelt.lab1.domain.RequestUserPayload;
import com.github.mangelt.lab1.entity.DynamoUserEntity;
import com.github.mangelt.lab1.entity.TableStorageUser;
import com.github.mangelt.lab1.entity.User;

@Configuration
public class MappingConfig {
	
	@Bean
	public ModelMapper mapper() {
		final ModelMapper modelMapper = new ModelMapper();
		//Map from RequestUserPayload to TableStorageUser
		modelMapper
			.typeMap(RequestUserPayload.class, TableStorageUser.class)
			.addMappings(mapper->{
				mapper.map(RequestUserPayload::getUserId, TableStorageUser::setPartitionKey);
				mapper.map(RequestUserPayload::getUserId, TableStorageUser::setRowKey);
				mapper.map(RequestUserPayload::getAuthGroups, TableStorageUser::setAuthGroups);
				mapper.map(RequestUserPayload::getIsAccountNonExpired, TableStorageUser::setIsAccountNonExpired);
				mapper.map(RequestUserPayload::getIsAccountNonLocked, TableStorageUser::setIsAccountNonLocked);
				mapper.map(RequestUserPayload::getIsCredentialsNonExpired, TableStorageUser::setIsCredentialsNonExpired);
				mapper.map(RequestUserPayload::getIsEnabled, TableStorageUser::setIsEnabled);
				mapper.map(RequestUserPayload::getPassword, TableStorageUser::setPassword);
			});
		//Map from RequestUserPayload to TableStorageUser
		modelMapper
			.typeMap(RequestUserPayload.class, User.class)
			.addMappings(mapper->{
				mapper.map(RequestUserPayload::getPassword, User::setPassword);
				mapper.map(RequestUserPayload::getUserId, User::setUserId);
			});
		//Map from RequestUserPayload to DynamoUserEntity
		modelMapper
		.typeMap(RequestUserPayload.class, DynamoUserEntity.class)
		.addMappings(mapper->{
			mapper.map(RequestUserPayload::getPassword, DynamoUserEntity::setPassword);
			mapper.map(RequestUserPayload::getAuthGroupsAsSet, DynamoUserEntity::setAuthGroups);
		});
		//Map from DynamoUserEntity to RequestUserPayload
		modelMapper
		.typeMap(DynamoUserEntity.class, RequestUserPayload.class)
		.addMappings(mapper->{
			mapper.map(DynamoUserEntity::getAuthGroups, RequestUserPayload::setAuthGroupsFromSet);
		});
		return modelMapper;
	}
}
