package classes

class Scale(val root: Pitch, scaleQuality: String = "major") {

    var numberOfFlats: Int = 0
    var numberOfSharps: Int = 0
    private val scaleIntervals: List<Int> = when (scaleQuality) {
        "major" -> listOf(0, 2, 4, 5, 7, 9, 11)
        "minor" -> listOf(0, 2, 3, 5, 7, 8, 10)
        else -> listOf()
    }
    private var scalePitches: List<Pitch> = listOf()

    init {
        for ((index, interval) in scaleIntervals.withIndex()) {
            scalePitches += Pitch(root.midiValue + interval)

            scalePitches[index].chosenReading = PitchSpelling(
                scalePitches[index].chromaticValue,
                root.chosenReading.pitchLetter.letterAtInterval(index),
                Accidental("unknown")
            )

            when (scalePitches[index].chosenReading.accidental.type) {
                "doubleflat" -> numberOfFlats += 2
                "flat" -> numberOfFlats++
                "sharp" -> numberOfSharps++
                "doublesharp" -> numberOfSharps += 2
            }
        }
    }
}