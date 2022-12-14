package com.icgen.movieapp.data.source.detail

import com.icgen.movieapp.data.model.CastData
import com.icgen.movieapp.data.model.DetailData
import com.icgen.movieapp.data.model.MovieData
import com.icgen.movieapp.data.model.VideoData

interface DetailApiDataSource {
    suspend fun getMovieDetail(videoId: String): DetailData
}
