package com.icgen.movieapp.data

import com.example.movieapp.core.model.Detail
import com.example.movieapp.core.repository.DetailRepository
import com.icgen.movieapp.data.mapper.toCoreModel
import com.icgen.movieapp.data.source.detail.DetailApiDataSource
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val apiDataSource: DetailApiDataSource
) : DetailRepository {

    override suspend fun getMovieDetail(id: Int): Detail {
        return apiDataSource.getMovieDetail(id)
            .toCoreModel()
    }
}
