package com.arthurabreu.commonscreens.ui.state.olympics

data class OlympicsState(
    val participation: OlympicsParticipation = OlympicsParticipation(),
)

data class OlympicsParticipation(
    val id: String = "",
    val name: String = "",
    val country: String = "",
    val sport: String = "",
    val medal: String = ""
)
