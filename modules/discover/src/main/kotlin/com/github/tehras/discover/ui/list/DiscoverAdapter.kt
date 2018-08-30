/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.discover.ui.list

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.discover.R
import com.github.tehras.restapi.tmdb.models.discover.Discover
import com.jakewharton.rxrelay2.BehaviorRelay
import ext.android.view.inflateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import timber.log.Timber

/**
 * @author tkoshkin created on 8/27/18
 */
@SuppressLint("CheckResult")
class DiscoverAdapter(private val clickConsumer: Consumer<Discover>) : RecyclerView.Adapter<DiscoverViewHolder>() {

    private var discoverItems: DiscoverItems = DiscoverItems()

    private val relay = BehaviorRelay.create<DiscoverItems>()
    fun consume(): Consumer<DiscoverItems> = relay

    init {
        relay
            .observeOn(AndroidSchedulers.mainThread())
            .startWith(DiscoverItems(State.LOADING, mutableListOf()))
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverViewHolder {
        return DiscoverMovieViewHolder(parent.inflateView(R.layout.discover_view_body), clickConsumer)
    }

    override fun getItemCount(): Int {
        return discoverItems.discoverList.size
    }

    override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
        when (holder) {
            is DiscoverMovieViewHolder -> holder.bind(discoverItems.discoverList[position])
        }
    }
}

data class DiscoverItems(var state: State = State.LOADING, var discoverList: MutableList<Discover> = mutableListOf())

enum class State {
    ERROR, LOADING, DONE
}

