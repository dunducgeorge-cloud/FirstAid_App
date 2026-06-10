package com.proiect.firstaidapp

import androidx.room.*

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ORDER BY id DESC")
    suspend fun getAllPosts(): List<PostEntity>

    @Insert
    suspend fun insertPost(post: PostEntity)

    @Delete
    suspend fun deletePost(post: PostEntity)
}
