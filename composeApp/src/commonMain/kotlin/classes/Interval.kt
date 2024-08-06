package classes

class Interval(name: String) {
    val chromaticInterval = when (name) {
        "unison" -> 0
        "minorSecond" -> 1
        "majorSecond" -> 2
        "minorThird" -> 3
        "majorThird" -> 4
        "fourth" -> 5
        "augmentedFourth" -> 6
        "diminishedFifth" -> 6
        "fifth" -> 7
        "augmentedFifth" -> 8
        "minorSixth" -> 8
        "majorSixth" -> 9
        "minorSeventh" -> 10
        "majorSeventh" -> 11
        else -> 0
    }
    val letterInterval = when(name) {
        "unison" -> 0
        "minorSecond" -> 1
        "majorSecond" -> 1
        "minorThird" -> 2
        "majorThird" -> 2
        "fourth" -> 3
        "augmentedFourth" -> 3
        "diminishedFifth" -> 4
        "fifth" -> 4
        "augmentedFifth" -> 4
        "minorSixth" -> 5
        "majorSixth" -> 5
        "minorSeventh" -> 6
        "majorSeventh" -> 6
        else -> 0
    }
}