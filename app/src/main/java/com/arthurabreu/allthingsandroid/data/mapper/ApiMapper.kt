package com.arthurabreu.allthingsandroid.data.mapper

import com.arthurabreu.allthingsandroid.data.remote.dto.ApiDto
import com.arthurabreu.allthingsandroid.domain.exception.NetworkException
import com.arthurabreu.allthingsandroid.domain.model.DomainModel

class ApiMapper {
    fun mapToDomain(dto: ApiDto): DomainModel {
        return try {
            DomainModel(
                id = dto.id,
                userId = dto.userId,
                title = dto.title,
                completed = dto.completed,

            )
        } catch (e: Exception) {
            throw NetworkException.SerializationError(e.message.toString())
        }
    }
}