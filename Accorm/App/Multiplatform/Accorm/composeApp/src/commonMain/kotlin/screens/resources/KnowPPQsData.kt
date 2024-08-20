package screens.resources

fun KnowPapersBySubject(subject: String): List<String> {
    return when (subject) {
        "Accounting" -> listOf(
            "11",
            "12",
            "13",
            "21",
            "22",
            "23"
        )
        "Biology" -> listOf(
            "21",
            "22",
            "23",
            "41",
            "42",
            "43",
            "61",
            "62",
            "63"
        )
        "Chemistry" -> listOf(
            "21",
            "22",
            "23",
            "41",
            "42",
            "43",
            "61",
            "62",
            "63"
        )
        "Physics" -> listOf(
            "21",
            "22",
            "23",
            "41",
            "42",
            "43",
            "61",
            "62",
            "63"
        )
        "CS" -> listOf(
            "11",
            "12",
            "13",
            "21",
            "22",
            "23"
        )
        "Maths" -> listOf(
            "21",
            "22",
            "23",
            "41",
            "42",
            "43"
        )
        "FLE" -> listOf(
            "11",
            "12",
            "13",
            "21",
            "22",
            "23"
        )
        "ESL" -> listOf(
            "11",
            "12",
            "13",
            "21",
            "22",
            "23",
            "31",
            "32",
            "33"
        )
        "Islamiyat" -> listOf(
            "11",
            "12",
            "13",
            "21",
            "22",
            "23"
        )
        "History" -> listOf(
            "11",
            "12",
            "13"
        )
        else -> listOf(
            "21",
            "22",
            "23"
        )
    }
}

fun KnowYearsBySubject(subject: String): IntRange {
    return when(subject) {
        else -> 2020..2023
    }
}

fun KnowSessionsBySubject(subject: String): List<String> {
    return when(subject) {
        else -> listOf("May/June", "Oct/Nov")
    }
}