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
    fun cMajorAdd9() {
        val cMajorAdd9 = Chord(
            pitches = mutableListOf(
                Pitch(0),
                Pitch(2),
                Pitch(4),
            )
        )
        assertEquals(
            "Cadd9", cMajorAdd9.fullChordName()
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