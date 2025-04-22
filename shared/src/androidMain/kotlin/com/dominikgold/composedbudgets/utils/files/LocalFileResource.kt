package com.dominikgold.composedbudgets.utils.files

import android.net.Uri
import com.dominikgold.composedbudgets.utils.PlatformContext
import okio.BufferedSource
import okio.buffer
import okio.source

actual data class LocalFileResource(val uri: Uri) {
    actual fun open(context: PlatformContext): BufferedSource {
        return context.value.contentResolver.openInputStream(uri)?.use {
            it.source().buffer()
        } ?: error("Failed to open URI")
    }
}
