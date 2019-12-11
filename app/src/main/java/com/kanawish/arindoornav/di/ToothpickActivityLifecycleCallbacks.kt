package com.kanawish.arindoornav.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import toothpick.InjectConstructor
import toothpick.ktp.KTP
import toothpick.smoothie.lifecycle.closeOnDestroy
import toothpick.smoothie.module.SmoothieActivityModule
import toothpick.smoothie.module.SmoothieAndroidXActivityModule
import javax.inject.Singleton

@Singleton
@InjectConstructor
class ToothpickActivityLifecycleCallbacks(
    private val fragmentLifecycleCallbacks: ToothpickFragmentLifecycleCallbacks
) : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is FragmentActivity) {
            KTP.openRootScope()
                .openSubScope(activity) { scope ->
                    scope
                        .installModules(SmoothieAndroidXActivityModule(activity))
                        .supportScopeAnnotation(ActivityScope::class.java)
                }
                .closeOnDestroy(activity)
                .inject(activity)
        } else {
            KTP.openRootScope()
                .openSubScope(activity) { scope ->
                    scope
                        .installModules(SmoothieActivityModule(activity))
                        .supportScopeAnnotation(ActivityScope::class.java)
                }
                .inject(activity)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        if(activity !is FragmentActivity) {
            KTP.closeScope(activity)
        }
    }

    // Unused, moved to bottom of class for readability.
    override fun onActivityStarted(activity: Activity?) {}
    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivityResumed(activity: Activity?) {}
    override fun onActivityStopped(activity: Activity?) {}
    override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {}
}