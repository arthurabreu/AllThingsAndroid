package com.arthurabreu.allthingsandroid.data.remote.dto

import kotlinx.serialization.Serializable

/* Tips
Case sensitivity matters: userId ≠ userID.
Use @SerialName("json_key") if field names differ from JSON keys.
 */

/*
The @Serializable annotation is used to mark a class as serializable.
This means that the class can be converted to and from JSON using kotlinx.serialization.

The ApiDto class represents the data returned by the API.
It contains the userId, id, title, and completed fields.

The ApiDto class is used to map the JSON response from the API to a Kotlin object.
 */
@Serializable
data class ApiDto(
    val userId: Int, // Field names must match JSON keys exactly
    val id: Int,
    val title: String,
    val completed: Boolean
)