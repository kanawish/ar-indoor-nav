package com.kanawish.arindoornav

import android.os.Bundle
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment

/* ArFragment inheritance / overrides diagram
@startuml
hide empty members
namespace sceneform {
    class BaseArFragment
    class ArFragment {
        +isArRequired()
        +getAdditionalPermissions()
        +getSessionConfiguration()
        +getSessionFeatures()
    }
    BaseArFragment <|-- ArFragment
}
@enduml
*/

/**
 * # Sceneform
 *
 * ## ArCustomFragment
 *
 * ArFragment is a Sceneform component. It's said to be the simplest way
 * to create a scene view.
 *
 * Compared to using ArCore directly, it takes care of a lot of things
 * on your behalf:
 *
 * - Setting up OpenGL, SurfaceView.
 * - Takes care of ArCoreApk installation/upgrade flow.
 * - Takes care of asking for required ArCore permissions. [CAMERA]
 *
 */
class ArCustomFragment : ArFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Move the raw/asset dependent init code here.
    }
    /**
     * We initialize the returned configuration here.
     *
     * Loads up augmented image DB, assigns it to session config.
     *
     * @return the configuration that will be used by ArFragment.
     */
    override fun getSessionConfiguration(session: Session): Config {
        return super.getSessionConfiguration(session).also { config ->
            // TODO: add custom configuration as needed.
        }
    }

}

