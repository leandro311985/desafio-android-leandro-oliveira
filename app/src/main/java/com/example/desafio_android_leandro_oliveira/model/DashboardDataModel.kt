package com.example.desafio_android_leandro_oliveira.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarvelHeroesModel(val id: Int = 0,
                             val name: String = "",
                             val description: String = "",
                             val thumbnail: String = "",
                             var isFavorite: Boolean = false) : Parcelable

@Parcelize
data class MarvelListModel(val marvelHeroes: List<MarvelHeroesModel> = listOf()):Parcelable