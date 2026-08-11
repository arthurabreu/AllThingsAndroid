package com.arthurabreu.allthingsandroid.feature.feedback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import com.arthurabreu.allthingsandroid.core.common.AppResult
import com.arthurabreu.allthingsandroid.core.domain.FeedbackRules
import com.arthurabreu.allthingsandroid.core.model.FeedbackDraft

data class FeedbackState(
    val score: Int = 0,
    val comment: String = "",
    val photoUri: String? = null,
    val drafts: List<FeedbackDraft> = emptyList(),
    val message: String? = null,
)

class FeedbackViewModel(
    private val rules: FeedbackRules = FeedbackRules(),
) : ViewModel() {
    private val _state = MutableStateFlow(FeedbackState())
    val state: StateFlow<FeedbackState> = _state.asStateFlow()
    private var seq = 1

    fun setScore(value: Int) { _state.update { it.copy(score = value, message = null) } }
    fun setComment(value: String) { _state.update { it.copy(comment = value) } }
    fun attachPhoto() { _state.update { it.copy(photoUri = "content://demo/photo.jpg") } }

    fun submit() {
        when (val result = rules.validate(_state.value.score, _state.value.comment)) {
            is AppResult.Err -> _state.update { it.copy(message = result.message) }
            is AppResult.Ok -> {
                val draft = FeedbackDraft(
                    id = "fb-$seq",
                    score = _state.value.score,
                    comment = _state.value.comment,
                    photoUri = _state.value.photoUri,
                    synced = false,
                )
                seq++
                _state.update {
                    it.copy(
                        drafts = it.drafts + draft,
                        score = 0,
                        comment = "",
                        photoUri = null,
                        message = "Queued for WorkManager sync",
                    )
                }
            }
        }
    }
}


@Composable
fun FeedbackScreen(viewModel: FeedbackViewModel, onBack: () -> Unit = {}) {
    val state by viewModel.state.collectAsState()
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).testTag("feedback-screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Feedback", style = MaterialTheme.typography.headlineSmall)

        Text("CSAT / field feedback (generic — no client IP)")
        state.message?.let { Text(it, modifier = Modifier.testTag("feedback-message")) }
        listOf(1, 2, 3, 4, 5).forEach { score ->
            Button(onClick = { viewModel.setScore(score) }, modifier = Modifier.testTag("score-$score")) {
                Text("$score")
            }
        }
        androidx.compose.material3.OutlinedTextField(
            value = state.comment,
            onValueChange = viewModel::setComment,
            label = { Text("Comment") },
            modifier = Modifier.testTag("feedback-comment"),
        )
        Button(onClick = viewModel::attachPhoto) { Text("Attach photo (demo)") }
        Button(onClick = viewModel::submit, modifier = Modifier.testTag("feedback-submit")) { Text("Queue sync") }
        state.drafts.forEach { Text("#${it.score} ${it.comment} synced=${it.synced}") }

        Button(onClick = onBack) { Text("Back") }
    }
}
