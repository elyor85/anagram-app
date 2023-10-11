package io.beyonnex.anagramapp

class AnagramChecker {
    private val learnedAnagrams = mutableMapOf<List<Int>, MutableSet<String>>()

    fun areAnagrams(str1: String, str2: String): Boolean {
        AnagramUtil.validateInput(str1)
        AnagramUtil.validateInput(str2)

        val normalStr1 = AnagramUtil.normalize(str1)
        val normalStr2 = AnagramUtil.normalize(str2)

        if (normalStr1.length != normalStr2.length) {
            return false
        }

        val str1Histogram = AnagramUtil.histogramOf(normalStr1)
        val str2Histogram = AnagramUtil.histogramOf(normalStr2)

        if (str1Histogram == str2Histogram) {
            learn(str1Histogram, str1, str2)
            return true
        }

        return false
    }

    fun getLearnedAnagrams(str: String): Set<String> {
        AnagramUtil.validateInput(str)
        val normalStr = AnagramUtil.normalize(str)
        val histogram = AnagramUtil.histogramOf(normalStr)
        return learnedAnagrams.getOrDefault(histogram, setOf()) - setOf(str)
    }

    private fun learn(histogram: List<Int>, vararg strings: String) {
        if (!learnedAnagrams.containsKey(histogram)) {
            learnedAnagrams[histogram] = mutableSetOf()
        }

        learnedAnagrams[histogram]?.addAll(strings)
    }
}
