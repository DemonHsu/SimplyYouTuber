package com.icgen.movieapp.remote

import com.example.movieapp.core.common.Secrets
import com.icgen.movieapp.data.model.*
import com.icgen.movieapp.data.source.detail.DetailApiDataSource
import com.icgen.movieapp.remote.mapper.toDataModel
import com.icgen.movieapp.remote.service.ApiService
import javax.inject.Inject

class DetailApiDataSourceImpl @Inject constructor(
    private val service: ApiService
) : DetailApiDataSource {

    private val key = Secrets().getQBjQeGcZ("com.example.movieapp")

    override suspend fun getMovieDetail(videoId: String): DetailData {

        val result = service.getVideo("snippet", videoId, key)
        return result.toDataModel()
    }

}
