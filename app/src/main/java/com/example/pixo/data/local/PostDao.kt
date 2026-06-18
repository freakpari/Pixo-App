package com.example.pixo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pixo.model.PostResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts LIMIT 1")
    fun getPost(): Flow<PostResponse?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostResponse)

    @Query("DELETE FROM posts")
    suspend fun clearPosts()
}