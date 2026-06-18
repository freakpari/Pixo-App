package com.example.pixo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pixo.R
import com.example.pixo.ui.theme.*
import com.example.pixo.ui.viewmodel.PostViewModel

@Composable
fun PostDetailScreen(
    onBackClick: () -> Unit,
    viewModel: PostViewModel = viewModel()
) {
    val post by viewModel.postState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        if (post == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Primary8)
            }
        } else {
            val currentPost = post!!

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .aspectRatio(0.7f)
                    .clip(RoundedCornerShape(24.dp))
            ) {
                AsyncImage(
                    model = currentPost.imageUrl,
                    contentDescription = currentPost.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(36.dp)
                        .border(1.dp, Primary2, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.4f))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = "Likes",
                    tint = Primary8,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${currentPost.likes}",
                    color = Primary8,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    fontFamily = interFont
                )

                Spacer(modifier = Modifier.width(20.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_comment),
                    contentDescription = "Comments",
                    tint = Primary8,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${currentPost.comments}",
                    color = Primary8,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    fontFamily = interFont
                )

                Spacer(modifier = Modifier.width(20.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = "Share",
                    tint = Primary8,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { }
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = "Save",
                    tint = Primary8,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "Author Avatar",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = currentPost.author,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Primary9,
                    fontFamily = interFont
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(
                    text = currentPost.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Primary10,
                    fontFamily = interFont
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = currentPost.description,
                    fontSize = 12.sp,
                    lineHeight = 22.sp,
                    color = Primary9,
                    fontFamily = interFont
                )
            }

            Text(
                text = "More Similar",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Primary11,
                fontFamily = interFont,
                modifier = Modifier.padding(start = 24.dp, top = 20.dp, bottom = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.post_1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Image(
                    painter = painterResource(id = R.drawable.post_2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }
    }
}