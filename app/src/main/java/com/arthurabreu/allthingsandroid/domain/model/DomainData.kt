package com.arthurabreu.allthingsandroid.domain.model

data class DomainData(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean,
) {
    override fun toString(): String {
        return """
            DomainData(
                id: ${this.id},
                userId: ${this.userId},
                title: "${this.title}",
                completed: ${this.completed}
            )
        """.trimIndent()
    }
}