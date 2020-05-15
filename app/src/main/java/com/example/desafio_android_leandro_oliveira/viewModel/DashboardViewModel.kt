package com.example.desafio_android_leandro_oliveira.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.desafio_android_leandro_oliveira.model.MarvelHeroesModel
import com.example.desafio_android_leandro_oliveira.repository.dashboard.DashboardRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


class DashboardViewModel (private val dashboardRepository: DashboardRepository?): BaseViewModelMarvel() {

    private lateinit var heroes: MutableLiveData<List<MarvelHeroesModel>>
    private var heroesList = arrayListOf<MarvelHeroesModel>()
    private var limit = 20 // limite de itens por página
    private var offset = 0
    private var isFetching = false


    fun getHeroes(): LiveData<List<MarvelHeroesModel>> {
        if (!::heroes.isInitialized) {
            heroes = MutableLiveData()
            loadHeroes()
        }
        return heroes
    }

    fun loadHeroes() {
        if (isFetching()) {
            return
        }
        setFetching(true)

        uiScope.launch {
            try {
                if (offset == 0) {
                    showLoading.value = true
                }
                val response = withContext(bgDispatcher) { dashboardRepository?.fetchHeroes(offset, limit) }
                response?.let {
                    showError.value = false
                    offset += limit
                    heroesList.addAll(it.marvelHeroes)
                    heroes.value = heroesList
                } ?: run {
                    showError.value = true
                }
            } catch (e: Exception) {
                Timber.e(e.toString())
                showError.value = true
            } finally {
                showLoading.value = false
                setFetching(false)
            }
        }
    }

    fun updateFavourite(heroId: Int) {
        uiScope.launch {
            showLoading.value = true
            try {
                withContext(bgDispatcher) { dashboardRepository?.updateFavourite(heroId) }
            } catch (e: Exception) {
                Timber.e(e.toString())
                showError.value = true
            } finally {
                showLoading.value = false
            }
        }
    }

    @Synchronized
    private fun isFetching(): Boolean {
        return this.isFetching
    }

    @Synchronized
    private fun setFetching(isFetching: Boolean) {
        this.isFetching = isFetching
    }
}