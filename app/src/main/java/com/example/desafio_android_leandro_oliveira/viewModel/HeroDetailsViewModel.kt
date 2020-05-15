package com.example.desafio_android_leandro_oliveira.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.desafio_android_leandro_oliveira.model.MarvelHeroesModel
import com.example.desafio_android_leandro_oliveira.repository.heroDetails.HeroDetailsRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class HeroDetailsViewModel(
    private val dashboardRepository: HeroDetailsRepository?,
    var hero: MarvelHeroesModel?
) : BaseViewModelMarvel() {

    private lateinit var comics: MutableLiveData<List<MarvelHeroesModel>>

    fun getComics(): LiveData<List<MarvelHeroesModel>> {
        if (!::comics.isInitialized) {
            loadComics()
        }
        return comics
    }

    private fun loadComics() {
        comics = MutableLiveData()
        uiScope.launch {
            try {
                showLoading.value = true
                val response = withContext(bgDispatcher) {
                    dashboardRepository?.fetchComics(hero?.id ?: 0)
                }
                response?.let {
                    showError.value = false
                    comics.value = it.marvelHeroes.toList()
                } ?: run {
                    showError.value = true
                }
            } catch (e: Exception) {
                Timber.e(e.toString())
                showError.value = true
            } finally {
                showLoading.value = false
            }
        }
    }
}