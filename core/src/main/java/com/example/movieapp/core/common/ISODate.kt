package com.example.movieapp.core.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object ISODate {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDate(dateString: String?): LocalDateTime? {
        val isoFormatter = DateTimeFormatter.ISO_INSTANT
        val dateInstant = Instant.from(isoFormatter.parse(dateString))
        return LocalDateTime.ofInstant(dateInstant, ZoneId.of(ZoneOffset.UTC.id))
    }
}