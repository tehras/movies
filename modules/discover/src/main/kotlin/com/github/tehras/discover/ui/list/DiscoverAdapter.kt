/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.discover.ui.list

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.view.inflateView
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.discover.R
import com.github.tehras.restapi.tmdb.Discover
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import timber.log.Timber

/**
 * @author tkoshkin created on 8/27/18
 */
@SuppressLint("CheckResult")
class DiscoverAdapter : RecyclerView.Adapter<DiscoverViewHolder>() {

    private val discoverItems: DiscoverItems = DiscoverItems()

    private val relay = BehaviorRelay.create<DiscoverItems>()
    fun consume(): Consumer<DiscoverItems> = relay

    init {
        relay
            .observeOn(AndroidSchedulers.mainThread())
            .startWith(DiscoverItems(State.LOADING, mutableListOf()))
            .subscribe {
                Timber.d("Results received - ${it.discoverList}")
                discoverItems.state = it.state
                discoverItems.discoverList.addAll(it.discoverList)

                notifyDataSetChanged()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverViewHolder {
        return when (ItemType.values()[viewType]) {
            ItemType.TITLE -> DiscoverTitleViewHolder(parent.inflateView(R.layout.discover_view_title))
            ItemType.BODY -> DiscoverMovieViewHolder(parent.inflateView(R.layout.discover_view_body))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (discoverItems.discoverList[position]) {
            is Int -> ItemType.TITLE.ordinal
            else -> ItemType.BODY.ordinal
        }
    }

    override fun getItemCount(): Int {
        return discoverItems.discoverList.size
    }

    override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
        when (holder) {
            is DiscoverTitleViewHolder -> holder.bind(discoverItems.discoverList[position] as Int)
            is DiscoverMovieViewHolder -> holder.bind(discoverItems.discoverList[position] as Discover)
        }
    }
}

data class DiscoverItems(var state: State = State.LOADING, var discoverList: MutableList<Any> = mutableListOf())

enum class ItemType {
    TITLE, BODY
}

enum class State {
    ERROR, LOADING, DONE
}

