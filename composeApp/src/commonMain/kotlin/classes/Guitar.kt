package classes

import androidx.compose.ui.graphics.Color
import settings

data class Guitar(
    var fretMemory: MutableList<FretMemory> = mutableListOf(),
    val isDefaultGuitar: Boolean = false
) {
    companion object {
        const val MIN_NUMBER_OF_STRINGS = 4
        const val MAX_NUMBER_OF_STRINGS = 8
    }
    val companion = Companion
    val numberOfFrets: Int = settings.getInt("number of frets", 15)
    val numberOfStrings: Int = settings.getInt("number of strings", 6)
    val strings: List<GuitarString> = (0..< numberOfStrings).map {
        val stringIndex = numberOfStrings - it
        val defaultValue = when (stringIndex) {
            6 -> 16
            5 -> 21
            4 -> 26
            3 -> 31
            2 -> 35
            1 -> 40
            else -> 0
        }
        GuitarString(Pitch(settings.getInt("$stringIndex string tuning", defaultValue)))
    }
    val lineColor: Color = Color.Black
    val lineThickness: Float = 1f
    val fretThickness: Float = 1f
    val fretboardLength: Float = 500f * (numberOfFrets.toFloat() / 12)
    val stringSpacing: Float = 30f
    val fretSpacing: Float = fretboardLength / numberOfFrets
    val fretMarkers: List<Int> = listOf(3,5,7,9,12,15,17,19,21,24)
    val fretMarkerSize: Float = 6f
    val frettedNoteSize: Float = stringSpacing / 2f * 0.9f
    val unfrettedSize: Float = frettedNoteSize / 2f
    val stringLocations: List<Float> = (1..numberOfStrings).map {
        stringSpacing * (it - 1)
    }
    val fretLocations: List<Float> = (0..numberOfFrets).map {
        (0f - fretSpacing * 0.5f) + (fretSpacing * it)
    }
    val fretSelectionRadius = 14f
    var intervalDisplayMemory: ChordMemory = ChordMemory()
    private val currentTuning: List<Pitch> = (0..< numberOfStrings).map {
        Pitch(strings[it].tuning.midiValue)
    }
    private val currentTuningInterpretation = ChordInterpretation(currentTuning[0], currentTuning[0], currentTuning)
    val currentTuningNoteNames: List<String> = (0..< currentTuningInterpretation.chosenPitches.size).map {
        if (currentTuningInterpretation.chosenPitches[it].hasNatural) {
            currentTuningInterpretation.chosenPitches[it].naturalReading.name
        }
        else {
            currentTuningInterpretation.chosenPitches[it].chosenReading.name
        }
    }
    var chordInterpretationMemory: ChordMemory = ChordMemory()

    init {
        // add default open string hidden values to fretMemory for each string
        if (isDefaultGuitar) {
            repeat(MAX_NUMBER_OF_STRINGS) { stringIndex ->
                fretMemory.add(
                    FretMemory(
                        x = stringSpacing * stringIndex,
                        y = 0f - fretSpacing * 0.5f,
                        fretSelected = 0
                    )
                )
            }
        }
    }
}