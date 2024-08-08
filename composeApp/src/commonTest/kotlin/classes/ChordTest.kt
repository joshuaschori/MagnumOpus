package classes

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ChordTest {

    @Test
    fun cMajor() {
        val cMajor = Chord(
            pitches = mutableListOf(
                Pitch(0),
                Pitch(4),
            )
        )
        assertEquals(
            "C", cMajor.fullChordName()
        )
    }

    @Test
    fun fMajor() {
        val fMajor = Chord(
            pitches = mutableListOf(
                Pitch(5),
                Pitch(9),
                Pitch(12),
            )
        )
        assertEquals(
            "F", fMajor.fullChordName()
        )
    }

    @Test
    fun cMajorAdd11() {
        val cMajor = Chord(
            pitches = mutableListOf(
                Pitch(0),
                Pitch(4),
                Pitch(5),
            )
        )
        assertEquals(
            "Cadd11", cMajor.fullChordName()
        )
    }

    @Test
    fun cMajorMajor7with2Es() {
        
    }

}