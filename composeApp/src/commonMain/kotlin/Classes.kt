import androidx.compose.ui.graphics.Color
import kotlin.math.floor

// TODO @Serializable
class Scale(val scaleQuality: String = "major") {
    /*

    val numberOfFlats: Int = 0
    val numberOfSharps: Int = 0

     */

}

// TODO @Serializable
class PitchSpelling(val chromaticValue: Int, val pitchLetter: PitchLetter, var accidental: Accidental) {
    var name: String = pitchLetter.name + accidental.symbol

    init {
        if (accidental.type == "unknown") {
            var deducedAccidental: String = "unknown"
            if (chromaticValue == 0) {
                if (pitchLetter.name == "B") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "C") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "D") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 1) {
                if (pitchLetter.name == "B") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "C") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "D") {deducedAccidental = "flat"}
            }
            else if (chromaticValue == 2) {
                if (pitchLetter.name == "C") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "D") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "E") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 3) {
                if (pitchLetter.name == "D") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "E") {deducedAccidental = "flat"}
                else if (pitchLetter.name == "F") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 4) {
                if (pitchLetter.name == "D") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "E") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "F") {deducedAccidental = "flat"}
            }
            else if (chromaticValue == 5) {
                if (pitchLetter.name == "E") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "F") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "G") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 6) {
                if (pitchLetter.name == "E") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "F") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "G") {deducedAccidental = "flat"}
            }
            else if (chromaticValue == 7) {
                if (pitchLetter.name == "F") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "G") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "A") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 8) {
                if (pitchLetter.name == "G") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "A") {deducedAccidental = "flat"}
                //else if (pitchLetter.name == "B") {deducedAccidental = "tripleflat"}
            }
            else if (chromaticValue == 9) {
                if (pitchLetter.name == "G") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "A") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "B") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 10) {
                if (pitchLetter.name == "A") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "B") {deducedAccidental = "flat"}
                else if (pitchLetter.name == "C") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 11) {
                if (pitchLetter.name == "A") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "B") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "C") {deducedAccidental = "flat"}
            }
            val accidentalCopy: Accidental = Accidental(deducedAccidental)
            accidental = accidentalCopy
            name = pitchLetter.name + accidental.symbol
        }
    }
}

// TODO @Serializable
class PitchLetter(val name: String) {
    val letterValue: Int = when (name) {
        "C" -> 0
        "D" -> 1
        "E" -> 2
        "F" -> 3
        "G" -> 4
        "A" -> 5
        "B" -> 6
        else -> 0
    }

    fun letterAtValue(value: Int): String {
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

// TODO @Serializable
data class Pitch(val midiValue: Int) {
    val octave: Int = floor((midiValue / 12).toFloat()).toInt()
    val hasNatural: Boolean = when (midiValue % 12) {
        0,2,4,5,7,9,11 -> true
        1,3,6,8,10 -> false
        else -> false
    }
    val naturalReading: PitchSpelling = when (midiValue % 12) {
        0 -> PitchSpelling(0, PitchLetter("C"), Accidental("natural"))
        2 -> PitchSpelling(2, PitchLetter("D"), Accidental("natural"))
        4 -> PitchSpelling(4, PitchLetter("E"), Accidental("natural"))
        5 -> PitchSpelling(5, PitchLetter("F"), Accidental("natural"))
        7 -> PitchSpelling(7, PitchLetter("G"), Accidental("natural"))
        9 -> PitchSpelling(9, PitchLetter("A"), Accidental("natural"))
        11 -> PitchSpelling(11, PitchLetter("B"), Accidental("natural"))
        else -> PitchSpelling(0, PitchLetter("error"), Accidental("error"))
    }
    val flatReading: PitchSpelling = when (midiValue % 12) {
        0 -> PitchSpelling(0, PitchLetter("D"), Accidental("doubleflat"))
        1 -> PitchSpelling(1, PitchLetter("D"), Accidental("flat"))
        2 -> PitchSpelling(2, PitchLetter("E"), Accidental("doubleflat"))
        3 -> PitchSpelling(3, PitchLetter("E"), Accidental("flat"))
        4 -> PitchSpelling(4, PitchLetter("F"), Accidental("flat"))
        5 -> PitchSpelling(5, PitchLetter("G"), Accidental("doubleflat"))
        6 -> PitchSpelling(6, PitchLetter("G"), Accidental("flat"))
        7 -> PitchSpelling(7, PitchLetter("A"), Accidental("doubleflat"))
        8 -> PitchSpelling(8, PitchLetter("A"), Accidental("flat"))
        9 -> PitchSpelling(9, PitchLetter("B"), Accidental("doubleflat"))
        10 -> PitchSpelling(10, PitchLetter("B"), Accidental("flat"))
        11 -> PitchSpelling(11, PitchLetter("C"), Accidental("flat"))
        else -> PitchSpelling(0, PitchLetter("error"), Accidental("error"))
    }
    val sharpReading: PitchSpelling = when (midiValue % 12) {
        0 -> PitchSpelling(0, PitchLetter("B"), Accidental("sharp"))
        1 -> PitchSpelling(1, PitchLetter("C"), Accidental("sharp"))
        2 -> PitchSpelling(2, PitchLetter("C"), Accidental("doublesharp"))
        3 -> PitchSpelling(3, PitchLetter("D"), Accidental("sharp"))
        4 -> PitchSpelling(4, PitchLetter("D"), Accidental("doublesharp"))
        5 -> PitchSpelling(5, PitchLetter("E"), Accidental("sharp"))
        6 -> PitchSpelling(6, PitchLetter("F"), Accidental("sharp"))
        7 -> PitchSpelling(7, PitchLetter("F"), Accidental("doublesharp"))
        8 -> PitchSpelling(8, PitchLetter("G"), Accidental("sharp"))
        9 -> PitchSpelling(9, PitchLetter("G"), Accidental("doublesharp"))
        10 -> PitchSpelling(10, PitchLetter("A"), Accidental("sharp"))
        11 -> PitchSpelling(11, PitchLetter("A"), Accidental("doublesharp"))
        else -> PitchSpelling(0, PitchLetter("error"), Accidental("error"))
    }
    var chosenReading: PitchSpelling = PitchSpelling(0, PitchLetter("none"), Accidental("none"))

    fun intervalFrom(pitch: Pitch): Int {
        return (midiValue - pitch.midiValue).mod(12)
    }
}