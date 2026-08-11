package com.arthurabreu.allthingsandroid.feature.feedback

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class FeedbackViewModelTest {
    @Test
    fun rejectsLowScoreWithoutComment() {
        val vm = FeedbackViewModel()
        vm.setScore(1)
        vm.submit()
        assertEquals("Low scores need a comment", vm.state.value.message)
    }

    @Test
    fun queuesValidFeedback() {
        val vm = FeedbackViewModel()
        vm.setScore(4)
        vm.setComment("Cold chain ok")
        vm.attachPhoto()
        vm.submit()
        assertEquals(1, vm.state.value.drafts.size)
        assertTrue(vm.state.value.message!!.contains("Queued"))
    }
}

