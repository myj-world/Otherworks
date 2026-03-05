package screens.assets

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class DictionaryResponse(
    val word: String,
    val phonetics: List<Phonetic>? = null,
    val meanings: List<Meaning>
)

@Serializable
data class Phonetic(
    val text: String? = null,
    val audio: String? = null
)

@Serializable
data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>
)

@Serializable
data class Definition(
    val definition: String,
    val example: String? = null,
    val synonyms: List<String>? = null
)

private val json = Json { ignoreUnknownKeys = true }

fun parseDictionaryJson(jsonString: String): List<DictionaryResponse> {
    return json.decodeFromString(jsonString)
}