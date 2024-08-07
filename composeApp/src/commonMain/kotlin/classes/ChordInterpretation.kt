package classes

// TODO create chord tests
// food for thought, when adding score based on intervals:
// when considering an extended chord with a missing 5th (C E X Bb D F A)
// vs the same pitch classes spelled without any missing 5th (Bb D F A C E),
// if the C and E are lower octaves, it should be C13
// if the Bb is lower than the C, it should be Bbmaj9(#11)
// (A D F) should be D chord first inversion
// (G D F) should be G7(no3) not Gm7(no3)
// (G F A) should be G7sus2
// (G F A C) should be Gsus9 or G9sus4? (abbreviation for G7sus4(9), a sus4 chord with 7 and 9)
// C# E G Bb for C# diminished, and C# E G# A# for C#m6
// G Bb D F# for Gm(maj7)
// C E G B B should be Cmaj7 not Em/C
// C F A C Eb G should probably be F9/C not Cm6add11
// test with two unison notes, one note going chromatically up to octave
// chords without 5 but with 3rd and 7th should still be pretty relevant
// should C G B D F A be C13(no 3) or G13 ??? suspended ??? if second / fourth is below fifth / seventh? prob C sus
// should be Eb minor and not D# minor, because D# minor is the relative minor of B#
// should be first inversion major chord, not minor chord with a b6
// TODO chord's conceptual structure vs intervallic structure
// TODO classical vs jazz vs etc options
// TODO advanced settings to hide things for first time user
// TODO polychords
// TODO bass notes, slash chords, inversions
// TODO bonus score for designated chord structures?
// TODO "sets" ( 0 1 7 ) ( 0 6 7 ) etc

// TODO subtract relevancy for dissonant intervals?
// TODO for example, if you have a second, the lower in pitch it is, the less relevant the chord interpretation becomes
// TODO fullscore and halfscore scaled based on midivalue in a way that's more spread out across the full "score" range (120 and 60)

// TODO move chosenReading idea into a pitchInterpretations variable in chord that keeps track of the root,
// TODO and possible natural, sharp, flat interpretations, like a toggle or something

