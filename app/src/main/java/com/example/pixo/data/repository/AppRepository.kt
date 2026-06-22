package com.example.pixo.data.repository

import com.example.pixo.data.local.NotificationDao
import com.example.pixo.data.local.NotificationEntity
import com.example.pixo.data.local.PostDao
import com.example.pixo.data.remote.ApiService
import com.example.pixo.data.remote.LoginRequest
import com.example.pixo.data.remote.LoginResponse
import com.example.pixo.data.remote.SignupRequest
import com.example.pixo.model.HomePostsResponse
import com.example.pixo.model.PostResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class AppRepository(
    private val apiService: ApiService,
    private val notificationDao: NotificationDao,
    private val postDao: PostDao
) {
    val allNotifications: Flow<List<NotificationEntity>> = notificationDao.getAllNotifications()

    val singlePost: Flow<PostResponse?> = postDao.getPost()

    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return apiService.loginUser(request)
    }

    suspend fun signup(request: SignupRequest): Response<LoginResponse> {
        return apiService.signupUser(request)
    }

    suspend fun refreshNotifications() {
        try {
            val responseList = apiService.getAllNotifications()
            val entities = responseList.map {
                NotificationEntity(
                    id = it.id,
                    senderName = it.senderName,
                    profileImage = it.profileImage,
                    date = it.date,
                    preview = it.preview
                )
            }
            notificationDao.clearNotifications()
            notificationDao.insertNotifications(entities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun refreshSinglePost() {
        try {
            val response = apiService.getSinglePost()
            if (response.isSuccessful) {
                response.body()?.let { post ->
                    postDao.clearPosts()
                    postDao.insertPost(post)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    suspend fun getAllPosts(): Response<HomePostsResponse> {
        return apiService.getAllPosts()
    }
}