package com.github.atamanroman.deob

import java.io.File

class ProgramFlow(val root: String) {

    var folders: List<String> = emptyList()
    var detected: List<String> = emptyList()

    fun go() {
        findFolders()
        findObfuscated()
    }

    private fun findFolders(): List<String> {
        folders = File(root).walk().filter { it.isDirectory }.map { it.absolutePath }.toList()
        return folders
    }

    private fun findObfuscated(): List<String> {
        detected = ObfuscationDetector(folders).detected
        return detected
    }
}
