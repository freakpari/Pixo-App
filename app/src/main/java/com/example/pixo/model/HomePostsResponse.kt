package com.example.pixo.model

data class HomePostsResponse(
    val message: String,
    val posts: List<HomePost>
)

data class HomePost(
    val id: Int,
    val title: String,
    val image_url: String,
    val description: String,
    val created_at: String
)