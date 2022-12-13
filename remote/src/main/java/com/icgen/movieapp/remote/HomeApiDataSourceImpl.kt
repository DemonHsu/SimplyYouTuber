package com.icgen.movieapp.remote

import android.util.Log
import com.example.movieapp.core.common.Secrets
import com.icgen.movieapp.data.model.MoviesData
import com.icgen.movieapp.data.source.home.HomeApiDataSource
import com.icgen.movieapp.remote.mapper.toDataModel
import com.icgen.movieapp.remote.service.ApiService
import javax.inject.Inject

class HomeApiDataSourceImpl @Inject constructor(
    private val service: ApiService
) : HomeApiDataSource {

    private val channelId = "UCn7dB9UMTBDjKtEKBy_XISw" //"UC0C-w0YjGpqDXGB8IHb662A"
    private var playlistId = ""
    private var ownerPosterPath = ""
    private val key = Secrets().getQBjQeGcZ("com.example.movieapp")

    override suspend fun getVideos(): MoviesData {
        val channels = service.getChannels("snippet,contentDetails", channelId, key)
        playlistId = channels.items[0].contentDetails.relatedPlaylists.uploads
        ownerPosterPath = channels.items[0].snippet.thumbnails.high.url
        val videos = service.getPlaylist("snippet,contentDetails,status", playlistId, key, 20)

        val dataModels = videos.toDataModel(ownerPosterPath)

        dataModels.list.map {
            it.overview = service.getVideo(part = "snippet", id = it.id, key = key).items[0].snippet.description
        }

        return dataModels
    }

    override suspend fun getVideos(pageToken:String): MoviesData {
        val result = service.getPlaylist("snippet,contentDetails,status", playlistId, key, 30 , pageToken)
        val dataModels = result.toDataModel(ownerPosterPath)

        dataModels.list.map {
            it.overview = service.getVideo(part = "snippet", id = it.id, key = key).items[0].snippet.description
        }

        return dataModels
    }
}
