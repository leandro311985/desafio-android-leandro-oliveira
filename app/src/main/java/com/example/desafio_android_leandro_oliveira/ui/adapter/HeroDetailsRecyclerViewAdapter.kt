package com.example.desafio_android_leandro_oliveira.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android_leandro_oliveira.R
import com.example.desafio_android_leandro_oliveira.model.MarvelHeroesModel
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_hero_details.view.*
import kotlinx.android.synthetic.main.row_hero_details.*

class HeroDetailsRecyclerViewAdapter(private val heroList: MutableList<MarvelHeroesModel>, private val rowWidth: Int) : RecyclerView.Adapter<HeroDetailsRecyclerViewAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_hero_details, parent, false),rowWidth)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val hero = heroList[position]
        holder.heroName.text = hero.name
        holder.heroDescription.text = handleDescription(hero.description, holder.itemView.context)
        Picasso.get().load(hero.thumbnail).into(holder.heroImageView.heroImageView)
    }

    private fun handleDescription(description: String, context: Context): String {
        return when {
            description.isEmpty() -> context.resources.getString(R.string.dummy_description)
            else -> description
        }
    }

    override fun getItemCount(): Int {
        return heroList.size
    }

    class ItemViewHolder(override val containerView: View, rowWidth: Int) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init {
            itemView.layoutParams.width = rowWidth
        }
    }
}