// TODO @Serializable
class Accidental(var type: String) {
    val symbol: String = when (type) {
        "doubleflat" -> "♭♭"
        "flat" -> "♭"
        "natural" -> ""
        "sharp" -> "♯"
        "doublesharp" -> "♯♯"
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

    fun lower(): Accidental {
        val newAccidental: Accidental = when (type) {
            "flat" -> Accidental("doubleflat")
            "natural" -> Accidental("flat")
            "sharp" -> Accidental("natural")
            "doublesharp" -> Accidental("sharp")
            else -> Accidental(type)
        }
        return newAccidental
    }

    fun raise(): Accidental {
        val newAccidental: Accidental = when (type) {
            "doubleflat" -> Accidental("flat")
            "flat" -> Accidental("natural")
            "natural" -> Accidental("sharp")
            "sharp" -> Accidental("doublesharp")
            else -> Accidental(type)
        }
        return newAccidental
    }

     */
}