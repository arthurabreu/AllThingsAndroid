package com.arthurabreu.allthingsandroid.core.navigation.destinations

object ApiShowcaseFeature {
    data object JsonPlaceHolder : NoParamsDestination("jsonplaceholder_typicode_example") {
        override fun toString(): String {
            return "JsonPlaceHolderExample"
        }
    }
}