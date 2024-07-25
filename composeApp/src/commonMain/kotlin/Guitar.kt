import androidx.compose.ui.graphics.Color

// TODO @Serializable
class FretMemory(var fretSelected: Int, var x: Float, var y: Float) {
    var visible = false
}

// TODO @Serializable
class GuitarString(val tuning: Pitch) {
    val maxThickness: Float = 6.0f
    val minThickness: Float = 1.0f
    val thicknessIncrement: Float = (maxThickness - minThickness) / 40f
    val thickness: Float = when (tuning.midiValue) {
        in 0 .. 39 -> (maxThickness - (tuning.midiValue * thicknessIncrement))
        else -> minThickness
    }
}

// TODO @Serializable
data class Guitar(
    val numberOfFrets: Int,
    // TODO convert mutable lists to regular lists
    var fretMemory: MutableList<FretMemory> = mutableListOf(),
    val isDefaultGuitar: Boolean = false
) {
    val lineColor: Color = Color.Black
    val lineThickness: Float = 1f
    val fretThickness: Float = 1f
    val fretboardLength: Float = 500f
    val stringSpacing: Float = 30f
    val fretSpacing: Float = fretboardLength / numberOfFrets
    val fretMarkers: List<Int> = listOf(3,5,7,9,12,15,17,19,21,24)
    val fretMarkerSize: Float = 6f
    val frettedNoteSize: Float = stringSpacing / 2f * 0.9f
    val unfrettedSize: Float = frettedNoteSize / 2f
    val strings: MutableList<GuitarString> = mutableListOf(
        GuitarString(Pitch(16)),
        GuitarString(Pitch(21)),
        GuitarString(Pitch(26)),
        GuitarString(Pitch(31)),
        GuitarString(Pitch(35)),
        GuitarString(Pitch(40))
    )
    val numberOfStrings: Int = strings.size
    val stringLocations: List<Float> = (1..numberOfStrings).map {
        stringSpacing * (it - 1)
    }
    val fretLocations: List<Float> = (0..numberOfFrets).map {
        (0f - fretSpacing * 0.5f) + (fretSpacing * it)
    }
    val fretSelectionRadius = 14f

    init {
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