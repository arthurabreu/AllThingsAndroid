package com.arthurabreu.allthingsandroid.feature.maps.domain.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MapPoi(
    val id: String,
    val name: String,
    val description: String,
    val latLng: LatLng,
    val category: PoiCategory,
) : ClusterItem {
    override fun getPosition(): LatLng = latLng
    override fun getTitle(): String = name
    override fun getSnippet(): String = description
    override fun getZIndex(): Float = 0f
}

enum class PoiCategory { LANDMARK, PARK, STADIUM, TRANSPORT }
