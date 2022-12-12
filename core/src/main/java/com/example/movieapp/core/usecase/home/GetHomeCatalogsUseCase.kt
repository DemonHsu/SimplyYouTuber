package com.example.movieapp.core.usecase.home

import com.example.movieapp.core.common.Dispatcher
import com.example.movieapp.core.repository.HomeRepository
import com.example.movieapp.core.usecase.base.BaseSuspendableUseCase
import com.icgen.movieapp.core.model.Movie
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetHomeCatalogsUseCase @Inject constructor(
    private val repository: HomeRepository,
    private val dispatcher: Dispatcher
) : BaseSuspendableUseCase<Unit, GetHomeCatalogsUseCase.Output>() {

    override suspend fun execute(input: Unit?): Output = coroutineScope {
        val popularMovies = async() {
            repository.getVideos()
        }

        Output(
            popularMovies.await()
        )
    }

    data class Output(
        val popularMovies: List<Movie>
    )
}
