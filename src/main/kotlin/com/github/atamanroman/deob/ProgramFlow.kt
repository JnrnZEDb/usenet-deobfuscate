package com.github.atamanroman.deob

import java.io.File

class ProgramFlow(val root: String, val dryRun: Boolean) {

    var folders: List<String> = emptyList()
    var detected: List<String> = emptyList()

    fun go() {
        findFolders()
        findObfuscated()
        rename()
    }

    private fun rename() {
        val deobfuscator = Deobfuscator(detected, dryRun)
        deobfuscator.rename()
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
