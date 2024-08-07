package classes

class Accidental(var type: String) {
    val symbol: String = when (type) {
        "doubleflat" -> "\uD834\uDD2B"
        "flat" -> "♭"
        "natural" -> ""
        "naturalneeded" -> "♮"
        "sharp" -> "♯"
        "doublesharp" -> "\uD834\uDD2A"
        else -> "error"
    }
}