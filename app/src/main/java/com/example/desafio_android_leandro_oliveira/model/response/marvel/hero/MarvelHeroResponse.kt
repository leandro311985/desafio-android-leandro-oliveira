package com.example.desafio_android_leandro_oliveira.model.response.marvel.hero

import com.example.desafio_android_leandro_oliveira.model.response.marvel.common.MarvelCommonResponse
import com.google.gson.annotations.SerializedName

data class MarvelHeroResponse(@SerializedName("data") val heroData: MarvelHeroData): MarvelCommonResponse()