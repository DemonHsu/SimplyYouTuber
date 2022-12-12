package com.icgen.movieapp.remote.mapper

import com.icgen.movieapp.data.model.MovieData
import com.icgen.movieapp.data.model.MoviesData
import com.icgen.movieapp.remote.dto.*

fun VideosDto.toDataModel(ownerPosterPath: String) = MoviesData( nextPageToken, items.map {
    MovieData(it.snippet.resourceId.videoId, it.snippet.title,it.snippet.thumbnails.high.url ?: "", it.snippet.channelTitle, ownerPosterPath, it.snippet.publishedAt)}
)
