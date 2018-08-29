/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.discover.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.tehras.arch.viewModel
import com.github.tehras.dagger.components.findComponent
import com.github.tehras.discover.R
import com.github.tehras.discover.ui.list.DiscoverAdapter
import com.github.tehras.discover.ui.list.DiscoverItems
import com.github.tehras.discover.ui.list.State
import ext.android.content.isDarkModeEnabled
import ext.android.content.setDarkModeEnabled
import ext.androidx.fragment.app.darkTheme
import ext.androidx.fragment.app.setToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_home_discover.*
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
    @Inject
    lateinit var sharedPrefs: SharedPreferences

    private val viewModel by viewModel<DiscoverViewModel> { factory }
    private val adapter by lazy { DiscoverAdapter() }

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        findComponent<DiscoverComponentCreator>()
            ?.plusDiscoverComponent()
            ?.inject(this)

        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()

        disposables += viewModel
            .observeState()
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                DiscoverItems(State.DONE, it.discoverItems)
            }
            .doOnError { DiscoverItems(State.ERROR, mutableListOf()) }
            .subscribe(adapter.consume())
    }

    override fun onStop() {
        disposables.dispose()

        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.discover_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_dark_mode).run {
            val isDarkModeEnabled = sharedPrefs.isDarkModeEnabled()
            title = "${if (isDarkModeEnabled) "Disable" else "Enable"} dark mode"
            setOnMenuItemClickListener {
                sharedPrefs.setDarkModeEnabled(!isDarkModeEnabled)
                (activity as? AppCompatActivity)?.darkTheme = !isDarkModeEnabled

                true
            }
        }
    }

    private fun initRecyclerView() {
        discover_list_view.let { rv ->
            rv.adapter = adapter
            rv.setHasFixedSize(true)
            rv.layoutManager = LinearLayoutManager(context)
            rv.itemAnimator = SlideInUpAnimator(DecelerateInterpolator())
            rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        }
    }

    private fun initToolbar() {
        setToolbar(discover_toolbar)
        setHasOptionsMenu(true)

        discover_toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}