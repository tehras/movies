/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.discover.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.tehras.arch.viewModel
import com.github.tehras.dagger.components.findComponent
import com.github.tehras.discover.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

/**
 * @author tkoshkin created on 8/26/18
 */
class DiscoverFragment : Fragment() {

    companion object {
        fun newInstance() = DiscoverFragment()
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModel<DiscoverViewModel> { factory }

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        findComponent<DiscoverComponentCreator>()
            ?.plusDiscoverComponent()
            ?.inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_discover, container, false)
    }

    override fun onStart() {
        super.onStart()

        disposables += viewModel
            .observeState()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Timber.d("Response In UI - $it")
            }
    }

    override fun onStop() {
        disposables.dispose()

        super.onStop()
    }
}