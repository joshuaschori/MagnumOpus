// TODO @Serializable
// TODO contain pitches in chord interpretation helper??? get rid of inUpperOctave? or not?
class ChordInterpretationHelper() {
    var inChord: Boolean = false
    var lowestPitchIndex: Int = 0
    var duplicatePitchIndexes: MutableList<Int> = mutableListOf()
    var letterInterval: Int = 0
}