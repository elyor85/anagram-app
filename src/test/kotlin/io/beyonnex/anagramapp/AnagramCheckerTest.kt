package io.beyonnex.anagramapp

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

internal class AnagramCheckerTest : ShouldSpec({
    should("check anagram words") {
        val checker = AnagramChecker()

        checker.areAnagrams("abba", "baab") shouldBe true
        checker.areAnagrams("aabb", "bbaa") shouldBe true
        checker.areAnagrams("abba", "abba") shouldBe true
        checker.areAnagrams("aabb", "aabb") shouldBe true
        checker.areAnagrams("kza", "akz") shouldBe true
        checker.areAnagrams("coronavirus", "carnivorous") shouldBe true
        checker.areAnagrams("abcdefghijklmnopqrstuvwxyz", "zyxwvutsrqponmlkjihgfedcba") shouldBe true
        checker.areAnagrams("", "") shouldBe true

        checker.areAnagrams("x", "y") shouldBe false
        checker.areAnagrams("x", "xx") shouldBe false
        checker.areAnagrams("xy", "xx") shouldBe false
        checker.areAnagrams("aaaa", "aaaab") shouldBe false
        checker.areAnagrams("aabaa", "aaaa") shouldBe false
        checker.areAnagrams("abc", "") shouldBe false
        checker.areAnagrams("", "abc") shouldBe false
    }

    should("check anagrams regardless of non-alphabetic characters") {
        val checker = AnagramChecker()

        checker.areAnagrams("a,b,c", "c'a !b") shouldBe true
        checker.areAnagrams("ab bccd", "db.bc->ca") shouldBe true
        checker.areAnagrams("", "  ") shouldBe true
        checker.areAnagrams(",.\"!= ", "~`@#$%^&*()-=_+[]{}|\\?/<>';:") shouldBe true

        checker.areAnagrams("ab bccd", "db.bc->a") shouldBe false
        checker.areAnagrams("[aa]", "[a]") shouldBe false
        checker.areAnagrams(" a a ", " a ") shouldBe false
    }

    should("check anagrams in case-insensitive manner") {
        val checker = AnagramChecker()

        checker.areAnagrams("aa", "AA") shouldBe true
        checker.areAnagrams("aBc", "Abc") shouldBe true
        checker.areAnagrams("Hello world!", "World,hello!!!") shouldBe true

        checker.areAnagrams("abc", "abcD") shouldBe false
        checker.areAnagrams("ABC", "ABCd") shouldBe false
    }

    should("return learned anagrams for a given string") {
        val checker = AnagramChecker()

        checker.areAnagrams("acm", "ACM")
        checker.areAnagrams("A C M", "acm")
        checker.areAnagrams("a.c.m", "A-cm")
        checker.areAnagrams("x y z", "zyx")

        checker.getLearnedAnagrams("acm") shouldBe setOf("ACM", "A C M", "a.c.m", "A-cm")
        checker.getLearnedAnagrams("a.c.m") shouldBe setOf("acm", "ACM", "A C M", "A-cm")
        checker.getLearnedAnagrams("mca") shouldBe setOf("acm", "ACM", "A C M", "a.c.m", "A-cm")
        checker.getLearnedAnagrams("zyx") shouldBe setOf("x y z")
        checker.getLearnedAnagrams("xyz") shouldBe setOf("x y z", "zyx")
    }

    should("return nothing for not learned anagrams") {
        val checker = AnagramChecker()

        checker.areAnagrams("abc", "cba")

        checker.getLearnedAnagrams("xyz") shouldBe setOf()
        checker.getLearnedAnagrams("abcd") shouldBe setOf()
        checker.getLearnedAnagrams("abc d") shouldBe setOf()
    }

    should("validate input") {
        val checker = AnagramChecker()

        // contains non-ASCII characters
        shouldThrow<ValidationException> {
            checker.areAnagrams("a цццц", "asd")
        }
        shouldThrow<ValidationException> {
            checker.areAnagrams("asd", "a цццц")
        }
        shouldThrow<ValidationException> {
            checker.getLearnedAnagrams("a цццц")
        }

        // the input is too long
        shouldThrow<ValidationException> {
            checker.areAnagrams("1234567890 1234567890 1234567890 1234567890 1234567890 1234567890", "")
        }
        shouldThrow<ValidationException> {
            checker.areAnagrams("", "1234567890 1234567890 1234567890 1234567890 1234567890 1234567890")
        }
        shouldThrow<ValidationException> {
            checker.getLearnedAnagrams("1234567890 1234567890 1234567890 1234567890 1234567890 1234567890")
        }
    }
})
