package com.github.com.atamanroman.deob

import io.kotlintest.KTestJUnitRunner
import io.kotlintest.specs.FlatSpec
import org.junit.runner.RunWith

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
    }
}