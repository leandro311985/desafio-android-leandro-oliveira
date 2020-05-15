package com.example.desafio_android_leandro_oliveira.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio_android_leandro_oliveira.R
import com.example.desafio_android_leandro_oliveira.commons.BUNDLE
import com.example.desafio_android_leandro_oliveira.commons.Definitions
import com.example.desafio_android_leandro_oliveira.commons.MarvelApplication
import com.example.desafio_android_leandro_oliveira.database.MarvelDatabase
import com.example.desafio_android_leandro_oliveira.repository.base.BaseViewModelFactory
import com.example.desafio_android_leandro_oliveira.repository.dashboard.DashboardRepository
import com.example.desafio_android_leandro_oliveira.ui.adapter.DashboardRecyclerViewAdapter
import com.example.desafio_android_leandro_oliveira.ui.adapter.PaginationScrollListener
import com.example.desafio_android_leandro_oliveira.viewModel.DashboardViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.layout_pagination_recyclerview.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class DashboardActivity : BaseActivity<DashboardViewModel>() {

    private var dashboardRecyclerViewAdapter: DashboardRecyclerViewAdapter? = null
    private var paginationScrollListener: PaginationScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        initLayout()
        initViewModel()
    }

    private fun initViewModel() {
        val dashboardViewModelFactory = BaseViewModelFactory {
            DashboardViewModel(
                DashboardRepository(
                    MarvelApplication.get()?.marvelClient,
                    MarvelDatabase.get(this)
                )
            )
        }

        viewModel = ViewModelProviders.of(this, dashboardViewModelFactory)
            .get(DashboardViewModel::class.java)
        viewModel?.getHeroes()?.observe(this, Observer { heroes ->
            // update UI
            moreProgressView?.visibility = View.GONE
            heroes?.let {
                dashboardRecyclerViewAdapter?.setHeroesList(it.toMutableList())
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

    private fun initLayout() {
        toolbarTitleTextView.text = getString(R.string.dashboard_toolbar_title)
        backButtonImageView.visibility = View.INVISIBLE


        val linearLayoutManager = LinearLayoutManager(this)
        dashboardRecyclerView.layoutManager = linearLayoutManager
        dashboardRecyclerViewAdapter = DashboardRecyclerViewAdapter(
            onFavouriteClicked = { heroId ->
                viewModel?.updateFavourite(heroId)
            },
            onHeroClicked = { hero ->
                val intent = Intent(this, HeroDetailsActivity::class.java)
                intent.putExtra(BUNDLE.HERO_DETAILS, hero)
                startActivity(intent)
            })

        paginationScrollListener = PaginationScrollListener(
            linearLayoutManager,
            {
                moreProgressView?.visibility = View.VISIBLE
                viewModel?.loadHeroes()
            },
            Definitions.PAGINATION_SIZE
        )
        paginationScrollListener?.let {
            dashboardRecyclerView.addOnScrollListener(it)
        }

        dashboardRecyclerView.adapter = dashboardRecyclerViewAdapter

    }
}