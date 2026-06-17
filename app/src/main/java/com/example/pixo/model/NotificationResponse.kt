package com.example.pixo.model

data class NotificationResponse(
    val id: Int,
    val senderName: String,
    val profileImage: String,
    val date: String,
    val preview: String
)