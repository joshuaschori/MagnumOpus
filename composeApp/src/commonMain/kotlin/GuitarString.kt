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