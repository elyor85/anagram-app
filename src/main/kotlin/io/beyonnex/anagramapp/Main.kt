package io.beyonnex.anagramapp

private val anagramChecker = AnagramChecker()

fun main() {
    while(true) {
        when(selectFeature()) {
            1 -> {
                val text1 = readText("first")
                val text2 = readText("second")
                try {
                    val result = anagramChecker.areAnagrams(text1, text2)
                    pressToContinue("These texts are ${if (!result) "NOT " else ""}anagrams.")
                } catch (e: Exception) {
                    pressToContinue(e.message)
                }
            }

            2 -> {
                val text = readText()
                try {
                    val anagrams = anagramChecker.getLearnedAnagrams(text)
                    pressToContinue("Learned anagrams for this text: $anagrams")
                } catch (e: Exception) {
                    pressToContinue(e.message)
                }
            }

            9 -> break
        }
    }
}

private fun selectFeature(): Int {
    var option = 0

    while(option == 0) {
        println("Please, select an option:\n" +
            "\t1. Check if two texts are anagrams\n" +
            "\t2. Show learned anagrams for a given text\n" +
            "\t9. Exit")

        try {
            option = readln().toInt()
        } catch (_: Exception) {}

        if (option !in setOf(1, 2, 9)) {
            pressToContinue("Invalid input.")
        }
    }

    return option
}

private fun readText(inputIndex: String = ""): String {
    println("Please, enter $inputIndex text (only ASCII characters are accepted):")
    return readln()
}

private fun pressToContinue(msg: String?) {
    println("$msg\nPress Enter to continue...")
    readln()
}
