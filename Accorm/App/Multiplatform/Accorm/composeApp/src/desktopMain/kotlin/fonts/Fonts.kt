package fonts

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import java.io.File

class Fonts() {
//    Specify Font Locations for Fonts throughout application
    val poppins = FontFamily(
        Font(
            file = File("src/main/res/font/poppins_thin.ttf"),
            weight = FontWeight.Thin
        ),
        Font(
            file = File("src/main/res/font/poppins_light.ttf"),
            weight = FontWeight.Light
        ),
        Font(
            file = File("src/main/res/font/poppins_regular.ttf"),
            weight = FontWeight.Normal
        ),
        Font(
            file = File("src/main/res/font/poppins_semibold.ttf"),
            weight = FontWeight.SemiBold
        ),
        Font(
            file = File("src/main/res/font/poppins_bold.ttf"),
            weight = FontWeight.Bold
        )
    )

    val lexend = FontFamily(
        Font(
            file = File("src/main/res/font/lexend_thin.ttf"),
            weight = FontWeight.Thin
        ),
        Font(
            file = File("src/main/res/font/lexend_regular.ttf"),
            weight = FontWeight.Normal
        ),
        Font(
            file = File("src/main/res/font/lexend_bold.ttf"),
            weight = FontWeight.Bold
        )
    )
}