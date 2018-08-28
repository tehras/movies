/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.discover.ui.list

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.restapi.tmdb.Discover
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.discover_view_body.*
import kotlinx.android.synthetic.main.discover_view_title.*

/**
 * @author tkoshkin created on 8/27/18
 */
sealed class DiscoverViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

class DiscoverTitleViewHolder(itemView: View) : DiscoverViewHolder(itemView) {
    @SuppressLint("SetTextI18n")
    fun bind(year: Int) {
        movie_year.text = "Year released - $year"
    }
}

class DiscoverMovieViewHolder(itemView: View) : DiscoverViewHolder(itemView) {
    fun bind(discover: Discover) {
        movie_title.text = discover.title
        movie_description.text = discover.overview
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w300/${discover.posterPath}")
            .into(movie_image)
    }
}