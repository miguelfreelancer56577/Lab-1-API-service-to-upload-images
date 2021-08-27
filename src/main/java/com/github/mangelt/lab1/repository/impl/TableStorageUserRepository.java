package com.github.mangelt.lab1.repository.impl;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.github.mangelt.lab1.entity.TableStorageUser;
import com.github.mangelt.lab1.exception.AppException;
import com.github.mangelt.lab1.repository.UserCrudRepository;
import com.github.mangelt.lab1.util.ApiConstants;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableResult;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Repository
@Profile(ApiConstants.PROFILE_AZURE)
public class TableStorageUserRepository implements UserCrudRepository<TableStorageUser> {

	private final CloudTable cloudTable;
	
	@Override
	public TableStorageUser insertOrReplace(TableStorageUser entity) {
		final TableOperation operation = TableOperation.insertOrReplace(entity);
		final TableStorageUser rs;
		try {
			TableResult execute = cloudTable.execute(operation);
			rs = execute.getResultAsType();
		} catch (StorageException e) {
			log.error(ApiConstants.EXP_ERROR_INSERT_USER, e);
			throw new AppException(ApiConstants.EXP_ERROR_INSERT_USER, e);
		}
		return rs;
	}

	@Override
	public Optional<TableStorageUser> findByEntity(TableStorageUser entity) {
		final TableOperation operation = TableOperation.retrieve(entity.getPartitionKey(), entity.getRowKey(), TableStorageUser.class);
		final Optional<TableStorageUser> rs; 
		try {
			rs = Optional.ofNullable(cloudTable.execute(operation).getResultAsType());
		} catch (StorageException e) {
			log.error(ApiConstants.EXP_ERROR_FIND_USER, e);
			throw new AppException(ApiConstants.EXP_ERROR_FIND_USER, e);
		}
		return rs;
	}

	@Override
	public void deleteByEntity(TableStorageUser entity) {
		final Optional<TableStorageUser> findEntity = this.findByEntity(entity);
		findEntity.ifPresent(row->{
			final TableOperation operation = TableOperation.delete(row);
			try {
				cloudTable.execute(operation);
			} catch (StorageException e) {
				log.error(ApiConstants.EXP_ERROR_DELETE_USER, e);
				throw new AppException(ApiConstants.EXP_ERROR_DELETE_USER, e);
			}
		});
	}

}
