package com.example.pixo

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val message: String?, val token: String?)
data class SignupRequest(val email: String, val password: String)

interface ApiService {
    @POST("api/v1/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
    @POST("api/v1/signup")
    suspend fun signupUser(@Body request: SignupRequest): Response<LoginResponse>
}

object RetrofitClient {
    private const val BASE_URL = "https://mock.apidog.com/m1/1309558-1309297-default/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}