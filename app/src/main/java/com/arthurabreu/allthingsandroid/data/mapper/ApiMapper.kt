package com.arthurabreu.allthingsandroid.data.mapper

import com.arthurabreu.allthingsandroid.data.remote.dto.ApiDto
import com.arthurabreu.allthingsandroid.domain.exceptions.DomainException
import com.arthurabreu.allthingsandroid.domain.model.DomainModel

interface ApiMapper {
    fun mapToDomainModel(dto: ApiDto): DomainModel
}

/**
 * Maps an [ApiDto] to a [DomainModel]
 * @throws DomainException.SerializationError if the mapping fails
 * @return a [DomainModel] object
 * @param dto the [ApiDto] to be mapped
 * @see DomainModel
 * @see ApiDto
 * @see DomainException.SerializationError
 * @see ApiMapper
 * @see ApiMapperImpl
 * @see ApiMapperImpl.mapToDomainModel
 * @see ApiMapperImpl.mapToDto
 * @see ApiMapperImpl.mapToDtoList
 * @see ApiMapperImpl.mapToDomainModelList
 */
class ApiMapperImpl : ApiMapper {
    override fun mapToDomainModel(dto: ApiDto): DomainModel = try {
        DomainModel(
            id = dto.id,
            userId = dto.userId,
            title = dto.title,
            completed = dto.completed
        )
    } catch (e: Exception) {
        throw DomainException.SerializationError("Mapping failed: ${e.message}")
    }
}