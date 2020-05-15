package com.example.desafio_android_leandro_oliveira.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MarvelTable")
class MarvelTable(
    @PrimaryKey
    var id: Int,
    var isFavourite: Boolean = false
)