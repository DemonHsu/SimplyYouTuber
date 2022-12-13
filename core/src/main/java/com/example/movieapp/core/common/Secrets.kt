package com.example.movieapp.core.common

class Secrets {

    // Method calls will be added by gradle task hideSecret
    companion object {
        init {
            System.loadLibrary("secrets")
        }
    }

    external fun getQBjQeGcZ(packageName: String): String
}