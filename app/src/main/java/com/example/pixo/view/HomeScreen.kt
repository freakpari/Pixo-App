package com.example.pixo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pixo.model.GalleryItem
import com.example.pixo.ui.theme.Primary12
import com.example.pixo.viewmodel.HomeViewModel
import com.example.pixo.ui.theme.Primary7
import com.example.pixo.ui.theme.Primary8
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import com.example.pixo.R
import com.example.pixo.ui.theme.Gray
import com.example.pixo.ui.theme.Primary10
import com.example.pixo.ui.theme.Secondary4
import com.example.pixo.ui.theme.White

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val galleryItems by viewModel.galleryItems.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenWidth = maxWidth
        val scale = screenWidth / 390.dp

        Scaffold(
            topBar = { CustomStatusBar() },
            containerColor = Color.White,
            bottomBar = { AppBottomNavigation() }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                HeaderSection(
                    query = searchQuery,
                    onQueryChanged = { viewModel.onSearchQueryChanged(it) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                HomeContent(
                    galleryItems = galleryItems,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
@Composable
private fun BottomNavItem(
    iconRes: Int,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = painterResource(iconRes),
            contentDescription = title,
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = title,
            color = White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
@Composable
private fun AppBottomNavigation() {

    var selectedItem by remember {
        mutableStateOf(1)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .background(Primary8)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 12.dp,
                    bottom = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            BottomNavItem(
                iconRes = R.drawable.ic_account,
                title = "Account",
                selected = selectedItem == 0,
                onClick = { selectedItem = 0 }
            )
            BottomNavItem(
                iconRes = R.drawable.ic_home,
                title = "Home",
                selected = selectedItem == 1,
                onClick = { selectedItem = 1 }
            )

            BottomNavItem(
                iconRes = R.drawable.ic_notification,
                title = "Notification",
                selected = selectedItem == 2,
                onClick = { selectedItem = 2 }
            )

        }
    }
}
@Composable
private fun CustomStatusBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(Primary7)
    ) {
        HorizontalDivider(
            modifier = Modifier.align(Alignment.BottomCenter),
            thickness = 1.dp,
            color = Primary8
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

        TextField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier
                .weight(1f)
                .height(45.dp)
                .border(1.dp, Primary8, RoundedCornerShape(24.dp)),
            placeholder = {
                Text(
                    "Search....",
                    color = Gray,
                    fontSize = 14.sp
                )
            },
            leadingIcon = {
                Icon(Icons.Default.Search, null, tint = Primary8)
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Primary12,
                unfocusedContainerColor = Primary12,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Primary8
            )
        )
        )
    }
}

@Composable
fun HomeContent(
    galleryItems: List<GalleryItem>,
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
            items(
                count = Int.MAX_VALUE
            ) { index ->

                val item = galleryItems[index % galleryItems.size]

                WallpaperItem(item)

            }
        }
    }

}
@Composable
fun WallpaperItem(
    item: GalleryItem
) {

    Column {

        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                painter = painterResource(item.imageRes),
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

