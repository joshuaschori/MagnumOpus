package classes

class ChordMemory {
    val pitchClassIntValues: MutableList<Int> = mutableListOf()
    val noteNames: MutableList<String> = mutableListOf()
    var root: Pitch = Pitch(-1)
}