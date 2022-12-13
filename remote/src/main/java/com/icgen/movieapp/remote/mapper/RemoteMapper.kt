package com.icgen.movieapp.remote.mapper

import com.icgen.movieapp.data.model.DetailData
import com.icgen.movieapp.data.model.MovieData
import com.icgen.movieapp.data.model.MoviesData
import com.icgen.movieapp.remote.dto.*

fun PlayListDto.toDataModel(ownerPosterPath: String) = MoviesData( nextPageToken, items.map {
    MovieData(it.snippet.resourceId.videoId, it.snippet.title,it.snippet.thumbnails.high.url ?: "", it.snippet.channelTitle, ownerPosterPath, it.snippet.publishedAt)}
)

fun VideoDto.toDataModel() = DetailData(id = items[0].id, title = items[0].snippet.title, overview = items[0].snippet.description, releaseDate = items[0].snippet.publishedAt)