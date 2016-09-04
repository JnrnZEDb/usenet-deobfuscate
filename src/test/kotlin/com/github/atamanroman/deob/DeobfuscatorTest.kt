package com.github.atamanroman.deob

import io.kotlintest.KTestJUnitRunner
import io.kotlintest.specs.FlatSpec
import org.junit.runner.RunWith
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@RunWith(KTestJUnitRunner::class)
class DeobfuscatorTest : FlatSpec() {

    val testfolder = DeobfuscatorTest::class.java.getResource("/sample1/baz-OBFUSCATED").file!!

    var cut = Deobfuscator(listOf(testfolder))


    init {
        "Deobfuscator" should "deobfuscates a single folder correctly" {
            cut.deobfuscated.size shouldBe 1
            val entry = cut.deobfuscated.entries.first().value!!
            entry.originalFolder shouldBe "$testfolder"
            entry.originalFile shouldBe "$testfolder/558EEDE8-04C4-4430-A6AA-A62CC6648D94.txt"
            entry.newFile shouldBe "${testfolder.replaceFirst("-OBFUSCATED", "")}/baz.txt"
            entry.newFolder shouldBe "${testfolder.replaceFirst("-OBFUSCATED", "")}"
        }

        "Deobfuscator" should "rename a single folder successfully" {
            val target = prepare()
            val cut = Deobfuscator(listOf(target.toString()))
            cut.deobfuscated.size shouldBe 1
            cut.deobfuscated.entries.first().value should { it != null }

            cut.rename()

            cleanup(target)
        }
    }

    private fun cleanup(target: Path) {
        target.toFile().deleteRecursively()
    }

    private fun prepare(): Path {
        val tmp = Files.createTempDirectory("deob-test")
        File(testfolder).parentFile.copyRecursively(tmp.toFile())
        return tmp.resolve("baz-OBFUSCATED")
    }
}