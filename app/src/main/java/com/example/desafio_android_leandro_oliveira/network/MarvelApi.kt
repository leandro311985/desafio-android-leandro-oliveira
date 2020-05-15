package com.example.desafio_android_leandro_oliveira.network

import com.example.desafio_android_leandro_oliveira.commons.Definitions
import com.example.desafio_android_leandro_oliveira.commons.Definitions.PARAM_CHARACTER_ID
import com.example.desafio_android_leandro_oliveira.model.response.marvel.comics.MarvelComicsResponse
import com.example.desafio_android_leandro_oliveira.model.response.marvel.hero.MarvelHeroResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    fun getMarvelHeroesAsync(@Query(Definitions.PARAM_LIMIT) limit: Int, @Query(Definitions.PARAM_OFFSET) offset: Int): Deferred<MarvelHeroResponse>

    @GET("characters/{$PARAM_CHARACTER_ID}/comics")
    fun getMarvelComicsAsync(@Path(PARAM_CHARACTER_ID) heroId: Int): Deferred<MarvelComicsResponse>

}