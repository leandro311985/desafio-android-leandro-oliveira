package com.example.desafio_android_leandro_oliveira.model.response.marvel.common

import com.google.gson.annotations.SerializedName

data class MarvelThumbnail(
    @SerializedName("path") val path: String,
    @SerializedName("extension") val extension: String
)