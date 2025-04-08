package viewmodels

object ColourProvider {
    private var theme = ""

    fun setTheme(newTheme: String) {
        theme = newTheme
    }

    val colour1: String
        get() {
            return when (theme) {
                "" -> "#8A8AFA"
                "black" -> "#000000"
                "blue" -> "#3da5ff"
                "green" -> "#4dd979"
                "red" -> "#f982a3"
                else -> "#8A8AFA"
            }
        }

    val colour2: String
        get() {
            return when (theme) {
                "" -> "#C48AFA"
                "black" -> "#5b5b5b"
                "blue" -> "#4ffffc"
                "green" -> "#0bb9b1"
                "red" -> "#f866db"
                else -> "#C48AFA"
            }
        }
}