package com.arthurabreu.allthingsandroid.data.remote.dto

import kotlinx.serialization.Serializable

/* Tips
Case sensitivity matters: userId ≠ userID.
Use @SerialName("json_key") if field names differ from JSON keys.
 */

@Serializable
data class ApiDto(
    val userId: Int, // Field names must match JSON keys exactly
    val id: Int,
    val title: String,
    val completed: Boolean
)