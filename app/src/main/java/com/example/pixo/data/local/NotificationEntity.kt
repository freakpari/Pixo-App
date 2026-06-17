package com.example.pixo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: Int,
    val senderName: String,
    val profileImage: String,
    val date: String,
    val preview: String
)