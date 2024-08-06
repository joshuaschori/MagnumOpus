package classes

// TODO @Serializable
class Accidental(var type: String) {
    val symbol: String = when (type) {
        "doubleflat" -> "\uD834\uDD2B"
        "flat" -> "♭"
        "natural" -> ""
        "naturalneeded" -> "♮"
        "sharp" -> "♯"
        "doublesharp" -> "\uD834\uDD2A"
        else -> "error"
    }

    /*

    val accidentalValue: Int = when (type) {
        "doubleflat" -> -2
        "flat" -> -1
        "natural" -> 0
        "sharp" -> 1
        "doublesharp" -> 2
        else -> 0
    }

    fun lower(): classes.Accidental {
        val newAccidental: classes.Accidental = when (type) {
            "flat" -> classes.Accidental("doubleflat")
            "natural" -> classes.Accidental("flat")
            "sharp" -> classes.Accidental("natural")
            "doublesharp" -> classes.Accidental("sharp")
            else -> classes.Accidental(type)
        }
        return newAccidental
    }

    fun raise(): classes.Accidental {
        val newAccidental: classes.Accidental = when (type) {
            "doubleflat" -> classes.Accidental("flat")
            "flat" -> classes.Accidental("natural")
            "natural" -> classes.Accidental("sharp")
            "sharp" -> classes.Accidental("doublesharp")
            else -> classes.Accidental(type)
        }
        return newAccidental
    }

     */
}