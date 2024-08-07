package classes

class Chord(pitches: MutableList<Pitch>) {
    val chordInterpretationsList: MutableList<ChordInterpretation> = mutableListOf()
    var chosenInterpretationIndex: Int = 0
    var chosenChordName: String = ""
    var chosenChordExtensionsPrefix: String = ""
    var chosenChordExtensions: String = ""

    init {

        // iterate through possible interpretations using each pitch as the root note
        for (root in pitches) {

            var tempBassNote: Pitch = root

            for (pitch in pitches) {
                if (pitch.midiValue < tempBassNote.midiValue) {
                    tempBassNote = pitch
                }
            }

            val bassNote: Pitch = tempBassNote.copy()

            // add classes.ChordInterpretation to chordInterpretationsList
            chordInterpretationsList.add(ChordInterpretation(root = root, bassNote = bassNote, pitches = pitches))

        }

        // determine chord spelling, which is the chord interpretation with highest relevancy score
        var tempScore = 0f
        var tempIndex = 0

        for ((index, chord) in chordInterpretationsList.withIndex()) {
            if (chord.relevancyScore > tempScore) {
                tempScore = chord.relevancyScore
                tempIndex = index
            }
        }

        chosenInterpretationIndex = tempIndex
        chosenChordName = chordInterpretationsList[chosenInterpretationIndex].chordName
        chosenChordExtensionsPrefix = chordInterpretationsList[chosenInterpretationIndex].extensionsPrefix
        chosenChordExtensions = chordInterpretationsList[chosenInterpretationIndex].extensions.joinToString(",")

    }
}