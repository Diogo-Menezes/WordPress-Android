package org.wordpress.android.ui.uploads

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.wordpress.android.fluxc.model.SiteModel
import org.wordpress.android.fluxc.store.PostStore
import org.wordpress.android.modules.BG_THREAD
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Provides a way to find and upload all local drafts.
 */
class LocalDraftUploadStarter @Inject constructor(
    /**
     * The Application context
     */
    private val context: Context,
    private val postStore: PostStore,
    /**
     * The Coroutine dispatcher used for querying in FluxC.
     */
    @Named(BG_THREAD) private val bgDispatcher: CoroutineDispatcher
) {
    fun uploadLocalDrafts(scope: CoroutineScope, site: SiteModel) = scope.launch(bgDispatcher) {
        val localDrafts = postStore.getLocalDrafts(site)
        localDrafts.forEach { UploadService.uploadPost(context, it) }
    }
}
