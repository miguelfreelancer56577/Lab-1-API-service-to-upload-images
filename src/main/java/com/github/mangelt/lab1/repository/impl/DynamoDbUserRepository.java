package com.github.mangelt.lab1.repository.impl;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.github.mangelt.lab1.entity.DynamoUserEntity;
import com.github.mangelt.lab1.entity.TableStorageUser;
import com.github.mangelt.lab1.exception.AppException;
import com.github.mangelt.lab1.repository.UserCrudRepository;
import com.github.mangelt.lab1.util.ApiConstants;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableResult;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Repository
@Profile(ApiConstants.PROFILE_AWS)
public class DynamoDbUserRepository implements UserCrudRepository<DynamoUserEntity> {

	private final DynamoDBMapper dynamoDBMapper;
	
	@Override
	public DynamoUserEntity insertOrReplace(DynamoUserEntity entity) {
		dynamoDBMapper.save(entity);
		return entity;
	}

	@Override
	public Optional<DynamoUserEntity> findByEntity(DynamoUserEntity entity) {
		return Optional.ofNullable(dynamoDBMapper.load(entity)); 
	}

	@Override
	public void deleteByEntity(DynamoUserEntity entity) {
		final Optional<DynamoUserEntity> findEntity = this.findByEntity(entity);
		findEntity.ifPresent(row->dynamoDBMapper.delete(entity));
	}

}
