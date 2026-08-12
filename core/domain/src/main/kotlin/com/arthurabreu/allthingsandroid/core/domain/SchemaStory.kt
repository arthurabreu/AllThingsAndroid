package com.arthurabreu.allthingsandroid.core.domain

import com.arthurabreu.allthingsandroid.core.model.SchemaInfo

object SchemaStory {
    fun versions(): List<SchemaInfo> = listOf(
        SchemaInfo(1, "notes table", "baseline"),
        SchemaInfo(2, "notes.pinned column", "AutoMigration"),
        SchemaInfo(3, "notes.updated_at + rename title", "manual Migration"),
    )
}
