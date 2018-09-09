package com.github.tehras.person.moviecredits

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.person.R
import com.github.tehras.models.tmdb.models.cast.PersonCast
import com.jakewharton.rxrelay2.PublishRelay
import ext.android.view.inflateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer

class MovieCreditsAdapter : RecyclerView.Adapter<MovieCreditsViewHolder>() {
    private val credits: MutableList<PersonCast> = mutableListOf()
    private val relay = PublishRelay.create<MutableList<PersonCast>>().also { relay ->
        relay
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    credits.clear()
                    credits.addAll(it)

                    notifyDataSetChanged()
                }
    }

    fun consume(): Consumer<MutableList<PersonCast>> = relay

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCreditsViewHolder = MovieCreditsViewHolder(parent.inflateView(R.layout.view_movie_credits))
    override fun getItemCount(): Int = credits.size
    override fun onBindViewHolder(holder: MovieCreditsViewHolder, position: Int) {
        holder.bind(credits[position])
    }
}