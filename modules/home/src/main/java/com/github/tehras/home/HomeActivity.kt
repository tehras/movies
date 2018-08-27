package com.github.tehras.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.tehras.arch.viewModelActivity
import com.github.tehras.dagger.components.findComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModelActivity<HomeViewModel> { factory }

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        findComponent<HomeComponentCreator>()
            ?.plusHomeActivity()
            ?.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
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
