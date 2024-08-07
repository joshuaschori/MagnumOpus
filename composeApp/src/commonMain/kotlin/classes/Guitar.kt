package classes

import androidx.compose.ui.graphics.Color
import settings

data class Guitar(
    var fretMemory: MutableList<FretMemory> = mutableListOf(),
    val isDefaultGuitar: Boolean = false
) {
    val numberOfFrets: Int = settings.getInt("number of frets", 12)
    val numberOfStrings: Int = settings.getInt("number of strings", 6)
    val strings: MutableList<GuitarString> = mutableListOf()
    val lineColor: Color = Color.Black
    val lineThickness: Float = 1f
    val fretThickness: Float = 1f
    val fretboardLength: Float = 500f * (numberOfFrets / 12)
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

    init {
        // get the tuning of each string from settings
        repeat(numberOfStrings) {index ->
            val stringIndex = numberOfStrings - index
            val defaultValue = when (stringIndex) {
                6 -> 16
                5 -> 21
                4 -> 26
                3 -> 31
                2 -> 35
                1 -> 40
                else -> 0
            }
            strings.add(GuitarString(Pitch(settings.getInt("$stringIndex string tuning", defaultValue))))
        }

        // add default open string hidden values to fretMemory for each string
        if (isDefaultGuitar) {
            repeat(numberOfStrings) {stringIndex ->
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