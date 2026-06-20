package com.example.pixo.viewmodel
import androidx.lifecycle.ViewModel
import com.example.pixo.R
import com.example.pixo.model.GalleryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
class HomeViewModel : ViewModel() {

    private val _galleryItems = MutableStateFlow<List<GalleryItem>>(emptyList())
    val galleryItems: StateFlow<List<GalleryItem>> = _galleryItems.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadHomeData()
    }
    private fun loadHomeData() {
        _galleryItems.value = listOf(
            GalleryItem("Galaxy & Stars", R.drawable.galaxy_stars),
            GalleryItem("Angel", R.drawable.angel,),
            GalleryItem("Painter Girl", R.drawable.painters_girl ),
            GalleryItem("Colorful Butterflies in forest", R.drawable.butterflies),
            GalleryItem("Galaxy", R.drawable.galaxy),
            GalleryItem("Painter's hand", R.drawable.painters_hand),
        )
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}