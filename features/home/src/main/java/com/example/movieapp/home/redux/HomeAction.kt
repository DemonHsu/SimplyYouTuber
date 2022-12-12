package com.example.movieapp.home.redux

import com.example.movieapp.core.redux.Action
import com.example.movieapp.presentation.model.MovieUI

sealed class HomeAction : Action {
    object Idle : HomeAction()
    object GetHomeCatalogs : HomeAction()
    data class GetHomeCatalogsError(val message: String?) : HomeAction()

    data class GetHomeCatalogsLoaded(
        val movies: List<MovieUI>
    ) : HomeAction()

    object GetCatalogsNextPage : HomeAction()
    data class GetCatalogsNextPageLoaded(
        val movies: List<MovieUI>
    ) : HomeAction()
}
