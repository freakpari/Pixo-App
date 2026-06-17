package com.example.pixo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pixo.data.remote.LoginRequest
import com.example.pixo.data.remote.LoginResponse
import com.example.pixo.data.remote.SignupRequest
import com.example.pixo.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(private val repository: AppRepository) : ViewModel() {

    private val _loginResult = MutableSharedFlow<Response<LoginResponse>?>()
    val loginResult: SharedFlow<Response<LoginResponse>?> = _loginResult

    private val _signupResult = MutableSharedFlow<Response<LoginResponse>?>()
    val signupResult: SharedFlow<Response<LoginResponse>?> = _signupResult

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: SharedFlow<String> = _errorFlow

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            try {
                val response = repository.login(request)
                _loginResult.emit(response)
            } catch (e: Exception) {
                _errorFlow.emit(e.localizedMessage ?: "Network Error")
            }
        }
    }

    fun signup(request: SignupRequest) {
        viewModelScope.launch {
            try {
                val response = repository.signup(request)
                _signupResult.emit(response)
            } catch (e: Exception) {
                _errorFlow.emit(e.localizedMessage ?: "Network Error")
            }
        }
    }
}

class AuthViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}