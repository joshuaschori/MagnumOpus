package classes

class PitchSpelling(val chromaticValue: Int, val pitchLetter: PitchLetter, var accidental: Accidental = Accidental("unknown")) {
    var name: String = pitchLetter.name + accidental.symbol

    init {
        if (accidental.type == "unknown") {
            var deducedAccidental = "unknown"
            when (chromaticValue) {
                0 -> {
                    when (pitchLetter.name) {
                        "B" -> {deducedAccidental = "sharp"}
                        "C" -> {deducedAccidental = "natural"}
                        "D" -> {deducedAccidental = "doubleflat"}
                    }
                }
                1 -> {
                    when (pitchLetter.name) {
                        "B" -> {deducedAccidental = "doublesharp"}
                        "C" -> {deducedAccidental = "sharp"}
                        "D" -> {deducedAccidental = "flat"}
                    }
                }
                2 -> {
                    when (pitchLetter.name) {
                        "C" -> {deducedAccidental = "doublesharp"}
                        "D" -> {deducedAccidental = "natural"}
                        "E" -> {deducedAccidental = "doubleflat"}
                    }
                }
                3 -> {
                    when (pitchLetter.name) {
                        "D" -> {deducedAccidental = "sharp"}
                        "E" -> {deducedAccidental = "flat"}
                        "F" -> {deducedAccidental = "doubleflat"}
                    }
                }
                4 -> {
                    when (pitchLetter.name) {
                        "D" -> {deducedAccidental = "doublesharp"}
                        "E" -> {deducedAccidental = "natural"}
                        "F" -> {deducedAccidental = "flat"}
                    }
                }
                5 -> {
                    when (pitchLetter.name) {
                        "E" -> {deducedAccidental = "sharp"}
                        "F" -> {deducedAccidental = "natural"}
                        "G" -> {deducedAccidental = "doubleflat"}
                    }
                }
                6 -> {
                    when (pitchLetter.name) {
                        "E" -> {deducedAccidental = "doublesharp"}
                        "F" -> {deducedAccidental = "sharp"}
                        "G" -> {deducedAccidental = "flat"}
                    }
                }
                7 -> {
                    when (pitchLetter.name) {
                        "F" -> {deducedAccidental = "doublesharp"}
                        "G" -> {deducedAccidental = "natural"}
                        "A" -> {deducedAccidental = "doubleflat"}
                    }
                }
                8 -> {
                    when (pitchLetter.name) {
                        "F" -> {deducedAccidental = "triplesharp"}
                        "G" -> {deducedAccidental = "sharp"}
                        "A" -> {deducedAccidental = "flat"}
                    }
                }
                9 -> {
                    when (pitchLetter.name) {
                        "G" -> {deducedAccidental = "doublesharp"}
                        "A" -> {deducedAccidental = "natural"}
                        "B" -> {deducedAccidental = "doubleflat"}
                    }
                }
                10 -> {
                    when (pitchLetter.name) {
                        "A" -> {deducedAccidental = "sharp"}
                        "B" -> {deducedAccidental = "flat"}
                        "C" -> {deducedAccidental = "doubleflat"}
                    }
                }
                11 -> {
                    when (pitchLetter.name) {
                        "A" -> {deducedAccidental = "doublesharp"}
                        "B" -> {deducedAccidental = "natural"}
                        "C" -> {deducedAccidental = "flat"}
                    }
                }
            }
            val accidentalCopy = Accidental(deducedAccidental)
            accidental = accidentalCopy
            name = pitchLetter.name + accidental.symbol
        }
    }
}