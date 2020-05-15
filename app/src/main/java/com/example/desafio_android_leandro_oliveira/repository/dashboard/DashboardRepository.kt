package com.example.desafio_android_leandro_oliveira.repository.dashboard

import com.example.desafio_android_leandro_oliveira.database.MarvelDatabase
import com.example.desafio_android_leandro_oliveira.database.MarvelTable
import com.example.desafio_android_leandro_oliveira.model.MarvelHeroesModel
import com.example.desafio_android_leandro_oliveira.model.MarvelListModel
import com.example.desafio_android_leandro_oliveira.network.MarvelClient
import com.example.desafio_android_leandro_oliveira.repository.base.BaseRepository
import timber.log.Timber

class DashboardRepository(private val marvelClient: MarvelClient?, private val marvelDatabase: MarvelDatabase) : BaseRepository() {

    suspend fun fetchHeroes(offset: Int, limit: Int): MarvelListModel? {
        val response = try {
            marvelClient?.getMarvelHeroesAsync(limit, offset)?.await()
        } catch (e: Exception) {
            return null
        }

        val marvelModelList = response?.heroData?.results?.map { marvelHero ->
            marvelDatabase.marvelDao().insertHero(MarvelTable(marvelHero.id, false))

            return@map MarvelHeroesModel(
                marvelHero.id,
                marvelHero.name,
                marvelHero.description,
                marvelHero.thumbnail.path + "." + marvelHero.thumbnail.extension,
                marvelDatabase.marvelDao().isHeroFavotite(marvelHero.id)
            )
        }
        return marvelModelList?.let { MarvelListModel(it) }
    }

    fun updateFavourite(marvelHeroId: Int) {
        Timber.d("Hero Favorite Clicked %s", marvelHeroId)
        marvelDatabase.marvelDao().updateFavorite(marvelHeroId)
    }
}