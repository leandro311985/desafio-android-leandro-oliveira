package com.example.desafio_android_leandro_oliveira.model.response.marvel.hero

import com.google.gson.annotations.SerializedName

data class MarvelHeroData(
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<MarvelHero>
)