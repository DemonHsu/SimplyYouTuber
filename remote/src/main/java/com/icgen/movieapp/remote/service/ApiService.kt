package com.icgen.movieapp.remote.service

import com.icgen.movieapp.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlistItems")
    suspend fun getPlaylist(
        @Query("part") part: String?,
        @Query("playlistId") playlistId: String?,
        @Query("key") key: String?,
        @Query("maxResults") resultNumbers: Int,
        @Query("pageToken") pageToken: String? = null
    ): PlayListDto

    @GET("channels")
    suspend fun getChannels(
        @Query("part") part: String?,
        @Query("id") id: String?,
        @Query("key") key: String?,
    ): ChannelsDto

    @GET("videos")
    suspend fun getVideo(
        @Query("part") part: String?,
        @Query("id") id: String?,
        @Query("key") key: String?,
    ): VideoDto
}
