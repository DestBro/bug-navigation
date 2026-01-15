package com.bug.navigation.bug_navigation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform