package com.example.pixo.data.remote

import com.example.pixo.model.NotificationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val message: String?, val token: String?)
data class SignupRequest(val email: String, val password: String)

interface ApiService {
    @POST("api/v1/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/v1/signup")
    suspend fun signupUser(@Body request: SignupRequest): Response<LoginResponse>

    @GET("api/v1/getAllNotif")
    suspend fun getAllNotifications(): List<NotificationResponse>
}