package com.example.movieapp.presentation.common

object ListHelper {
    private const val GENRE_LIMIT = 3
    private const val GENRE_SEPARATOR = " | "

    fun <T> List<T>.sliceOrGet(index: Int): List<T> =
        when {
            (size > index) -> this.slice(0..index)
            else -> this
        }

}
