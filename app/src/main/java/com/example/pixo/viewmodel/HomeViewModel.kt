package com.example.pixo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixo.data.local.AppDatabase
import com.example.pixo.data.remote.RetrofitClient
import com.example.pixo.data.repository.AppRepository
import com.example.pixo.model.HomePost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository

    private val _allPosts = MutableStateFlow<List<HomePost>>(emptyList())
    private val _galleryItems = MutableStateFlow<List<HomePost>>(emptyList())
    val galleryItems: StateFlow<List<HomePost>> = _galleryItems.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        val db = AppDatabase.getDatabase(application)
        repository = AppRepository(
            apiService = RetrofitClient.instance,
            notificationDao = db.notificationDao(),
            postDao = db.postDao()
        )
        fetchHomePosts()
    }

    private fun fetchHomePosts() {
        viewModelScope.launch {
            try {
                val response = repository.getAllPosts()
                if (response.isSuccessful) {
                    val posts = response.body()?.posts ?: emptyList()
                    _allPosts.value = posts
                    _galleryItems.value = posts
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        if (query.isEmpty()) {
            _galleryItems.value = _allPosts.value
        } else {
            _galleryItems.value = _allPosts.value.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
    }
}