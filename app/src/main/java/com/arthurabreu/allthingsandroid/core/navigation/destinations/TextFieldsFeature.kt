package com.arthurabreu.allthingsandroid.core.navigation.destinations

object TextFieldsFeature {
    data object TextFields : NoParamsDestination("text_fields_example") {
        override fun toString(): String {
            return "TextFieldsExample"
        }
    }
}