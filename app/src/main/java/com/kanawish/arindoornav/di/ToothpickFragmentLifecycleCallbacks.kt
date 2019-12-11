package com.kanawish.arindoornav.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import toothpick.InjectConstructor
import toothpick.ktp.KTP
import toothpick.smoothie.lifecycle.closeOnDestroy
import javax.inject.Singleton

/**
 * Typically not needed, but nice option on hand if we want fragment-scoped objects.
 *
 * Also a good 'cross-cut' point if we want to use and abstract away VM-like scope.
 */
@Singleton
@InjectConstructor
class ToothpickFragmentLifecycleCallbacks() : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentPreAttached(fm: FragmentManager, fragment: Fragment, context: Context) {
        KTP.openRootScope()
            .openSubScope(context) // aka parent activity
            .openSubScope(fragment) { scope ->
                scope.supportScopeAnnotation(FragmentScope::class.java)
            }
            .closeOnDestroy(fragment)
            .inject(fragment)
    }
}