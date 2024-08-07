package classes

class ChordInterpretationHelper {
    var inChord: Boolean = false
    var lowestPitchIndex: Int = 0
    var duplicatePitchIndexes: MutableList<Int> = mutableListOf()
    var letterInterval: Int = 0
}