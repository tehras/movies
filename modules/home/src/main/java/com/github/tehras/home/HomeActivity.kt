package com.github.tehras.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.tehras.arch.viewModelActivity
import com.github.tehras.boxoffice.BoxOfficeFragment
import com.github.tehras.dagger.components.findComponent
import com.github.tehras.discover.ui.DiscoverFragment
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModelActivity<HomeViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        findComponent<HomeComponentCreator>()
                ?.plusHomeActivity()
                ?.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        setupBottomNav()

        if (savedInstanceState == null) {
            bottom_nav_view.selectedItemId = R.id.home_discover
            startDiscoverFragment()
        }
    }

    private fun setupBottomNav() {
        bottom_nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_discover -> startDiscoverFragment()
                R.id.home_box_office -> startBoxOfficeFragment()
            }

            true
        }
    }

    private fun startDiscoverFragment() {
        startFragment(DiscoverFragment.newInstance(), "discover_fragment")
    }

    private fun startBoxOfficeFragment() {
        startFragment(BoxOfficeFragment.newInstance(), "box_office_fragment")
    }

    private fun startFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.fragments.lastOrNull()?.let {
            if (it.javaClass == fragment.javaClass) {
                return
            }
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .commit()
    }

}
