package com.github.com.atamanroman.deob

import io.kotlintest.KTestJUnitRunner
import io.kotlintest.specs.FlatSpec
import org.junit.runner.RunWith

@RunWith(KTestJUnitRunner::class)
class ObfuscationDetectorTest : FlatSpec() {

    init {
        "ObfuscationDetector" should "not find anything with empty input" {
            ObfuscationDetector(emptyList()).detected.size shouldBe 0
        }

        "ObfuscationDetector" should "not find anything without obfuscated input" {
            ObfuscationDetector(listOf("/foo", "/bar")).detected.size shouldBe 0
        }

        "ObfuscationDetector" should "find only the obfuscated input" {
            ObfuscationDetector(listOf("/foo", "/bar", "/baz/OBFUSCATED")).detected.size shouldBe 1
        }

        "ObfuscationDetector" should "not care about the markers casing" {
            ObfuscationDetector(listOf("/foo", "/bar", "/baz-OBFUSCATED", "/yolo-obfuscated")).detected.size shouldBe 2
        }

        "ObfuscationDetector" should "accept multiple other suffixes" {
            ObfuscationDetector(listOf("/foo-test"), listOf("test")).detected.size shouldBe 1
            ObfuscationDetector(listOf("/foo-test", "/bar-MARKER"), listOf("test", "marker")).detected.size shouldBe 2
        }

        "ObfuscationDetector" should "require at least one suffix" {
            shouldThrow<IllegalArgumentException> { ObfuscationDetector(emptyList(), emptyList()) }
        }

        "ObfuscationDetector" should "detect a single obfuscated folder" {
            ObfuscationDetector(listOf("/foo-OBFUSCATED")).detected.size shouldBe 1
        }
    }

}