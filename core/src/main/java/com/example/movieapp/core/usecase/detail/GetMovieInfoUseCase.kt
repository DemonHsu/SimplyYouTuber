package com.example.movieapp.core.usecase.detail

import com.example.movieapp.core.common.Dispatcher
import com.example.movieapp.core.model.Cast
import com.example.movieapp.core.model.Detail
import com.example.movieapp.core.model.Video
import com.example.movieapp.core.repository.DetailRepository
import com.example.movieapp.core.usecase.base.BaseSuspendableUseCase
import com.icgen.movieapp.core.model.Movie
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetMovieInfoUseCase @Inject constructor(
    private val repository: DetailRepository,
    private val dispatcher: Dispatcher
) : BaseSuspendableUseCase<String, GetMovieInfoUseCase.Output>() {

    override suspend fun execute(input: String?): Output = coroutineScope {
        requireNotNull(input)

        val detail = async() {
            repository.getMovieDetail(input)
        }

        Output(
            detail.await(),
        )
    }

    data class Output(
        val detail: Detail,
    )
}
