package com.github.atamanroman.deob

class ObfuscationDetector(targets: List<String>, val suffixes: List<String> = listOf("OBFUSCATED")) {

    val detected: List<String>

    init {
        if(suffixes.isEmpty())
            throw IllegalArgumentException("List of suffixes must not be empty.")
        detected = targets.filter { dir ->
            suffixes.any { suffix ->
                dir.endsWith(suffix, ignoreCase = true)
            }
        }
    }
}
