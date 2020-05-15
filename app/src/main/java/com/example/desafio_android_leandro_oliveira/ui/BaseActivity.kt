package com.example.desafio_android_leandro_oliveira.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.example.desafio_android_leandro_oliveira.viewModel.BaseViewModelMarvel

@SuppressLint("Registered")
open class BaseActivity<T : BaseViewModelMarvel> : AppCompatActivity() {

    protected var viewModel: T? = null
}
