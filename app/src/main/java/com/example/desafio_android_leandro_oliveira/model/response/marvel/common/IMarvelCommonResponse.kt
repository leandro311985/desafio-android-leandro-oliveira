package com.example.desafio_android_leandro_oliveira.model.response.marvel.common

interface IMarvelCommonResponse {
    fun isSuccess(): Boolean
    fun getErrorMessage(): String
}