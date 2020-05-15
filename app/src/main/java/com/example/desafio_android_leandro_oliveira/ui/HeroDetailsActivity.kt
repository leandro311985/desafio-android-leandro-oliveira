package com.example.desafio_android_leandro_oliveira.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio_android_leandro_oliveira.R
import com.example.desafio_android_leandro_oliveira.commons.BUNDLE
import com.example.desafio_android_leandro_oliveira.commons.MarvelApplication
import com.example.desafio_android_leandro_oliveira.commons.PagerSnapCallbackHelper
import com.example.desafio_android_leandro_oliveira.extentions.getScreenWidth
import com.example.desafio_android_leandro_oliveira.model.MarvelHeroesModel
import com.example.desafio_android_leandro_oliveira.repository.base.BaseViewModelFactory
import com.example.desafio_android_leandro_oliveira.repository.heroDetails.HeroDetailsRepository
import com.example.desafio_android_leandro_oliveira.ui.adapter.HeroDetailsRecyclerViewAdapter
import com.example.desafio_android_leandro_oliveira.viewModel.HeroDetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_hero_details.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class HeroDetailsActivity : BaseActivity<HeroDetailsViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_details)
        initViewModel()
        initLayout()
    }

    private fun initLayout() {
        backButtonImageView.setOnClickListener { onBackPressed() }
        toolbarTitleTextView.text = getString(R.string.dashboard_toolbar_title)
        Picasso.get().load(viewModel?.hero?.thumbnail).into(heroImageView)
        heroTitleTextView.text = viewModel?.hero?.name
        heroDescriptionTextView.text =
            if (viewModel?.hero?.description.isNullOrEmpty()) getString(R.string.dummy_description) else viewModel?.hero?.description
    }

    private fun initViewModel() {
        val heroDetailsViewModelFactory = BaseViewModelFactory {
            HeroDetailsViewModel(
                HeroDetailsRepository(MarvelApplication.get()?.marvelClient),
                intent?.extras?.getParcelable<MarvelHeroesModel>(
                    BUNDLE.HERO_DETAILS
                )
            )
        }
        viewModel = ViewModelProviders.of(this, heroDetailsViewModelFactory)
            .get(HeroDetailsViewModel::class.java)

        viewModel?.getComics()?.observe(this, Observer { heroes ->
            heroes?.let {
                heroDetailsRecyclerView.setHasFixedSize(true)
                heroDetailsRecyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                heroDetailsRecyclerView.adapter = HeroDetailsRecyclerViewAdapter(
                    it.toMutableList(),
                    (getScreenWidth(this) * 0.85).toInt()
                )

                val pagerSnapCallbackHelper = PagerSnapCallbackHelper()
                pagerSnapCallbackHelper.attachToRecyclerView(heroDetailsRecyclerView)
            } ?: run { emptyView.visibility = View.VISIBLE }
        })

        viewModel?.getIsLoading()?.observe(this, Observer { value ->
            value?.let { show ->
                loadingView.visibility = if (show) View.VISIBLE else View.GONE
            }
        })

        viewModel?.shouldShowError()?.observe(this, Observer { value ->
            value?.let { show ->
                emptyView.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }
}