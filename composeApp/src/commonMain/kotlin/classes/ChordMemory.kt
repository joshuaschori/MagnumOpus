package classes

class ChordMemory {
    val pitchClassIntValues: MutableList<Int> = mutableListOf()
    val noteNames: MutableList<String> = mutableListOf()
    var root: Pitch = Pitch(-1)
    val checkboxMemory: MutableList<Boolean> = mutableListOf(
        false, false, false, false, false, false, false, false, false, false, false, false
    )
}