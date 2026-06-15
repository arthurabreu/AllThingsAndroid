package com.arthurabreu.allthingsandroid.domain.model

/**
 * DomainModel is a data class that represents the data that will be used in the domain layer.
 * It contains the id, userId, title and completed fields.
 *
 * @param id the id of the data
 * @param userId the id of the user that owns the data
 * @param title the title of the data
 * @param completed a boolean that represents if the data is completed
 */
data class DomainModel(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean,
) {
    override fun toString(): String {
        return """
            DomainModel(
                id: ${this.id},
                userId: ${this.userId},
                title: "${this.title}",
                completed: ${this.completed}
            )
        """.trimIndent()
    }
}