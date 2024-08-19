package classes

class Chord(pitches: MutableList<Pitch>) {
    val chordInterpretationsList: MutableList<ChordInterpretation> = mutableListOf()
    var chosenInterpretationIndex: Int = 0
    var chosenChordName: String = ""
    var chosenChordExtensionsPrefix: String = ""
    var chosenChordExtensions: String = ""
    var sortedInterpretationList: List<ChordInterpretation> = listOf()

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

        // sort chordInterpretationsList to determine ranking of chord readings by relevancy score
        sortedInterpretationList = chordInterpretationsList.sortedByDescending { it.relevancyScore }

    }
}

fun Chord.fullChordName(): String {
    return this.chosenChordName + this.chosenChordExtensionsPrefix + this.chosenChordExtensions
}