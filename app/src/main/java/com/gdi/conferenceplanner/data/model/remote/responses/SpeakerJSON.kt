package com.gdi.conferenceplanner.data.model.remote.responses

data class SpeakerJSON (
    val imageUrl: String,
    val name: String,
    val title: String,
    val biography: String,
    val id: Long
)