package com.example.movieapp.detail.redux

import com.example.movieapp.core.redux.State
import com.example.movieapp.presentation.model.DetailUI


sealed class DetailState : State {
    object Idle : DetailState()
    object GetMovieInfoStarted : DetailState()
    data class MovieInfoError(val message: String?) : DetailState()

    data class MovieInfoLoaded(
        val detail: DetailUI
    ) : DetailState()
}
