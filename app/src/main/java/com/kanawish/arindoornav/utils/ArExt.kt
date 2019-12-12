package com.kanawish.arindoornav.utils

import android.app.Activity
import android.net.Uri
import androidx.annotation.RawRes
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Future

/**
 * Sceneform Renderable builders use `java.util.concurrent.Future`.
 * This is a small utility function to convert these `Future` instances
 * to RxJava friendly `Single` instances.
 */
fun <T> Future<T>.toSingle() =
    Single.fromFuture(this)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

/**
 * Utility function that returns a Single<ModelRenderable>
 *
 * @param rawId Raw res ID for the source rawId for our ModelRenderable
 * @return A Single<ModelRenderable> triggered on the main thread.
 */
fun Activity.singleModelRenderable(@RawRes rawId: Int) =
    ModelRenderable
        .builder()
        .setSource(this, rawId)
        .build()
        .toSingle()

fun Activity.singleModelRenderable(assetUri: Uri): Single<ModelRenderable> =
    ModelRenderable
        .builder()
        .setSource(this, assetUri)
        .build()
        .toSingle()

fun ArFragment.initTouchModelAnchoring(model: ModelRenderable) {
    setOnTapArPlaneListener { hitResult, _, _ ->
        val anchor = hitResult.createAnchor()
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arSceneView.scene)

        // The node for the model itself.
        val modelNode = TransformableNode(transformationSystem)
        modelNode.renderable = model
        modelNode.setParent(anchorNode)
    }
}

fun String.toRawUri() = Uri.parse("file:///android_asset/$this")
