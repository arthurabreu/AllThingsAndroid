package com.arthurabreu.allthingsandroid.feature.maps

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class MapsViewModelTest {
    @Test
    fun polylineFlag() {
        val vm = MapsViewModel()
        vm.selectRoute()
        assertTrue(vm.state.value.showRoute)
    }
}

