package com.example.pixo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostResponse(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val author: String,
    val likes: Int,
    val comments: Int,
    val category: List<String>,
    val tags: List<String>,
    val createdAt: String,
    val imageUrl: String
)