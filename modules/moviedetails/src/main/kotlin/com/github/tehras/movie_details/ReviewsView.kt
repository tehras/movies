package com.github.tehras.movie_details

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.github.tehras.restapi.tmdb.models.moviedetails.MovieDetails
import com.github.tehras.restapi.tmdb.models.reviews.Review
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.content_reviews.view.*
import kotlinx.android.synthetic.main.view_reviews_item.view.*
import kotlinx.android.synthetic.main.view_reviews_total.view.*

class ReviewsView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.content_reviews, this)
    }

    private val reviews: MutableList<Review> = mutableListOf()
    private var details: MovieDetails? = null
    private val relay = PublishRelay.create<Pair<MovieDetails, MutableList<Review>>>()

    fun consume(): PublishRelay<Pair<MovieDetails, MutableList<Review>>> = relay.also { relay ->
        relay
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    details = it.first
                    reviews.clear()
                    reviews.addAll(it.second)

                    dataChanged()
                }

    }

    private fun dataChanged() {
        updateTotalRatings()
        updateReviews()
    }

    @SuppressLint("SetTextI18n")
    private fun updateTotalRatings() {
        details?.let {
            reviews_rating.setProgress(it.voteAverage.times(10))
            reviews_total_ratings.text = "${it.voteCount.toInt()} reviews"
        }
    }

    private fun updateReviews() {
        reviews.getOrNull(0)?.let {
            updateReview(review_1, it)
        }

        reviews.getOrNull(1)?.let {
            updateReview(review_2, it)
        }
    }

    private fun updateReview(view: View, review: Review) {
        view.review_name.text = review.author
        view.review_content.text = Html.fromHtml(review.content)
    }
}