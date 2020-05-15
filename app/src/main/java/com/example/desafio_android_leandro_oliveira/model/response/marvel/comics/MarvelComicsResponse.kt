package com.example.desafio_android_leandro_oliveira.model.response.marvel.comics

import com.example.desafio_android_leandro_oliveira.model.response.marvel.common.MarvelCommonResponse
import com.google.gson.annotations.SerializedName

data class MarvelComicsResponse(
    @SerializedName("data") val heroData: MarvelComicsData
): MarvelCommonResponse()