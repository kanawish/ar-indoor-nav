package com.kanawish.arindoornav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.ux.ArFragment
import com.kanawish.arindoornav.utils.initTouchModelAnchoring
import com.kanawish.arindoornav.utils.singleModelRenderable
import com.kanawish.arindoornav.utils.toRawUri
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class ArActivity : AppCompatActivity() {

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // `FrameLayout` holding a `ArCustomFragment`
        setContentView(R.layout.activity_main)

        val arFragment = supportFragmentManager
            .findFragmentById(R.id.ux_fragment) as ArFragment

        disposables +=
            singleModelRenderable("andy-machinery.sfb".toRawUri()).subscribe { andy ->
                arFragment.initTouchModelAnchoring(andy)
            }
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

}
