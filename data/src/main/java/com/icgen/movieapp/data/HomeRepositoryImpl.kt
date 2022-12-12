package com.icgen.movieapp.data

import com.example.movieapp.core.repository.HomeRepository
import com.icgen.movieapp.core.model.Movie
import com.icgen.movieapp.data.mapper.toCoreModel
import com.icgen.movieapp.data.source.home.HomeApiDataSource
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val remoteSource: HomeApiDataSource
) : HomeRepository {

    private val list =  mutableListOf<Movie>()
    private var pageToken = ""

    override suspend fun getVideos(): List<Movie> {

        if (pageToken.isEmpty()){
            val result = remoteSource.getVideos()
            pageToken = result.nextPageToken
            result.list.map { movieData -> list.add(movieData.toCoreModel()) }
        }else{
            val result = remoteSource.getVideos(pageToken)
            pageToken = result.nextPageToken
            result.list.map { movieData -> list.add(movieData.toCoreModel()) }
        }

        return list
    }
}
