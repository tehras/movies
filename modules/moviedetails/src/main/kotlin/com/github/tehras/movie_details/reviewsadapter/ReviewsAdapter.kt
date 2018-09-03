package com.github.tehras.movie_details.reviewsadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.movie_details.R
import com.github.tehras.restapi.tmdb.models.moviedetails.MovieDetails
import com.github.tehras.restapi.tmdb.models.reviews.Review
import com.jakewharton.rxrelay2.PublishRelay
import ext.android.view.inflateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy

class ReviewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

                    notifyDataSetChanged()
                }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DETAILS -> ReviewsTotalRating(parent.inflateView(R.layout.view_reviews_total))
            else -> ReviewsViewHolder(parent.inflateView(R.layout.view_reviews_item))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReviewsViewHolder -> holder.bind(review = reviews[position - (if (details != null) 1 else 0)])
            is ReviewsTotalRating -> holder.bind(details!!)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && details != null) {
            VIEW_TYPE_DETAILS
        } else {
            VIEW_TYPE_REVIEWS
        }
    }

    override fun getItemCount(): Int = reviews.size + (if (details != null) 1 else 0)

    companion object {
        const val VIEW_TYPE_DETAILS = 0
        const val VIEW_TYPE_REVIEWS = 1
    }
}