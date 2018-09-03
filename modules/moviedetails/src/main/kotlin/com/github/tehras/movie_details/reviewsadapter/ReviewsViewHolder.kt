package com.github.tehras.movie_details.reviewsadapter

import android.annotation.SuppressLint
import android.text.Html
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.restapi.tmdb.models.moviedetails.MovieDetails
import com.github.tehras.restapi.tmdb.models.reviews.Review
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_reviews_item.*
import kotlinx.android.synthetic.main.view_reviews_total.*

class ReviewsViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    @Suppress("DEPRECATION")
    fun bind(review: Review) {
        review_name.text = review.author
        review_content.text = Html.fromHtml(review.content)
    }
}

class ReviewsTotalRating(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    @SuppressLint("SetTextI18n")
    @Suppress("DEPRECATION")
    fun bind(details: MovieDetails) {
        reviews_rating.setProgress(details.voteAverage.times(10))
        reviews_total_ratings.text = "${details.voteCount.toInt()} reviews"
    }
}