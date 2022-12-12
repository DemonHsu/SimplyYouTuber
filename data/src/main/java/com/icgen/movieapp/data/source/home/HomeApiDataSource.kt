package com.icgen.movieapp.data.source.home

import com.icgen.movieapp.data.model.MoviesData

interface HomeApiDataSource {
    suspend fun getVideos(): MoviesData
    suspend fun getVideos(pageToken:String): MoviesData
}
