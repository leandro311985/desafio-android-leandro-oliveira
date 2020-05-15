package com.example.desafio_android_leandro_oliveira.model.response.marvel.hero

import com.example.desafio_android_leandro_oliveira.model.response.marvel.common.MarvelThumbnail
import com.google.gson.annotations.SerializedName


data class MarvelHero(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnail") val thumbnail: MarvelThumbnail
)