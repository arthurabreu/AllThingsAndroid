package com.arthurabreu.allthingsandroid.feature.maps.data

import com.arthurabreu.allthingsandroid.feature.maps.domain.model.MapPoi
import com.arthurabreu.allthingsandroid.feature.maps.domain.model.PoiCategory
import com.google.android.gms.maps.model.LatLng

// Static sample POIs — in a real app these would come from an API/DB.
object PoiRepository {
    fun getSamplePois(): List<MapPoi> = listOf(
        MapPoi("1", "Maracanã Stadium", "Iconic Olympic venue", LatLng(-22.9122, -43.2302), PoiCategory.STADIUM),
        MapPoi("2", "Christ the Redeemer", "World-famous landmark", LatLng(-22.9519, -43.2105), PoiCategory.LANDMARK),
        MapPoi("3", "Tijuca National Park", "Urban rainforest", LatLng(-22.9318, -43.2770), PoiCategory.PARK),
        MapPoi("4", "Rio–Niteroi Bridge", "Major transport link", LatLng(-22.8977, -43.1792), PoiCategory.TRANSPORT),
        MapPoi("5", "Copacabana Palace", "Historic beachfront hotel", LatLng(-22.9678, -43.1767), PoiCategory.LANDMARK),
        MapPoi("6", "Deodoro Olympic Park", "2016 Olympics venue cluster", LatLng(-22.8588, -43.3741), PoiCategory.STADIUM),
    )
}
