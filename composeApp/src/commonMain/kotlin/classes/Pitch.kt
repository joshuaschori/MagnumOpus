package classes

import kotlin.math.floor

data class Pitch(val midiValue: Int) {
    val octave: Int = floor((midiValue / 12).toFloat()).toInt() + 1
    val chromaticValue: Int = midiValue % 12
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