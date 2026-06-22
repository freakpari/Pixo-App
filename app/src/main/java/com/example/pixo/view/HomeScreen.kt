package com.example.pixo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import com.example.pixo.model.HomePost
import com.example.pixo.ui.theme.Primary12
import com.example.pixo.viewmodel.HomeViewModel
import com.example.pixo.ui.theme.Primary7
import com.example.pixo.ui.theme.Primary8
import androidx.compose.foundation.border
import com.example.pixo.R
import com.example.pixo.ui.theme.Gray
import com.example.pixo.ui.theme.Primary10

@Composable
fun HomeScreen(
    onPostClick: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val galleryItems by viewModel.galleryItems.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HeaderSection(
            query = searchQuery,
            onQueryChanged = { viewModel.onSearchQueryChanged(it) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        HomeContent(
            galleryItems = galleryItems,
            onPostClick = onPostClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun HeaderSection(
    query: String,
    onQueryChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Pixo Logo",
            modifier = Modifier.size(43.dp, 50.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(12.dp))

        BasicTextField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier
                .weight(1f)
                .height(45.dp)
                .background(Primary12, RoundedCornerShape(24.dp))
                .border(1.dp, Primary8, RoundedCornerShape(24.dp))
                .padding(horizontal = 16.dp),
            singleLine = true,
            textStyle = TextStyle(
                color = Primary8,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Primary8,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        if (query.isEmpty()) {
                            Text(
                                text = "Search...",
                                color = Gray,
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}

@Composable
fun HomeContent(
    galleryItems: List<HomePost>,
    onPostClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Primary12,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 12.dp,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 16.dp
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            if (galleryItems.isNotEmpty()) {
                items(
                    count = Int.MAX_VALUE
                ) { index ->
                    val item = galleryItems[index % galleryItems.size]
                    WallpaperItem(
                        item = item,
                        onPostClick = onPostClick
                    )
                }
            }
        }
    }
}

@Composable
fun WallpaperItem(
    item: HomePost,
    onPostClick: () -> Unit
) {
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }

    Column(
        modifier = Modifier.clickable { onPostClick() }
    ) {
        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = item.image_url,
                imageLoader = imageLoader,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text(
            text = item.title,
            color = Primary10,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(
                top = 8.dp,
                start = 4.dp
            )
        )
    }
}