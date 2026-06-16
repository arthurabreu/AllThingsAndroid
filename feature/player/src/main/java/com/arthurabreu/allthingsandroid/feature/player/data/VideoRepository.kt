package com.arthurabreu.allthingsandroid.feature.player.data

import com.arthurabreu.allthingsandroid.feature.player.domain.model.VideoItem

// Static HLS catalog — public test streams, no auth needed.
object VideoRepository {
    fun getCatalog(): List<VideoItem> = listOf(
        VideoItem(
            id = "1",
            title = "Big Buck Bunny",
            description = "Classic Blender Foundation short film — HLS stream",
            uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
        ),
        VideoItem(
            id = "2",
            title = "Elephant Dream",
            description = "First Blender open-movie project",
            uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg",
        ),
        VideoItem(
            id = "3",
            title = "For Bigger Blazes",
            description = "Sample MP4 from Google",
            uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg",
        ),
    )
}
