package com.arthurabreu.allthingsandroid.core.domain

import com.arthurabreu.allthingsandroid.core.common.AppResult
import com.arthurabreu.allthingsandroid.core.model.FeedbackDraft

class FeedbackRules {
    fun validate(score: Int, comment: String): AppResult<Unit> {
        if (score !in 1..5) return AppResult.Err("Score must be 1 to 5")
        if (comment.isBlank() && score <= 2) return AppResult.Err("Low scores need a comment")
        return AppResult.Ok(Unit)
    }

    fun pending(drafts: List<FeedbackDraft>): List<FeedbackDraft> = drafts.filterNot { it.synced }
}
