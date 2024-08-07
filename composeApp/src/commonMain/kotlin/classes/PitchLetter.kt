package classes

class PitchLetter(val name: String) {
    private val letterValue: Int = when (name) {
        "C" -> 0
        "D" -> 1
        "E" -> 2
        "F" -> 3
        "G" -> 4
        "A" -> 5
        "B" -> 6
        else -> 0
    }

    private fun letterAtValue(value: Int): String {
        return when (value) {
            0 -> "C"
            1 -> "D"
            2 -> "E"
            3 -> "F"
            4 -> "G"
            5 -> "A"
            6 -> "B"
            else -> "error"
        }
    }

    fun letterAtInterval(interval: Int): PitchLetter {
        var workingValue: Int = letterValue + interval
        if (workingValue > 6) {
            workingValue -= 7
        }
        val workingName = letterAtValue(workingValue)
        return PitchLetter(workingName)
    }
}