class ChordInterpretation(
    val root: Pitch,
    val bassNote: Pitch,
    val pitches: List<Pitch>
) {
    companion object {
        // constants for relevancy score
        const val OCTAVE_SCORING_FACTOR: Float = 0.5f
        const val DUPLICATE_SCORING_FACTOR: Float = 0.5f
        const val FULL_SCORE = 128.0f
        // TODO?: const val HALF_SCORE = 64.0f
        const val BASS_NOTE_BONUS = 128.0f

        // chromatic interval values
        const val PERFECT_UNISON: Int = 0
        const val MINOR_SECOND: Int = 1
        const val MAJOR_SECOND: Int = 2
        const val AUGMENTED_SECOND: Int = 3
        const val MINOR_THIRD: Int = 3
        const val MAJOR_THIRD: Int = 4
        const val PERFECT_FOURTH: Int = 5
        const val AUGMENTED_FOURTH: Int = 6
        const val DIMINISHED_FIFTH: Int = 6
        const val PERFECT_FIFTH: Int = 7
        const val AUGMENTED_FIFTH: Int = 8
        const val MINOR_SIXTH: Int = 8
        const val MAJOR_SIXTH: Int = 9
        const val AUGMENTED_SIXTH: Int = 10
        const val DIMINISHED_SEVENTH: Int = 9
        const val MINOR_SEVENTH: Int = 10
        const val MAJOR_SEVENTH: Int = 11

        // letter interval values
        const val UNISON: Int = 0
        const val SECOND: Int = 1
        const val THIRD: Int = 2
        const val FOURTH: Int = 3
        const val FIFTH: Int = 4
        const val SIXTH: Int = 5
        const val SEVENTH: Int = 6
    }

    var chosenRoot: Pitch = root.copy()
    val chosenPitches: List<Pitch> = pitches.map { it.copy() }
    val intervals: List<ChordInterpretationHelper> = (0..11).map {
        ChordInterpretationHelper()
    }
    var relevancyScore: Float = 0f
    var chordQuality: String = ""
    var chordType: String = ""
    var chordName: String = ""
    var extensionsPrefix: String = ""
    var extensions: List<String> = listOf()

    init {
        for ((index, pitch) in chosenPitches.withIndex()) {
            val interval: Int = pitch.intervalFrom(root)

            if (!intervals[interval].inChord) {
                intervals[interval].inChord = true
                intervals[interval].lowestPitchIndex = index
            }
            else if (pitches[index].midiValue < pitches[intervals[interval].lowestPitchIndex].midiValue) {
                intervals[interval].duplicatePitchIndexes.add(intervals[interval].lowestPitchIndex)
                intervals[interval].lowestPitchIndex = index
            }
            else {
                intervals[interval].duplicatePitchIndexes.add(index)
            }

        }

        // default pairings of chromatic intervals with letter intervals, updated later when necessary
        intervals[PERFECT_UNISON].letterInterval = UNISON
        intervals[MINOR_SECOND].letterInterval = SECOND
        intervals[MAJOR_SECOND].letterInterval = SECOND
        intervals[MINOR_THIRD].letterInterval = THIRD
        intervals[MAJOR_THIRD].letterInterval = THIRD
        intervals[PERFECT_FOURTH].letterInterval = FOURTH
        intervals[AUGMENTED_FOURTH].letterInterval = FOURTH
        intervals[PERFECT_FIFTH].letterInterval = FIFTH
        intervals[MINOR_SIXTH].letterInterval = SIXTH
        intervals[MAJOR_SIXTH].letterInterval = SIXTH
        intervals[MINOR_SEVENTH].letterInterval = SEVENTH
        intervals[MAJOR_SEVENTH].letterInterval = SEVENTH

        fun applyRelevancy(interval: Int, score: Float) {

            relevancyScore += score - chosenPitches[intervals[interval].lowestPitchIndex].midiValue * OCTAVE_SCORING_FACTOR

            if (intervals[interval].duplicatePitchIndexes.isNotEmpty()) {
                for (index in intervals[interval].duplicatePitchIndexes) {
                    relevancyScore += ( score - chosenPitches[index].midiValue * OCTAVE_SCORING_FACTOR ) * DUPLICATE_SCORING_FACTOR
                }
            }

        }

        fun applyExtensions(addOrMod: String, extensionsToOmit: List<Int> = listOf()) {

            val extensionsToCheck: List<Int> = listOf(1,2,3,5,6,8,10)
            var extensionNeeded: Boolean = false
            var listOfExtensions: List<Int> = listOf()

            for (extension in extensionsToCheck) {
                if (intervals[extension].inChord && extension !in extensionsToOmit) {
                    extensionNeeded = true
                    listOfExtensions = listOfExtensions + extension
                }
            }

            if (extensionNeeded) {
                if (DIMINISHED_FIFTH in listOfExtensions &&
                    chordQuality != "diminished" &&
                    chordQuality != "augmented" &&
                    !intervals[PERFECT_FIFTH].inChord
                    ) {
                    intervals[DIMINISHED_FIFTH].letterInterval = FIFTH

                    if (addOrMod == "mod") {
                        extensions = extensions + "♭5"
                    }
                    if (addOrMod == "add") {
                        extensionsPrefix += "♭5"
                    }
                }
                if (MINOR_SECOND in listOfExtensions) {
                    extensions = extensions + "♭9"
                }
                if (MAJOR_SECOND in listOfExtensions && chordQuality != "suspended2") {
                    if (addOrMod == "mod" &&
                        // unused possibility to leave out of 9 chords:
                        // (intervals[perfectFourth].inChord || intervals[majorSixth].inChord) &&
                        (intervals[MINOR_SECOND].inChord ||
                                ( intervals[AUGMENTED_SECOND].inChord) && intervals[AUGMENTED_SECOND].letterInterval == SECOND)
                        ) {
                        extensions = extensions + "♮9"
                    }
                    else if (addOrMod == "add" && !intervals[MAJOR_SIXTH].inChord) {
                        if (intervals[MINOR_SECOND].inChord || intervals[AUGMENTED_SECOND].inChord) {
                            extensions = extensions + "♮9"
                        }
                        else {
                            extensions = extensions + "9"
                        }
                    }
                }
                if (AUGMENTED_SECOND in listOfExtensions) {
                    if (intervals[MAJOR_THIRD].inChord) {
                        extensions = extensions + "♯9"
                        // unused possible subjective reading:
                        // intervals[augmentedSecond].letterInterval = second
                    }
                }
                if (PERFECT_FOURTH in listOfExtensions && chordQuality != "suspended4") {
                    if (addOrMod == "mod" &&
                        intervals[AUGMENTED_FOURTH].inChord &&
                        intervals[AUGMENTED_FOURTH].letterInterval == FOURTH &&
                        // unused possibility to leave out of 11 chords:
                        // intervals[majorSixth].inChord &&
                        chordQuality != "diminished"
                    ) {
                        extensions = extensions + "♮11"
                    }
                    else if (addOrMod == "add") {
                        if (intervals[AUGMENTED_FOURTH].inChord && intervals[AUGMENTED_FOURTH].letterInterval == FOURTH) {
                            extensions = extensions + "♮11"
                        }
                        else {
                            extensions = extensions + "11"
                        }
                    }
                }
                if (AUGMENTED_FOURTH in listOfExtensions &&
                    chordQuality != "diminished" &&
                    (intervals[PERFECT_FIFTH].inChord || chordQuality == "augmented")
                    ) {
                    extensions = extensions + "♯11"
                }
                if (MINOR_SIXTH in listOfExtensions) {
                    if (addOrMod == "mod" && intervals[PERFECT_FIFTH].inChord) {
                        extensions = extensions + "♭13"
                    }
                    else if (addOrMod == "add" && intervals[PERFECT_FIFTH].inChord && intervals[MAJOR_SIXTH].inChord) {
                        extensions = extensions + "♭13"
                    }
                }
                if (AUGMENTED_SIXTH in listOfExtensions) {
                    if (intervals[MAJOR_SEVENTH].inChord) {
                        extensions = extensions + "♯13"
                        intervals[AUGMENTED_SIXTH].letterInterval = SIXTH
                    }
                }
            }

            if (addOrMod == "add" && extensions.isNotEmpty()) {
                extensionsPrefix += "add"
            }
        }

        // apply relevancy for root note(s)
        applyRelevancy(PERFECT_UNISON, FULL_SCORE)

        // apply relevancy bonus for bass note being the root note
        if (root.midiValue == bassNote.midiValue) {
            relevancyScore += BASS_NOTE_BONUS
        }

        // determine identity of chord while applying relevancy for appropriate intervals
        if (intervals[MAJOR_THIRD].inChord) { chordQuality = "major"; chordType = ""
            applyRelevancy(MAJOR_THIRD, FULL_SCORE)

            if (intervals[PERFECT_FIFTH].inChord || !intervals[AUGMENTED_FIFTH].inChord) {
                if (intervals[PERFECT_FIFTH].inChord) { applyRelevancy(PERFECT_FIFTH, FULL_SCORE) }

                if (intervals[MAJOR_SEVENTH].inChord) { chordType = "maj7"
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "maj13" }
                    else if (intervals[PERFECT_FOURTH].inChord) { chordType = "maj11" }
                    else if (intervals[MAJOR_SECOND].inChord) { chordType = "maj9" }

                    applyExtensions("mod")
                }
                else if (intervals[MINOR_SEVENTH].inChord) { chordType = "7"
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "13" }
                    else if (intervals[PERFECT_FOURTH].inChord) { chordType = "11" }
                    else if (intervals[MAJOR_SECOND].inChord) { chordType = "9" }

                    applyExtensions("mod")
                }
                else {
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "6"
                        if (intervals[MAJOR_SECOND].inChord) { chordType = "6/9" }
                    }
                    else if (intervals[MINOR_SIXTH].inChord) { chordType = "(♭6)" }

                    applyExtensions("add")
                }
            }

            else if (intervals[AUGMENTED_FIFTH].inChord) { chordQuality = "augmented"; chordType = "+"
                applyRelevancy(AUGMENTED_FIFTH, FULL_SCORE)
                intervals[AUGMENTED_FIFTH].letterInterval = FIFTH

                if (intervals[MAJOR_SEVENTH].inChord) { chordType = "+maj7"
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "+maj13" }
                    else if (intervals[PERFECT_FOURTH].inChord) { chordType = "+maj11" }
                    else if (intervals[MAJOR_SECOND].inChord) { chordType = "+maj9" }

                    applyExtensions("mod")
                }
                else if (intervals[MINOR_SEVENTH].inChord) { chordType = "+7"
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "+13" }
                    else if (intervals[PERFECT_FOURTH].inChord) { chordType = "+11" }
                    else if (intervals[MAJOR_SECOND].inChord) { chordType = "+9" }

                    applyExtensions("mod")
                }
                else {
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "+6"
                        if (intervals[MAJOR_SECOND].inChord) { chordType = "+6/9" }
                    }

                    applyExtensions("add")
                }
            }
        }
        else if (intervals[MINOR_THIRD].inChord) { chordQuality = "minor"; chordType = "m"
            applyRelevancy(MINOR_THIRD, FULL_SCORE)

            if (intervals[PERFECT_FIFTH].inChord || !intervals[DIMINISHED_FIFTH].inChord) {
                if (intervals[PERFECT_FIFTH].inChord) { applyRelevancy(PERFECT_FIFTH, FULL_SCORE) }

                if (intervals[MAJOR_SEVENTH].inChord) {
                    chordType = "m(maj7)"
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "m(maj13)" }
                    else if (intervals[PERFECT_FOURTH].inChord) { chordType = "m(maj11)" }
                    else if (intervals[MAJOR_SECOND].inChord) { chordType = "m(maj9)" }

                    applyExtensions("mod")
                }
                else if (intervals[MINOR_SEVENTH].inChord) { chordType = "m7"
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "m13" }
                    else if (intervals[PERFECT_FOURTH].inChord) { chordType = "m11" }
                    else if (intervals[MAJOR_SECOND].inChord) { chordType = "m9" }

                    applyExtensions("mod")
                }
                else {
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "m6"
                        if (intervals[MAJOR_SECOND].inChord) { chordType = "m6/9" }
                    }
                    else if (intervals[MINOR_SIXTH].inChord) { chordType = "m♭6" }

                    applyExtensions("add")
                }
            }

            else if (intervals[DIMINISHED_FIFTH].inChord) { chordQuality = "diminished"; chordType = "°"
                applyRelevancy(DIMINISHED_FIFTH, FULL_SCORE)
                intervals[DIMINISHED_FIFTH].letterInterval = FIFTH

                if (intervals[MAJOR_SEVENTH].inChord) { chordType = "°maj7"
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "°maj13" }
                    else if (intervals[PERFECT_FOURTH].inChord) { chordType = "°maj11" }
                    else if (intervals[MAJOR_SECOND].inChord) { chordType = "°maj9" }

                    applyExtensions("mod")
                }
                else if (intervals[MINOR_SEVENTH].inChord) { chordType = "ø7"
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "ø13" }
                    else if (intervals[PERFECT_FOURTH].inChord) { chordType = "ø11" }
                    else if (intervals[MAJOR_SECOND].inChord) { chordType = "ø9" }

                    applyExtensions("mod")
                }
                else if (intervals[DIMINISHED_SEVENTH].inChord) { chordType = "°7"; intervals[DIMINISHED_SEVENTH].letterInterval = SEVENTH
                    if (intervals[PERFECT_FOURTH].inChord) { chordType = "°11" }
                    else if (intervals[MAJOR_SECOND].inChord) { chordType = "°9" }

                    applyExtensions("mod")
                }
                else {
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "°6"
                        if (intervals[MAJOR_SECOND].inChord) { chordType = "°6/9" }
                    }
                    else if (intervals[MINOR_SIXTH].inChord) { chordType = "°♭6" }

                    applyExtensions("add")
                }
            }
        }
        else if (intervals[PERFECT_FOURTH].inChord) { chordQuality = "suspended4"; chordType = "sus4"
            // TODO?: applyRelevancy(PERFECT_FOURTH, HALF_SCORE)

            if (intervals[PERFECT_FIFTH].inChord) { applyRelevancy(PERFECT_FIFTH, FULL_SCORE) }

            if (intervals[MAJOR_SEVENTH].inChord) { chordType = "maj7sus4"
                if (intervals[MAJOR_SIXTH].inChord) { chordType = "maj13sus4" }
                else if (intervals[MAJOR_SECOND].inChord) { chordType = "maj9sus4" }

                applyExtensions("mod")
            }
            else if (intervals[MINOR_SEVENTH].inChord) { chordType = "7sus4"
                if (intervals[MAJOR_SIXTH].inChord) { chordType = "13sus4" }
                else if (intervals[MAJOR_SECOND].inChord) { chordType = "9sus4" }

                applyExtensions("mod")
            }
            else {
                if (intervals[MAJOR_SIXTH].inChord) { chordType = "6sus4"
                    if (intervals[MAJOR_SECOND].inChord) { chordType = "6/9sus4" }
                }
                else if (intervals[MINOR_SIXTH].inChord) { chordType = "(♭6)sus4" }

                applyExtensions("add")
            }
        }
        else if (intervals[MAJOR_SECOND].inChord) { chordQuality = "suspended2"; chordType = "sus2"
            // TODO?: applyRelevancy(MAJOR_SECOND, HALF_SCORE)

            if (intervals[PERFECT_FIFTH].inChord) { applyRelevancy(PERFECT_FIFTH, FULL_SCORE) }

            if (intervals[MAJOR_SEVENTH].inChord) { chordType = "maj7sus2"
                if (intervals[MAJOR_SIXTH].inChord) { chordType = "maj13sus2" }
                else if (intervals[PERFECT_FOURTH].inChord) { chordType = "maj11sus2" }

                applyExtensions("mod")
            }
            else if (intervals[MINOR_SEVENTH].inChord) { chordType = "7sus2"
                if (intervals[MAJOR_SIXTH].inChord) { chordType = "13sus2" }
                else if (intervals[PERFECT_FOURTH].inChord) { chordType = "11sus2" }

                applyExtensions("mod")
            }
            else {
                if (intervals[MAJOR_SIXTH].inChord) { chordType = "6sus2" }
                else if (intervals[MINOR_SIXTH].inChord) { chordType = "(♭6)sus2" }

                applyExtensions("add")
            }
        }
        else {
            chordQuality = "other"

            if (intervals[PERFECT_FIFTH].inChord) { chordType = "(no3)"
                applyRelevancy(PERFECT_FIFTH, FULL_SCORE)

                if (intervals[MAJOR_SEVENTH].inChord) { chordType = "maj7(no3)"
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "maj13(no3)" }

                    applyExtensions("mod")
                }
                else if (intervals[MINOR_SEVENTH].inChord) { chordType = "7(no3)"
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "13(no3)" }

                    applyExtensions("mod")
                }
                else {
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "6(no3)" }
                    else if (intervals[MINOR_SIXTH].inChord) { chordType = "(♭6)(no3)" }

                    applyExtensions("add")
                }
            }
            else if (intervals[DIMINISHED_FIFTH].inChord) { chordQuality = "diminished"; chordType = "°(no3)"
                applyRelevancy(DIMINISHED_FIFTH, FULL_SCORE)
                // TODO?: full or half score
                intervals[DIMINISHED_FIFTH].letterInterval = FIFTH

                if (intervals[MAJOR_SEVENTH].inChord) { chordType = "°maj7(no3)"
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "°maj13(no3)" }

                    applyExtensions("mod")
                }
                else if (intervals[MINOR_SEVENTH].inChord) { chordType = "ø7(no3)"
                    if (intervals[MAJOR_SIXTH].inChord) { chordType = "ø13(no3)" }

                    applyExtensions("mod")
                }
                else if (intervals[DIMINISHED_SEVENTH].inChord) { chordType = "°7(no3)"; intervals[DIMINISHED_SEVENTH].letterInterval = SEVENTH
                    applyExtensions("mod")
                }
                else {
                    if (intervals[MINOR_SIXTH].inChord) { chordType = "°♭6(no3)" }

                    applyExtensions("add")
                }
            }
            else if (intervals[MAJOR_SEVENTH].inChord) { chordType = "maj7(no3)"
                // TODO?: applyRelevancy(MAJOR_SEVENTH, HALF_SCORE)
                applyExtensions("mod")
            }
            else if (intervals[MINOR_SEVENTH].inChord) { chordType = "7(no3)"
                // TODO?: applyRelevancy(MINOR_SEVENTH, HALF_SCORE)
                applyExtensions("mod")
            }
            else { chordType = "(no3)"
                if (intervals[MAJOR_SIXTH].inChord) { chordType = "6(no3)" }
                else if (intervals[MINOR_SIXTH].inChord) { chordType = "(♭6)(no3)" }

                applyExtensions("add")
            }
        }

        // determine pitch readings
        if (chosenRoot.hasNatural) {
            chosenRoot.chosenReading = chosenRoot.naturalReading

            for (pitch in chosenPitches) {
                if (pitch.midiValue == chosenRoot.midiValue) {
                    pitch.chosenReading = chosenRoot.chosenReading
                }
                else {
                    pitch.chosenReading = PitchSpelling(
                        pitch.midiValue % 12,
                        chosenRoot.chosenReading.pitchLetter.letterAtInterval(intervals[((pitch.midiValue - chosenRoot.midiValue).mod(12))].letterInterval),
                        Accidental("unknown")
                    )
                }
            }

        }
        else {
            val flatRoot = chosenRoot.copy()
            flatRoot.chosenReading = flatRoot.flatReading
            val flatPitches = chosenPitches.map { it.copy() }
            val flatIncrementedPitches: MutableList<Int> = mutableListOf()

            val sharpRoot = chosenRoot.copy()
            sharpRoot.chosenReading = sharpRoot.sharpReading
            val sharpPitches = chosenPitches.map { it.copy() }
            val sharpIncrementedPitches: MutableList<Int> = mutableListOf()

            var numberOfFlats = 0
            var numberOfSharps = 0

            for (pitch in flatPitches) {
                if (pitch.midiValue == flatRoot.midiValue) {
                    pitch.chosenReading = flatRoot.chosenReading

                    if ((pitch.midiValue % 12) !in flatIncrementedPitches) {
                        numberOfFlats++
                        flatIncrementedPitches.add(pitch.midiValue % 12)
                    }
                }
                else {
                    pitch.chosenReading = PitchSpelling(
                        pitch.midiValue % 12,
                        flatRoot.chosenReading.pitchLetter.letterAtInterval(intervals[((pitch.midiValue - flatRoot.midiValue).mod(12))].letterInterval),
                        Accidental("unknown")
                    )
                    if ((pitch.midiValue % 12) !in flatIncrementedPitches) {
                        if (pitch.chosenReading.accidental.type == "flat") {
                            numberOfFlats++
                        }
                        else if (pitch.chosenReading.accidental.type == "doubleflat") {
                            numberOfFlats += 2
                        }
                        flatIncrementedPitches.add(pitch.midiValue % 12)
                    }
                }
            }

            if (!intervals[PERFECT_FIFTH].inChord && !intervals[DIMINISHED_FIFTH].inChord && !intervals[AUGMENTED_FIFTH].inChord) {
                val impliedFifth: Pitch = Pitch(root.midiValue + 7)

                impliedFifth.chosenReading = PitchSpelling(
                    impliedFifth.midiValue % 12,
                    flatRoot.chosenReading.pitchLetter.letterAtInterval(4),
                    Accidental("unknown")
                )
                when (impliedFifth.chosenReading.accidental.type) {
                    "flat" -> {
                        numberOfFlats++
                    }
                    "doubleflat" -> {
                        numberOfFlats += 2
                    }
                }
            }

            for (pitch in sharpPitches) {
                if (pitch.midiValue == sharpRoot.midiValue) {
                    pitch.chosenReading = sharpRoot.chosenReading

                    if ((pitch.midiValue % 12) !in sharpIncrementedPitches) {
                        numberOfSharps++
                        sharpIncrementedPitches.add(pitch.midiValue % 12)
                    }
                }
                else {
                    pitch.chosenReading = PitchSpelling(
                        pitch.midiValue % 12,
                        sharpRoot.chosenReading.pitchLetter.letterAtInterval(intervals[((pitch.midiValue - sharpRoot.midiValue).mod(12))].letterInterval),
                        Accidental("unknown")
                    )
                    if ((pitch.midiValue % 12) !in sharpIncrementedPitches) {
                        if (pitch.chosenReading.accidental.type == "sharp") {
                            numberOfSharps++
                        }
                        else if (pitch.chosenReading.accidental.type == "doublesharp") {
                            numberOfSharps += 2
                        }
                        sharpIncrementedPitches.add(pitch.midiValue % 12)
                    }
                }
            }

            if (!intervals[PERFECT_FIFTH].inChord && !intervals[DIMINISHED_FIFTH].inChord && !intervals[AUGMENTED_FIFTH].inChord) {
                val impliedFifth: Pitch = Pitch(root.midiValue + 7)

                impliedFifth.chosenReading = PitchSpelling(
                    impliedFifth.midiValue % 12,
                    sharpRoot.chosenReading.pitchLetter.letterAtInterval(4),
                    Accidental("unknown")
                )
                when (impliedFifth.chosenReading.accidental.type) {
                    "sharp" -> {
                        numberOfSharps++
                    }
                    "doublesharp" -> {
                        numberOfSharps += 2
                    }
                }
            }

            if (numberOfFlats < numberOfSharps) {
                chosenRoot = flatRoot
                for ((index, pitch) in chosenPitches.withIndex()) {
                    pitch.chosenReading = flatPitches[index].chosenReading
                }
            }
            else if (numberOfSharps < numberOfFlats) {
                chosenRoot = sharpRoot
                for ((index, pitch) in chosenPitches.withIndex()) {
                    pitch.chosenReading = sharpPitches[index].chosenReading
                }
            }
            else {

                val scaleNumberOfFlats: Int = when (chordQuality) {
                    "minor", "diminished" -> Scale(flatRoot, "minor").numberOfFlats
                    else -> Scale(flatRoot, "major").numberOfFlats
                }
                val scaleNumberOfSharps: Int = when (chordQuality) {
                    "minor", "diminished" -> Scale(sharpRoot, "minor").numberOfSharps
                    else -> Scale(sharpRoot, "major").numberOfSharps
                }

                if (scaleNumberOfSharps < scaleNumberOfFlats) {
                    chosenRoot = sharpRoot
                    for ((index, pitch) in chosenPitches.withIndex()) {
                        pitch.chosenReading = sharpPitches[index].chosenReading
                    }
                }
                // flat is a better default, when considering leading tone for minor scales, etc
                else {
                    chosenRoot = flatRoot
                    for ((index, pitch) in chosenPitches.withIndex()) {
                        pitch.chosenReading = flatPitches[index].chosenReading
                    }
                }
            }
        }

        // combine chosenRoot and chordType to get the name of the chord
        chordName = chosenRoot.chosenReading.name + chordType

    }
}


