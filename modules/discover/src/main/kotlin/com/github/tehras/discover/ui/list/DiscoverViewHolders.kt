/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.discover.ui.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.restapi.tmdb.models.Discover
import com.github.tehras.views.GenreOutline
import com.squareup.picasso.Picasso
import ext.android.view.gone
import ext.android.view.visible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.discover_view_body.*

/**
 * @author tkoshkin created on 8/27/18
 */
sealed class DiscoverViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

class DiscoverMovieViewHolder(itemView: View) : DiscoverViewHolder(itemView) {
    fun bind(discover: Discover) {
        movie_title.text = discover.title
        movie_description.text = discover.overview
        movie_rating.setProgress(discover.voteAverage.times(10))

        movie_genre_1.gone()
        movie_genre_2.gone()
        movie_genre_3.gone()

        discover.genres
            .take(3)
            .forEachIndexed { index, genre ->
                genreView(index).run {
                    setText(genre.name)
                    visible()
                }
            }

        Picasso.get()
            .load("https://image.tmdb.org/t/p/w300/${discover.posterPath}")
            .into(movie_image)
    }

    private fun genreView(index: Int): GenreOutline {
        return when (index) {
            0 -> movie_genre_1
            1 -> movie_genre_2
            else -> movie_genre_3
        }
    }
}