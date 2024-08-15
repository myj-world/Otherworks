package fonts

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.platform.SystemFont
//import java.io.File

@OptIn(ExperimentalTextApi::class)
class Fonts() {
//    Specify Font Locations for Fonts throughout application
val poppins = FontFamily(
        SystemFont("Poppins Thin", FontWeight.Thin),
        SystemFont("Poppins Light", FontWeight.Light),
        SystemFont("Poppins Regular", FontWeight.Normal),
        SystemFont("Poppins SemiBold", FontWeight.SemiBold),
        SystemFont("Poppins Bold", FontWeight.Bold)
//        Font(
//            file = File("src/main/res/font/poppins_thin.ttf"),
//            weight = FontWeight.Thin
//        ),
//        Font(
//            file = File("src/main/res/font/poppins_light.ttf"),
//            weight = FontWeight.Light
//        ),
//        Font(
//            file = File("src/main/res/font/poppins_regular.ttf"),
//            weight = FontWeight.Normal
//        ),
//        Font(
//            file = File("src/main/res/font/poppins_semibold.ttf"),
//            weight = FontWeight.SemiBold
//        ),
//        Font(
//            file = File("src/main/res/font/poppins_bold.ttf"),
//            weight = FontWeight.Bold
//        )
    )
    val lexend = FontFamily(
        SystemFont("Lexend Thin", FontWeight.Thin),
        SystemFont("Lexend Light", FontWeight.Light),
        SystemFont("Lexend Bold", FontWeight.Bold)
//        Font(
//            file = File("src/main/res/font/lexend_thin.ttf"),
//            weight = FontWeight.Thin
//        ),
//        Font(
//            file = File("src/main/res/font/lexend_regular.ttf"),
//            weight = FontWeight.Normal
//        ),
//        Font(
//            file = File("src/main/res/font/lexend_bold.ttf"),
//            weight = FontWeight.Bold
//        )
    )
}