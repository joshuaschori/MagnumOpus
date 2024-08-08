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
    fun cMajorAdd13() {
        val cMajorAdd13 = Chord(
            pitches = mutableListOf(
                Pitch(0),
                Pitch(4),
                Pitch(7),
            )
        )
        assertEquals(
            "Cadd113", cMajorAdd13.fullChordName()
        )
    }

    @Test
    fun cMajorMajor7with2Es() {
        val cMajorMajor7with2Es = Chord(
            pitches = mutableListOf(
                Pitch(0),
                Pitch(4),
                Pitch(7),
                Pitch(11),
                Pitch(16),
            )
        )
        assertEquals(
            "Cmaj7", cMajorMajor7with2Es.fullChordName()
        )

    }

}