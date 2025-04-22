package com.dominikgold.composedbudgets.utils.files

import com.dominikgold.composedbudgets.utils.PlatformContext
import okio.BufferedSource

expect class LocalFileResource {
    fun open(context: PlatformContext): BufferedSource
}
