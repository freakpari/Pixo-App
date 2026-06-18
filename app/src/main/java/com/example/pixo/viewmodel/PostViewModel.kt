package com.example.pixo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixo.data.local.AppDatabase
import com.example.pixo.data.remote.RetrofitClient
import com.example.pixo.data.repository.AppRepository
import com.example.pixo.model.PostResponse
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AppRepository
    val postState: StateFlow<PostResponse?>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = AppRepository(
            apiService = RetrofitClient.instance,
            notificationDao = database.notificationDao(),
            postDao = database.postDao()
        )

        postState = repository.singlePost.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

        refreshPost()
    }

    fun refreshPost() {
        viewModelScope.launch {
            repository.refreshSinglePost()
        }
    }
}