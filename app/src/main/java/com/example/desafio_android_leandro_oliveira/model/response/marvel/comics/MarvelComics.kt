package com.example.desafio_android_leandro_oliveira.model.response.marvel.comics

import com.example.desafio_android_leandro_oliveira.model.response.marvel.common.MarvelThumbnail
import com.google.gson.annotations.SerializedName

data class MarvelComics(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String ?,
    @SerializedName("description") val description: String ?,
    @SerializedName("thumbnail") val thumbnail: MarvelThumbnail
)