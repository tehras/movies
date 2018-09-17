package com.github.tehras.commonviews.movies

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.tehras.models.tmdb.models.common.Movie
import com.github.tehras.restapi.IMAGE_URL_SMALL
import com.github.tehras.views.GenreOutline
import com.jakewharton.rxbinding2.view.clicks
import ext.android.view.gone
import ext.android.view.visible
import io.reactivex.functions.Consumer
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.discover_view_body.*

/**
 * @author tkoshkin created on 8/27/18
 */
sealed class MoviesViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

class MoviesBodyViewHolder(itemView: View, private val clicked: Consumer<Movie>) : MoviesViewHolder(itemView) {
    lateinit var discover: Movie

    @SuppressLint("CheckResult")
    fun bind(discover: Movie) {
        this.discover = discover

        movie_clickable_layout
                .clicks()
                .map { discover }
                .subscribe(clicked)

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

        Glide.with(movie_image)
                .load("$IMAGE_URL_SMALL${discover.posterPath}")
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