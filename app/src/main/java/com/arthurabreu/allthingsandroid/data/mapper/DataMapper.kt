package com.arthurabreu.allthingsandroid.data.mapper

import com.arthurabreu.allthingsandroid.data.db.entity.DataEntity
import com.arthurabreu.allthingsandroid.data.remote.dto.ApiDto
import com.arthurabreu.allthingsandroid.domain.exceptions.DomainException
import com.arthurabreu.allthingsandroid.domain.model.DomainData
import com.arthurabreu.allthingsandroid.domain.model.DomainModel


interface DataMapper {
    fun apiDtoToDomainModel(dto: ApiDto): DomainModel
    fun domainDataToEntity(domainData: DomainData): DataEntity
    fun dataEntityToDomainData(entity: DataEntity): DomainData
    fun domainModelToDomainData(domainModel: DomainModel): DomainData
}
/**
 * DataMapperImpl is responsible for converting data between different layers of the application.
 * It implements the DataMapper interface.
 * @see DataMapper
 * @see DomainModel
 * @see DomainData
 * @see DataEntity
 * @see ApiDto
 * @see DomainException
 * @see DataMapperImpl
 */
class DataMapperImpl : DataMapper {

    // ApiDto -> DomainModel conversion
    override fun apiDtoToDomainModel(dto: ApiDto): DomainModel = try {
        DomainModel(
            id = dto.id,
            userId = dto.userId,
            title = dto.title,
            completed = dto.completed
        )
    } catch (e: Exception) {
        throw DomainException.SerializationError("ApiDto to DomainModel failed: ${e.message}")
    }

    // DomainData -> DataEntity conversion
    override fun domainDataToEntity(domainData: DomainData): DataEntity = try {
        DataEntity(
            id = domainData.id,
            userId = domainData.userId,
            title = domainData.title,
            completed = domainData.completed
        )
    } catch (e: Exception) {
        throw DomainException.SerializationError("DomainData to DataEntity failed: ${e.message}")
    }

    // DataEntity -> DomainData conversion
    override fun dataEntityToDomainData(entity: DataEntity): DomainData = try {
        DomainData(
            id = entity.id,
            userId = entity.userId,
            title = entity.title,
            completed = entity.completed
        )
    } catch (e: Exception) {
        throw DomainException.SerializationError("DataEntity to DomainData failed: ${e.message}")
    }

    // DomainModel -> DomainData conversion
    override fun domainModelToDomainData(domainModel: DomainModel): DomainData = try {
        DomainData(
            id = domainModel.id,
            userId = domainModel.userId,
            title = domainModel.title,
            completed = domainModel.completed
        )
    } catch (e: Exception) {
        throw DomainException.SerializationError("DomainModel to DomainData failed: ${e.message}")
    }
}