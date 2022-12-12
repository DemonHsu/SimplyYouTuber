package com.icgen.movieapp.remote

import com.icgen.movieapp.data.model.*
import com.icgen.movieapp.data.source.detail.DetailApiDataSource
import com.icgen.movieapp.remote.service.ApiService
import javax.inject.Inject

class DetailApiDataSourceImpl @Inject constructor(
    private val service: ApiService
) : DetailApiDataSource {

    override suspend fun getMovieDetail(id: Int): DetailData {
        return DetailData(0, "", "", listOf(), "",0.0,0.0,0, "",0,"")
    }

}
