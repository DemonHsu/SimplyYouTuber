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
    private var currentToken = ""

    override suspend fun getVideos(): List<Movie> {

        if (pageToken.isEmpty()){
            val result = remoteSource.getVideos()
            result.nextPageToken?.let {
              pageToken = it
            }
            result.list.map { movieData -> list.add(movieData.toCoreModel()) }
        }else{

            if( currentToken != pageToken ){
                currentToken = pageToken
                val result = remoteSource.getVideos(pageToken)

                result.nextPageToken?.let {
                    pageToken = it
                }

                result.list.map { movieData -> list.add(movieData.toCoreModel()) }
            }
        }
        return list
    }
}
