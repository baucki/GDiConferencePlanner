package com.gdi.conferenceplanner.data.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "speakers")
data class Speaker(
    val imageUrl: String,
    val name: String,
    val title: String,
    val biography: String,
    @PrimaryKey
    val speakerId: Long
)
