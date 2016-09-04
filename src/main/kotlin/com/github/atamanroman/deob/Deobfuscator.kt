package com.github.atamanroman.deob

import java.io.File
import java.nio.file.Paths
import java.util.*

class Deobfuscator(obfuscated: List<String>, val dryRun: Boolean = true, val suffixes: List<String> = listOf("OBFUSCATED")) {

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

    fun rename() {
        deobfuscated.filter { it.value != null }.forEach {
            val info = it.value as DeobfuscationResult

            val originalFolder = File(info.originalFolder)
            // new file temporary sits in new folder
            val newFileLocation = originalFolder.resolve(File(info.newFile).name)

            var result = dryRun || File(info.originalFile).renameTo(newFileLocation)
            logRename(result, info.originalFile, info.newFile)

            if (result) {
                result = dryRun || File(info.originalFolder).renameTo(File(info.newFolder))
                logRename(result, info.originalFolder, info.newFolder)
            }

            if (result)
                println("Successfully deobfuscated ${it.key}${if (dryRun) " (dry-run)" else ""}")
            else
                error("Could not deobfuscate ${it.key}${if (dryRun) " (dry-run)" else ""}")

        }
    }

    private fun logRename(result: Boolean, from: String, to: String) {
        if (result)
            println("Renamed $from to $to${if (dryRun) " (dry-run)" else ""}")
        else
            error("Could not rename $from to $to${if (dryRun) " (dry-run)" else ""}")
    }

    class DeobfuscationResult(val originalFolder: String, val originalFile: String,
                              val newFolder: String, val newFile: String)
}
