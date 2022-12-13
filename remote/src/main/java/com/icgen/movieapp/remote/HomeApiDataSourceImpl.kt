package com.icgen.movieapp.remote

import com.example.movieapp.core.common.Secrets
import com.icgen.movieapp.data.model.MoviesData
import com.icgen.movieapp.data.source.home.HomeApiDataSource
import com.icgen.movieapp.remote.mapper.toDataModel
import com.icgen.movieapp.remote.service.ApiService
import javax.inject.Inject

class HomeApiDataSourceImpl @Inject constructor(
    private val service: ApiService
) : HomeApiDataSource {

    val channelId = "UC0C-w0YjGpqDXGB8IHb662A" //"UCn7dB9UMTBDjKtEKBy_XISw"//
    var playlistId = ""
    var ownerPosterPath = ""
    val key = Secrets().getQBjQeGcZ("com.example.movieapp")

    override suspend fun getVideos(): MoviesData {
        val channels = service.getChannels("snippet,contentDetails", channelId, key)
        playlistId = channels.items[0].contentDetails.relatedPlaylists.uploads
        ownerPosterPath = channels.items[0].snippet.thumbnails.high.url
        val videos = service.getVideos("snippet,contentDetails,status", playlistId, key, 20)
        return videos.toDataModel(ownerPosterPath)
    }

    override suspend fun getVideos(pageToken:String): MoviesData {
        val result = service.getVideos("snippet,contentDetails,status", playlistId, key, 30 , pageToken)
        return result.toDataModel(ownerPosterPath)
    }
}
