package io.beyonnex.anagramapp

const val MAX_INPUT_LENGTH = 60
private const val ALPHABET_SIZE = 26
private const val MAX_SUPPORTED_CHAR_CODE = 127

object AnagramUtil {

    /**
     * returns the frequencies of letters of the given string as a list of Ints,
     * where the index represents the position of the letter in the alphabet
     * and the value represents its frequency.
     */
    fun histogramOf(str: String): List<Int> {
        val histogram = IntArray(ALPHABET_SIZE)

        str.toCharArray().forEach {
            histogram[it - 'a']++
        }

        return histogram.toList()
    }

    /**
     * Returns the given string by removing non-alphabetic characters and turning to lowercase
     */
    fun normalize(str: String): String =
        str.replace("[^a-zA-Z]".toRegex(), "")
            .lowercase()

    fun validateInput(str: String) {
        if (str.length > MAX_INPUT_LENGTH) {
            throw ValidationException("Input shouldn't exceed $MAX_INPUT_LENGTH characters")
        }

        str.chars().filter { it > MAX_SUPPORTED_CHAR_CODE }.findAny().ifPresent {
            throw ValidationException("Only ASCII characters are allowed")
        }
    }
}
