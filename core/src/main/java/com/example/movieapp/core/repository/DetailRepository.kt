package com.example.movieapp.core.repository


import com.example.movieapp.core.model.Detail

interface DetailRepository {
    suspend fun getMovieDetail(id: Int): Detail
}
