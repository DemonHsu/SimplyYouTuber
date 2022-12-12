package com.example.movieapp.home.redux

import com.example.movieapp.core.common.Dispatcher
import com.example.movieapp.core.redux.Middleware
import com.example.movieapp.core.redux.Store
import com.example.movieapp.core.usecase.home.GetHomeCatalogsUseCase
import com.example.movieapp.home.redux.HomeAction.GetHomeCatalogs
import com.example.movieapp.home.redux.HomeAction.GetHomeCatalogsError
import com.example.movieapp.home.redux.HomeAction.GetHomeCatalogsLoaded
import com.example.movieapp.home.redux.HomeAction.GetCatalogsNextPageLoaded
import com.example.movieapp.home.redux.HomeAction.GetCatalogsNextPage
import com.example.movieapp.presentation.mapper.toPresentationModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeMiddleware @Inject constructor(
    private val getHomeCatalogsUseCase: GetHomeCatalogsUseCase,
    private val dispatcher: Dispatcher
) : Middleware<HomeState, HomeAction> {

    override suspend fun dispatch(
        currentState: HomeState,
        action: HomeAction,
        store: Store<HomeState, HomeAction>
    ) {
        when (action) {
            is GetHomeCatalogs -> getHomeCatalogsSideEffect(store)
            is GetCatalogsNextPage -> getHomeCatalogsNextPageSideEffect(store)
            else -> return
        }
    }

    private suspend fun getHomeCatalogsSideEffect(
        store: Store<HomeState, HomeAction>
    ) {
        getHomeCatalogsUseCase().fold(
            { output -> onGetHomeCatalogsSuccess(output, store) },
            { exception -> onGetHomeCatalogsError(store, exception) }
        )
    }

    private suspend fun getHomeCatalogsNextPageSideEffect(
        store: Store<HomeState, HomeAction>
    ) {
        getHomeCatalogsUseCase().fold(
            { output -> onGetHomeCatalogsNextPageSuccess(output, store) },
            { exception -> onGetHomeCatalogsError(store, exception) }
        )
    }

    private suspend fun onGetHomeCatalogsError(
        store: Store<HomeState, HomeAction>,
        exception: Throwable
    ) {
        store.dispatch(GetHomeCatalogsError(exception.message))
    }

    private suspend fun onGetHomeCatalogsSuccess(
        output: GetHomeCatalogsUseCase.Output,
        store: Store<HomeState, HomeAction>
    ) {
        withContext(dispatcher.default) {
            output.apply {

                val popular = popularMovies.map {
                    it.toPresentationModel()
                }
                store.dispatch(
                    GetHomeCatalogsLoaded(
                        movies = popular,
                    )
                )
            }
        }
    }

    private suspend fun onGetHomeCatalogsNextPageSuccess(
        output: GetHomeCatalogsUseCase.Output,
        store: Store<HomeState, HomeAction>
    ) {
        withContext(dispatcher.default) {
            output.apply {

                val popular = popularMovies.map {
                    it.toPresentationModel()
                }
                store.dispatch(
                    GetCatalogsNextPageLoaded(
                        movies = popular,
                    )
                )
            }
        }
    }
}
