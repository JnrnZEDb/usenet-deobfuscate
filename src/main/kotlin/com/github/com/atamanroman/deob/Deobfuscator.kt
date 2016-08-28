package com.github.com.atamanroman.deob

import java.io.File
import java.nio.file.Paths
import java.util.*

class Deobfuscator(obfuscated: List<String>, val suffixes: List<String> = listOf("OBFUSCATED")) {

    var deobfuscated: Map<String, DeobfuscationResult?>

    init {
        deobfuscated = obfuscated.associate { key ->
            val folder = File(key)

            try {
                val matchingSuffix = suffixes.first {
                    folder.absolutePath.endsWith(it)
                }

                val newFolderName = folder.absolutePath.substringBeforeLast("-$matchingSuffix")

                val largestFile = folder.walk().filter { it.isFile }.maxBy { it.length() }

                if (largestFile != null) {
                    val ext = largestFile.extension
                    val realFileName = Paths.get(newFolderName).fileName.toString()
                    val newFileName = "$newFolderName/$realFileName.$ext"

                    return@associate Pair(key,
                            DeobfuscationResult(key, largestFile.absolutePath, newFolderName, newFileName))
                }
            } catch (e: NoSuchElementException) {
            }
            Pair(key, null)
        }
    }

    class DeobfuscationResult(val originalFolder: String, val originalFile: String,
                              val newFolder: String, val newFile: String)
}
