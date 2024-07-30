package fonts

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.yousufjamil.accorm.R

class Fonts() {
    val poppins = FontFamily(
        Font(R.font.poppins_thin, FontWeight.Thin),
        Font(R.font.poppins_light, FontWeight.Light),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_semibold, FontWeight.SemiBold),
        Font(R.font.poppins_regular, FontWeight.Normal)
    )
    val lexend = FontFamily(
        Font(R.font.lexend_thin, FontWeight.Thin),
        Font(R.font.lexend_regular, FontWeight.Normal),
        Font(R.font.lexend_bold, FontWeight.Bold)
    )
}