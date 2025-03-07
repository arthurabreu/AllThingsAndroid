package com.arthurabreu.allthingsandroid.data.mapper

import com.arthurabreu.allthingsandroid.data.remote.dto.ApiDto
import com.arthurabreu.allthingsandroid.domain.exceptions.DomainException
import com.arthurabreu.allthingsandroid.domain.model.DomainModel

interface ApiMapper {
    fun mapToDomain(dto: ApiDto): DomainModel
}

class ApiMapperImpl : ApiMapper {
    override fun mapToDomain(dto: ApiDto): DomainModel = try {
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