package com.github.tehras.commonviews.movies

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.commonviews.R
import com.github.tehras.restapi.tmdb.models.common.Movie
import com.jakewharton.rxrelay2.BehaviorRelay
import ext.android.view.inflateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import timber.log.Timber

/**
 * @author tkoshkin created on 8/27/18
 */
@SuppressLint("CheckResult")
class MoviesAdapter(private val clickConsumer: Consumer<Movie>) : RecyclerView.Adapter<MoviesViewHolder>() {

    private var discoverItems: MovieItems = MovieItems()

    private val relay = BehaviorRelay.create<MovieItems>()
    fun consume(): Consumer<MovieItems> = relay.also { relay ->
        relay
                .observeOn(AndroidSchedulers.mainThread())
                .startWith(MovieItems(State.LOADING, mutableListOf()))
                .subscribe {
                    Timber.d("Results received - ${it.discoverList}")
                    val animate = discoverItems.state == State.LOADING && discoverItems.discoverList.isEmpty()
                    discoverItems = it

                    if (animate) {
                        notifyItemRangeInserted(0, discoverItems.discoverList.size)
                    } else {
                        notifyDataSetChanged()
                    }
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesBodyViewHolder(parent.inflateView(R.layout.discover_view_body), clickConsumer)
    }

    override fun getItemCount(): Int {
        return discoverItems.discoverList.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        when (holder) {
            is MoviesBodyViewHolder -> holder.bind(discoverItems.discoverList[position])
        }
    }
}

data class MovieItems(var state: State = State.LOADING, var discoverList: MutableList<Movie> = mutableListOf())

enum class State {
    ERROR, LOADING, DONE
}

