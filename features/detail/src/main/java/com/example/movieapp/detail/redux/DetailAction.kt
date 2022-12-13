package com.example.movieapp.detail.redux

import com.example.movieapp.core.redux.Action
import com.example.movieapp.presentation.model.DetailUI

sealed class DetailAction : Action {
    object Idle : DetailAction()
    data class GetMovieInfo(val movieId: String) : DetailAction()
    data class GetMovieInfoError(val message: String?) : DetailAction()

    data class GetMovieInfoLoaded(
        val detail: DetailUI,
    ) : DetailAction()
}
