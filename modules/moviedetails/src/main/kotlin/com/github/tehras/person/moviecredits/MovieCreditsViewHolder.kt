package com.github.tehras.person.moviecredits

import android.annotation.SuppressLint
import android.text.Html
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.tehras.models.tmdb.models.cast.PersonCast
import com.github.tehras.moviedetails.R
import com.github.tehras.restapi.IMAGE_URL_SMALL
import ext.android.view.gone
import ext.android.view.visible
import ext.kotlin.toDate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_movie_credits.*

class MovieCreditsViewHolder(
        override val containerView: View,
        val itemClicked: (Long) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    @SuppressLint("SetTextI18n")
    @Suppress("DEPRECATION")
    fun bind(cast: PersonCast) {
        movie_character.text = Html.fromHtml("as <b>${cast.character}</b>")
        movie_title.text = cast.title ?: ""
        movie_overview.text = cast.overview

        val releaseDate = cast.releaseDate?.toDate() ?: ""
        if (releaseDate.isNotEmpty()) {
            movie_release.text = "Release - $releaseDate"
        } else {
            movie_release.text = ""
        }

        if (cast.voteAverage ?: 0.0 > 0.0) {
            movie_rating.visible()
            movie_rating.setProgress(cast.voteAverage!!.times(10))
        } else {
            movie_rating.gone()
        }
        if (cast.voteCount ?: 0 > 0) {
            movie_rating_count.visible()
            movie_rating_count.text = "${cast.voteCount} votes"
        } else {
            movie_rating_count.gone()
        }

        movie_clickable_item.setOnClickListener {
            itemClicked(cast.id)
        }

        Glide.with(movie_image)
                .load("$IMAGE_URL_SMALL${cast.posterPath}")
                .apply(RequestOptions()
                        .transform(RoundedCorners(16))
                        .placeholder(R.drawable.placeholder_cast)
                        .error(R.drawable.placeholder_cast)
                )
                .into(movie_image)
    }
}