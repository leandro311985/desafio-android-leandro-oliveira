package com.example.desafio_android_leandro_oliveira.repository.heroDetails

import com.example.desafio_android_leandro_oliveira.model.MarvelHeroesModel
import com.example.desafio_android_leandro_oliveira.model.MarvelListModel
import com.example.desafio_android_leandro_oliveira.network.MarvelClient
import com.example.desafio_android_leandro_oliveira.repository.base.BaseRepository

class HeroDetailsRepository(private val marvelClient: MarvelClient?) : BaseRepository() {

    suspend fun fetchComics(heroId: Int): MarvelListModel? {
        val response = try {
            marvelClient?.getMarvelComicsAsync(heroId)?.await()
        } catch (e: Exception) {
            return null
        }

        val marvelModelList = response?.heroData?.results?.map { marvelHero ->
            return@map MarvelHeroesModel(
                marvelHero.id,
                marvelHero.title ?: "",
                marvelHero.description ?: "",
                marvelHero.thumbnail.path + "." + marvelHero.thumbnail.extension)
        }
        return marvelModelList?.let { MarvelListModel(it) }
    }
}