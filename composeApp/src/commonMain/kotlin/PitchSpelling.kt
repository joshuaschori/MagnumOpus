// TODO @Serializable
class PitchSpelling(val chromaticValue: Int, val pitchLetter: PitchLetter, var accidental: Accidental = Accidental("unknown")) {
    var name: String = pitchLetter.name + accidental.symbol

    init {
        if (accidental.type == "unknown") {
            var deducedAccidental: String = "unknown"
            if (chromaticValue == 0) {
                if (pitchLetter.name == "B") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "C") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "D") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 1) {
                if (pitchLetter.name == "B") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "C") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "D") {deducedAccidental = "flat"}
            }
            else if (chromaticValue == 2) {
                if (pitchLetter.name == "C") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "D") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "E") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 3) {
                if (pitchLetter.name == "D") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "E") {deducedAccidental = "flat"}
                else if (pitchLetter.name == "F") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 4) {
                if (pitchLetter.name == "D") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "E") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "F") {deducedAccidental = "flat"}
            }
            else if (chromaticValue == 5) {
                if (pitchLetter.name == "E") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "F") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "G") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 6) {
                if (pitchLetter.name == "E") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "F") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "G") {deducedAccidental = "flat"}
            }
            else if (chromaticValue == 7) {
                if (pitchLetter.name == "F") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "G") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "A") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 8) {
                if (pitchLetter.name == "G") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "A") {deducedAccidental = "flat"}
                //else if (pitchLetter.name == "B") {deducedAccidental = "tripleflat"}
            }
            else if (chromaticValue == 9) {
                if (pitchLetter.name == "G") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "A") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "B") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 10) {
                if (pitchLetter.name == "A") {deducedAccidental = "sharp"}
                else if (pitchLetter.name == "B") {deducedAccidental = "flat"}
                else if (pitchLetter.name == "C") {deducedAccidental = "doubleflat"}
            }
            else if (chromaticValue == 11) {
                if (pitchLetter.name == "A") {deducedAccidental = "doublesharp"}
                else if (pitchLetter.name == "B") {deducedAccidental = "natural"}
                else if (pitchLetter.name == "C") {deducedAccidental = "flat"}
            }
            val accidentalCopy: Accidental = Accidental(deducedAccidental)
            accidental = accidentalCopy
            name = pitchLetter.name + accidental.symbol
        }
    }
}