package com.dominikgold.composedbudgets

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform