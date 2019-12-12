package com.kanawish.arindoornav

import android.app.Application
import com.kanawish.arindoornav.di.ToothpickActivityLifecycleCallbacks
import com.kanawish.arindoornav.timber.CrashReportingTree
import timber.log.Timber
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import toothpick.smoothie.module.SmoothieApplicationModule

class ArApp : Application() {
    private val activityLifecycleCallbacks: ToothpickActivityLifecycleCallbacks by inject()

    private val scope: Scope = KTP.openRootScope()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree());
        } else {
            Timber.plant(CrashReportingTree());
        }

        scope
            .installModules(SmoothieApplicationModule(this)) // NOTE: Extra modules go here.
            .inject(this)

        // NOTE: Can only be called post-injection.
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        scope.release()
    }

}