package com.arthurabreu.allthingsandroid.domain.model

data class DownloadData(
    val id: String,
    val content: String
) {
    override fun toString(): String {
        return """
            DownloadData(
                id: ${this.id},
                userId: ${this.content}
            )
        """.trimIndent()
    }
}